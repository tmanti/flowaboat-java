import me.tmanti.flowaboat.utils.osu.types.osuMods;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class modTypeTests {
    @Test
    public void noModAndEmpty() {
        osuMods expectations = new osuMods(0);
        osuMods result1 = osuMods.fromString("noMod");
        osuMods result2 = osuMods.fromString("");
        Assert.assertEquals(expectations, result1);
        Assert.assertEquals(expectations, result2);
    }

    @Test
    public void randomString() {
        osuMods expectations = new osuMods(0);
        osuMods result = osuMods.fromString("giberesh");
        Assert.assertEquals(expectations, result);
    }

    @Test
    public void hardRockDoubleTime() {
        osuMods expectations = new osuMods(osuMods.getModInt("HR") + osuMods.getModInt("DT"));
        osuMods result = osuMods.fromString("+HRDT");
        Assert.assertEquals(expectations, result);
    }

    @Test
    public void convertToString() {
        List<String> expectations = Arrays.asList("HDHR", "HRHD");
        osuMods actual = new osuMods(osuMods.getModInt("HD") + osuMods.getModInt("HR"));
        Assert.assertTrue(expectations.contains(actual.getModString()));

        Assert.assertEquals("PF", osuMods.fromString("PF").getModString());
    }

    @Test
    public void addAndSubtract() {
        osuMods original = new osuMods(65);
        original.remove(1);
        original.remove(new osuMods(32));
        Assert.assertEquals(original, new osuMods(64));

        original = new osuMods(1);
        original.add(32);
        Assert.assertEquals(original, new osuMods(33));
    }

    @Test
    public void missingMods() {
        osuMods Nightcore = new osuMods(osuMods.getModInt("NC"));
        osuMods Perfect = new osuMods(osuMods.getModInt("PF"));

        long NCDT = osuMods.getModInt("NC") + osuMods.getModInt("DT");
        long PFSD = osuMods.getModInt("PF") + osuMods.getModInt("SD");

        Assert.assertEquals(NCDT, (long) Nightcore.getBitWiseFlag());
        Assert.assertEquals(PFSD, (long) Perfect.getBitWiseFlag());
    }
}
