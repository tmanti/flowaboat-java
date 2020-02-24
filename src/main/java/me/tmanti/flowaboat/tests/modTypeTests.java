package me.tmanti.flowaboat.tests;

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
    }
}
