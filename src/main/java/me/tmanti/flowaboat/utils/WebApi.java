package me.tmanti.flowaboat.utils;

import me.tmanti.flowaboat.errors.web.InvalidRequestCount;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;


public class WebApi {
    public String url;
    public Map<String, String> parameters;
    public int maxRequests;
    private Queue<Long> actions;

    OkHttpClient client = new OkHttpClient();

    public WebApi(String base_url, int max_requests_per_minute, Map<String, String> params) {
        this.url = base_url;
        this.parameters = params;
        this.maxRequests = max_requests_per_minute;
        this.actions = new LinkedList<>();
    }

    private Request compileUrl(String link_extension, Map<String, String> params) {
        HttpUrl.Builder url = HttpUrl.parse(this.url).newBuilder();
        url.addPathSegment(link_extension);

        for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
            if (!params.containsKey(entry.getKey())) {
                params.put(entry.getKey(), entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return new Request.Builder().url(url.build()).build();
    }


    private void clearQueue() {
        while (this.actions.size() > 0) {
            if ((System.currentTimeMillis() - this.actions.peek()) / 1000 >= 60) {
                this.actions.remove();
            } else {
                break;
            }
        }
    }


    public Response get(String url, Map<String, String> params) throws IOException, InvalidRequestCount {
        this.clearQueue();

        if (this.actions.size() <= this.maxRequests) {
            this.actions.add(System.currentTimeMillis());

            Request request = compileUrl(url, params);
            try (Response response = client.newCall(request).execute()) {
                return response;
            }
        }

        long free_in = this.minWait();
        //Thread.sleep(free_in);
        //return this.get(url, params);
        throw new InvalidRequestCount("Too many requests queue will be free in " + free_in / 1000 + "seconds", free_in);
    }

    public boolean usable(){
        return this.actions.size() <= this.maxRequests;
    }

    public long minWait(){
        return Math.max(60100 - (System.currentTimeMillis() - this.actions.peek()), 0);
    }

    // overloading stuff

    public WebApi(String base_url, Map<String, String> params) {
        this(base_url, 60, params);
    }

    public WebApi(String base_url) {
        this(base_url, new HashMap<>());
    }

    public WebApi(String base_url, int max_requests_per_minute) {
        this(base_url);
        this.maxRequests = max_requests_per_minute;
    }


    public Response get(String url) throws IOException, InvalidRequestCount {
        return this.get(url, new HashMap<>());
    }

    public Response get(Map<String, String> params) throws IOException, InvalidRequestCount {
        return this.get("", params);
    }

    public Response get() throws IOException, InvalidRequestCount {
        return this.get("");
    }
}
