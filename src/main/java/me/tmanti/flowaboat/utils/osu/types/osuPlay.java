package me.tmanti.flowaboat.utils.osu.types;


import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class osuPlay {
    private final static SimpleDateFormat osuDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public int score_id, score, count300, count100, count50, countmiss, maxcombo, countkatu, countgeki, user_id, beatmap_id;
    public boolean perfect, replay_available;
    public osuMods enabled_mods;
    public String username, rank;
    public Date date;
    public float pp;


    public osuPlay(String score_id, String score, String username, String count300, String count100, String count50,
                   String countmiss, String maxcombo, String countkatu, String countgeki, String perfect,
                   String enabled_mods, String user_id, String date, String rank, String pp, String replay_available,
                   String beatmap_id) {

        this.score_id = Integer.parseInt(score_id);
        this.username = username;
        this.score = Integer.parseInt(score);
        this.count300 = Integer.parseInt(count300);
        this.count100 = Integer.parseInt(count100);
        this.count50 = Integer.parseInt(count50);
        this.countmiss = Integer.parseInt(countmiss);
        this.maxcombo = Integer.parseInt(maxcombo);
        this.countkatu = Integer.parseInt(countkatu);
        this.countgeki = Integer.parseInt(countgeki);
        this.perfect = Integer.parseInt(perfect) == 1;
        this.enabled_mods = new osuMods(Integer.parseInt(enabled_mods));
        this.user_id = Integer.parseInt(user_id);
        try {
            this.date = osuDate.parse(date);
        } catch (java.text.ParseException e) {
            this.date = new Date(0);
        }
        this.rank = rank;
        this.pp = Float.parseFloat(pp);
        this.replay_available = Integer.parseInt(replay_available) == 1;
        this.beatmap_id = Integer.parseInt(beatmap_id);
    }

    public static osuPlay proccessJson(JSONObject json_object) {
        return new osuPlay((String) json_object.getOrDefault("score_id", "0"),
                (String) json_object.get("score"), (String) json_object.get("username"),
                (String) json_object.get("count300"), (String) json_object.get("count100"),
                (String) json_object.get("count50"), (String) json_object.get("countmiss"),
                (String) json_object.get("maxcombo"), (String) json_object.get("countkatu"),
                (String) json_object.get("countgeki"), (String) json_object.get("perfect"),
                (String) json_object.get("enabled_mods"), (String) json_object.get("user_id"),
                (String) json_object.get("date"), (String) json_object.get("rank"),
                (String) json_object.getOrDefault("pp", "0"),
                (String) json_object.getOrDefault("replay_available", "0"),
                (String) json_object.getOrDefault("beatmap_id", "0"));
    }

    public static int compareDates(osuPlay a, osuPlay b) {
        return a.date.compareTo(b.date);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof osuPlay)) return false;

        osuPlay c = (osuPlay) o;

        return date == c.date && user_id == c.user_id;
    }
}
