package me.tmanti.flowaboat.utils.osu;

import me.tmanti.flowaboat.errors.osu.NoLeaderBoard;
import me.tmanti.flowaboat.errors.osu.NoPlays;
import me.tmanti.flowaboat.errors.web.InvalidRequestCount;
import me.tmanti.flowaboat.utils.ApiResponse;
import me.tmanti.flowaboat.utils.WebApi;
import me.tmanti.flowaboat.errors.osu.UserNonexistent;
import me.tmanti.flowaboat.utils.osu.types.osuPlay;
import me.tmanti.flowaboat.utils.osu.types.osuUser;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

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


    private static ApiResponse get(String url, Map<String, String> params) throws InterruptedException, IOException {
        try {
            return getApi().get(url, params);
        } catch (InvalidRequestCount e) {
            return get(url, params);
        }
    }

    private static osuPlay[] jsonToPlays(JSONArray plays, int beatmap_id) {
        osuPlay[] playObjs = new osuPlay[plays.size()];

        for (int i = 0; i < plays.size(); i++) {
            JSONObject play = (JSONObject) plays.get(i);
            playObjs[i] = osuPlay.proccessJson(play);
            if (beatmap_id != 0)
                playObjs[i].beatmap_id = beatmap_id;
        }
        return playObjs;
    }


    private static boolean nullResponse(ApiResponse response) throws IOException {
        return (!response.isSuccessful() || response.body() == null || response.getContent().equals("[]"));
    }


    // api parts

    public static osuUser[] get_user(String user) throws IOException, InterruptedException, UserNonexistent, ParseException {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("u", user);
        }};
        ApiResponse response = get("get_user", parameters);

        if (nullResponse(response))
            throw new UserNonexistent("Couldn't find user: " + user);

        JSONArray users = (JSONArray) new JSONParser().parse(response.getContent());

        osuUser[] userObjs = new osuUser[users.size()];

        for (int i = 0; i < users.size(); i++) {
            userObjs[i] = osuUser.proccessJson((JSONObject) users.get(i));
        }

        return userObjs;
    }

    public static osuPlay[] get_leaderboard(int beatmap_id, int limit) throws IOException, InterruptedException, ParseException, NoLeaderBoard {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("b", String.valueOf(beatmap_id));
            put("limit", String.valueOf(limit));
        }};
        ApiResponse response = get("get_scores", parameters);

        if (nullResponse(response))
            throw new NoLeaderBoard("Couldn't find leaderboard for this beatmap");

        return jsonToPlays((JSONArray) new JSONParser().parse(response.getContent()), beatmap_id);
    }


    public static osuPlay[] get_user_map_best(int beatmap_id, String user, int enabled_mods) throws IOException, InterruptedException, ParseException, NoPlays {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("b", String.valueOf(beatmap_id));
            put("u", user);
            put("mods", String.valueOf(enabled_mods));
        }};

        ApiResponse response = get("get_scores", parameters);

        if (nullResponse(response))
            throw new NoPlays("No top plays on this map found for " + user);

        return jsonToPlays((JSONArray) new JSONParser().parse(response.getContent()), beatmap_id);
    }

    public static osuPlay[] get_user_best(String user, int limit) throws NoPlays, IOException, InterruptedException, ParseException {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("u", user);
            put("limit", String.valueOf(limit));
        }};

        ApiResponse response = get("get_user_best", parameters);

        if (nullResponse(response))
            throw new NoPlays("No top plays found for " + user);

        return jsonToPlays((JSONArray) new JSONParser().parse(response.getContent()));
    }

    public static osuPlay[] get_user_recent(String user, int limit) throws NoPlays, IOException, InterruptedException, ParseException {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("u", user);
            put("limit", String.valueOf(limit));
        }};

        ApiResponse response = get("get_user_recent", parameters);

        if (nullResponse(response))
            throw new NoPlays("No recent plays found for " + user);

        return jsonToPlays((JSONArray) new JSONParser().parse(response.getContent()));
    }

    public static String get_replay(int beatmap_id, int user_id, int mods, int mode) throws IOException, InterruptedException, NoPlays, ParseException {
        Map<String, String> parameters = new HashMap<String, String>() {{
            put("b", String.valueOf(beatmap_id));
            put("u", String.valueOf(user_id));
            put("mods", String.valueOf(mods));
            put("m", String.valueOf(mode));
        }};

        ApiResponse response = get("get_replay", parameters);

        if (nullResponse(response))
            throw new NoPlays("Could not find replay for this user");

        JSONObject json = (JSONObject) new JSONParser().parse(response.getContent());

        return (String) json.get("content");
    }

    public static osuPlay get_top(String user, int index, boolean rb, boolean ob) throws InterruptedException, NoPlays, ParseException, IOException {
        int limit;
        index = Math.min(Math.max(index, 1), 100);
        if (rb || ob) {
            limit = 100;
        } else {
            limit = index;
        }
        osuPlay[] response = get_user_best(user, limit);

        if (rb) {
            Arrays.sort(response, osuPlay::compareDates);
            Collections.reverse(Arrays.asList(response));
        }
        if (ob)
            Arrays.sort(response, osuPlay::compareDates);

        if (response.length < index)
            index = response.length;

        return response[index - 1];
    }

    public static osuPlay get_recent(String user, int index) throws InterruptedException, NoPlays, ParseException, IOException {
        index = Math.min(Math.max(index, 1), 50);
        osuPlay[] response = get_user_recent(user, index);

        if (response.length < index)
            index = response.length;

        return response[index - 1];
    }


    // overloading
    private static osuPlay[] jsonToPlays(JSONArray plays) {
        return jsonToPlays(plays, 0);
    }

    public static osuPlay[] get_leaderboard(int beatmap_id) throws InterruptedException, ParseException, IOException, NoLeaderBoard {
        return get_leaderboard(beatmap_id, 100);
    }

    public static osuPlay[] get_user_map_best(int beatmap_id, String user) throws InterruptedException, ParseException, IOException, NoPlays {
        return get_user_map_best(beatmap_id, user, 0);
    }

    public static osuPlay[] get_user_best(String user) throws InterruptedException, NoPlays, ParseException, IOException {
        return get_user_best(user, 100);
    }

    public static osuPlay[] get_user_recent(String user) throws NoPlays, IOException, InterruptedException, ParseException {
        return get_user_recent(user, 10);
    }

    public static String get_replay(int beatmap_id, int user_id, int mods) throws IOException, InterruptedException, NoPlays, ParseException {
        return get_replay(beatmap_id, user_id, mods, 0);
    }

    public static osuPlay get_top(String user, int index) throws InterruptedException, NoPlays, ParseException, IOException {
        return get_top(user, index, false, false);
    }
}
