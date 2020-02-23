package me.tmanti.flowaboat.utils.osu;

import com.sun.javafx.iio.ios.IosDescriptor;
import me.tmanti.flowaboat.errors.web.InvalidRequestCount;
import me.tmanti.flowaboat.utils.WebApi;
import me.tmanti.flowaboat.errors.osu.UserNonexistent;
import me.tmanti.flowaboat.utils.osu.types.osuPlay;
import me.tmanti.flowaboat.utils.osu.types.osuUser;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.naming.directory.Attributes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//todo look at api v2 when it comes out

public class OsuApi {
    static ArrayList<WebApi> apiList = new ArrayList<>();

    public static void addKey(String key) {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("k", key);
        }};

        apiList.add(new WebApi("https://osu.ppy.sh/api", 60, parameters));
    }

    private static WebApi getApi() throws InterruptedException {
        Collections.shuffle(apiList);
        long min = 60000;
        for (WebApi api : apiList) {
            if (api.usable()) {
                return api;
            } else {
                min = Math.min(api.minWait(), min);
            }
        }
        Thread.sleep(min);
        return getApi();
        //throw new InvalidRequestCount("No free api key", min);
    }


    private static Response get(String url, Map<String, String> params) throws InterruptedException, IOException {
        try {
            return getApi().get(url, params);
        } catch (InvalidRequestCount e) {
            return get(url, params);
        }
    }


    // api parts

    public static osuUser[] get_user(String user) throws IOException, InterruptedException, UserNonexistent, ParseException {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("u", user);
        }};
        Response User = get("get_user", parameters);

        if (!User.isSuccessful() || User.body() == null || User.body().string().equals("[]"))
            throw new UserNonexistent("Couldn't find user: " + user);

        JSONArray users = (JSONArray) new JSONParser().parse(User.body().string());

        osuUser[] userObjs = new osuUser[users.size()];

        for(int i=0; i<users.size();i++){
            userObjs[i] = osuUser.proccessJson((JSONObject) users.get(i));
        }

        return userObjs;
    }
}
