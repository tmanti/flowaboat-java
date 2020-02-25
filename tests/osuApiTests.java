import me.tmanti.flowaboat.errors.osu.NoLeaderBoard;
import me.tmanti.flowaboat.errors.osu.NoPlays;
import me.tmanti.flowaboat.errors.osu.UserNonexistent;
import me.tmanti.flowaboat.utils.osu.OsuApi;
import me.tmanti.flowaboat.utils.osu.types.osuMods;
import me.tmanti.flowaboat.utils.osu.types.osuPlay;
import me.tmanti.flowaboat.utils.osu.types.osuUser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class osuApiTests {
    @BeforeClass
    public static void setUp() {
        Assert.assertNotNull("no api key", System.getenv("osu_key"));
        OsuApi.addKey(System.getenv("osu_key"));
    }

    @Test
    public void getUser() throws InterruptedException, UserNonexistent, ParseException, IOException {
        osuUser actual = OsuApi.get_user("zivoy")[0];
        Assert.assertNotNull(actual);
    }

    @Test
    public void getLeaderboard() throws InterruptedException, ParseException, IOException, NoLeaderBoard {
        osuPlay[] actual = OsuApi.get_leaderboard(839792);
        Assert.assertNotNull(actual);
    }

    @Test
    public void getUserMapBest() throws InterruptedException, ParseException, IOException, NoPlays {
        osuPlay[] actual = OsuApi.get_user_map_best(1617047,"tmanti");
        Assert.assertNotNull(actual);
    }

    @Test
    public void getReplay() throws InterruptedException, ParseException, IOException, NoPlays {
        String actual = OsuApi.get_replay(2193921, "Arkisol", osuMods.fromString("DT"));
        Assert.assertNotNull(actual);
    }

    @Test
    public void getTop() throws InterruptedException, ParseException, IOException, NoPlays {
        osuPlay actual = OsuApi.get_top("LynVA", 3);
        Assert.assertNotNull(actual);
    }

    @Test
    public void getRecent() throws InterruptedException, ParseException, IOException, NoPlays {
        osuPlay actual = OsuApi.get_recent("WhiteCat",1);
        Assert.assertNotNull(actual);
    }
}
