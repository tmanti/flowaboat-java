package me.tmanti.flowaboat.utils.osu.types;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class osuUser {
    private final static SimpleDateFormat osuDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public int user_id, pp_rank, pp_raw, count_rank_ss, count_rank_ssh, count_rank_s, count_rank_sh, count_rank_a,
            pp_country_rank;
    public long count300, count100, count50, playcount, ranked_score, total_score, total_seconds_played;
    public float level, accuracy;
    public Date join_date;
    public String username, country;
    public Event[] events;

    public osuUser(String user_id, String username, String join_date, String count300, String count100, String count50,
                   String playcount, String ranked_score, String total_score, String pp_rank, String level,
                   String pp_raw, String accuracy, String count_rank_ss, String count_rank_ssh, String count_rank_s,
                   String count_rank_sh, String count_rank_a, String country, String total_seconds_played,
                   String pp_country_rank, JSONArray events) {

        this.user_id = Integer.parseInt(user_id);
        this.username = username;
        try {
            this.join_date = osuDate.parse(join_date); // In UTC
        } catch (java.text.ParseException e) {
            this.join_date = new Date(0);
        }
        this.count300 = Long.parseLong(count300);   // Total amount for all ranked, approved, and loved beatmaps played
        this.count100 = Long.parseLong(count100);   // Total amount for all ranked, approved, and loved beatmaps played
        this.count50 = Long.parseLong(count50);     // Total amount for all ranked, approved, and loved beatmaps played
        this.playcount = Long.parseLong(playcount); // Only counts ranked, approved, and loved beatmaps
        this.ranked_score = Long.parseLong(ranked_score);  // Counts the best individual score on each ranked, approved, and loved beatmaps
        this.total_score = Long.parseLong(total_score);    // Counts every score on ranked, approved, and loved beatmaps
        this.pp_rank = Integer.parseInt(pp_rank);
        this.level = Float.parseFloat(level);
        this.pp_raw = Integer.parseInt(pp_raw);   // For inactive players this will be 0 to purge them from leaderboards
        this.accuracy = Float.parseFloat(accuracy);

        this.count_rank_ss = Integer.parseInt(count_rank_ss);
        this.count_rank_ssh = Integer.parseInt(count_rank_ssh);
        this.count_rank_s = Integer.parseInt(count_rank_s);       // Counts for SS/SSH/S/SH/A ranks on maps
        this.count_rank_sh = Integer.parseInt(count_rank_sh);
        this.count_rank_a = Integer.parseInt(count_rank_a);

        this.country = country;    // Uses the ISO3166-1 alpha-2 country code naming. See this for more information: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
        this.total_seconds_played = Long.parseLong(total_seconds_played);
        this.pp_country_rank = Integer.parseInt(pp_country_rank);    // The user's rank in the country.
        this.events = new Event[events.size()];   // Contains events for this user
        for (int i = 0; i < events.size(); i++) {
            JSONObject current = (JSONObject) events.get(i);
            this.events[i] = new Event((String) current.get("display_html"), (String) current.get("beatmap_id"),
                    (String) current.get("beatmapset_id"), (String) current.get("date"),
                    (String) current.get("epicfactor"));
        }
    }

    public static osuUser proccessJson(JSONObject json_object) {
        return new osuUser((String) json_object.get("user_id"), (String) json_object.get("username"),
                (String) json_object.get("join_date"), (String) json_object.get("count300"),
                (String) json_object.get("count100"), (String) json_object.get("count50"),
                (String) json_object.get("playcount"), (String) json_object.get("ranked_score"),
                (String) json_object.get("total_score"), (String) json_object.get("pp_rank"),
                (String) json_object.get("level"), (String) json_object.get("pp_raw"),
                (String) json_object.get("accuracy"), (String) json_object.get("count_rank_ss"),
                (String) json_object.get("count_rank_ssh"), (String) json_object.get("count_rank_s"),
                (String) json_object.get("count_rank_sh"), (String) json_object.get("count_rank_a"),
                (String) json_object.get("country"), (String) json_object.get("total_seconds_played"),
                (String) json_object.get("pp_country_rank"), (JSONArray) json_object.get("events"));
    }

    private class Event {
        public String display_html;
        public int beatmap_id, beatmapset_id, epicfactor;
        public Date date;

        public Event(String display_html, String beatmap_id, String beatmapset_id, String date, String epicfactor) {
            this.display_html = display_html;
            this.beatmap_id = Integer.parseInt(beatmap_id);
            this.beatmapset_id = Integer.parseInt(beatmapset_id);
            try {
                this.date = osuDate.parse(date);  // In UTC
            } catch (ParseException e) {
                this.date = new Date(0);
            }
            this.epicfactor = Integer.parseInt(epicfactor); // How "epic" this event is (between 1 and 32)
        }
    }
}
