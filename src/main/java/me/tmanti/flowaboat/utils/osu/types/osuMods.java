package me.tmanti.flowaboat.utils.osu.types;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class osuMods {
    private static final String[] DIFF_MODS = new String[]{"HR", "EZ", "DT", "HT", "NC", "FL", "HD", "NF"};
    private static final String[] TIME_MODS = new String[]{"DT", "HT", "NC"};
    private static final Map<String, Long> MOD_FLAGS = new HashMap<String, Long>() {{
        put("NF", 1L);
        put("EZ", 1L << 1);
        put("TD", 1L << 2);
        put("HD", 1L << 3);
        put("HR", 1L << 4);
        put("SD", 1L << 5);
        put("DT", 1L << 6);
        put("RX", 1L << 7);
        put("HT", 1L << 8);
        put("NC", 1L << 9);
        put("FL", 1L << 10);
        put("AT", 1L << 11);
        put("SO", 1L << 12);
        put("AP", 1L << 13);
        put("PF", 1L << 14);
        put("4K", 1L << 15);
        put("5K", 1L << 16);
        put("6K", 1L << 17);
        put("7K", 1L << 18);
        put("8K", 1L << 19);
        put("FI", 1L << 20);
        put("RD", 1L << 21);
        put("LM", 1L << 22);
        put("TR", 1L << 23);
        put("9K", 1L << 24);
        put("10K", 1L << 25);
        put("1K", 1L << 26);
        put("3K", 1L << 27);
        put("2K", 1L << 28);
        put("V2", 1L << 29);
    }};
    private static final Map<Long, String> MOD_FLAGS_INV = new HashMap<Long, String>() {{
        for (Map.Entry<String, Long> entry : MOD_FLAGS.entrySet()) {
            put(entry.getValue(), entry.getKey());
        }
    }};
    private static final Pattern MODS_RE = Pattern.compile(generateModString());
    public Long modFlag;

    public osuMods(Long mods) {
        this.modFlag = mods;
        this.modFlag = addMissing(mods);
    }

    public osuMods(int mods) {
        this((long) mods);
    }

    public osuMods() {
        this(0);
    }

    private static String generateModString() {
        StringBuilder string = new StringBuilder();
        string.append("(");  // ^(
        boolean first = true;
        for (String i : MOD_FLAGS.keySet()) {
            if (!first) {
                string.append("|");
            }
            first = false;
            string.append(i);
        }
        string.append(")");  // )+$
        return string.toString();
    }

    public static Long getModInt(String mod) {
        if (!MOD_FLAGS.containsKey(mod)) return 0L;
        return MOD_FLAGS.get(mod.toUpperCase());
    }

    public static osuMods fromString(String string) {
        if (string.equals("") || string.toLowerCase().equals("nomod")) return new osuMods();

        string = string.replace("+", "");

        Matcher mods_included = MODS_RE.matcher(string);

        //if (!mods_included.matches()) return new osuMods();

        int mods = 0;
        while (mods_included.find()) {
            Long curr = getModInt(mods_included.group());
            if ((mods & curr) == 0) {
                mods += curr;
            }
        }
        return new osuMods(mods);
    }

    public String getModString() {
        this.modFlag = this.sanitize(this.modFlag);
        StringBuilder string = new StringBuilder();
        for (Long i : MOD_FLAGS_INV.keySet()) if (this.contains(i)) string.append(MOD_FLAGS_INV.get(i));
        this.modFlag = this.addMissing(this.modFlag);
        return string.toString();
    }

    public Long getBitWiseFlag() {
        return this.modFlag;
    }

    public boolean contains(String mod) {
        return this.contains(getModInt(mod));
    }

    public boolean contains(long mod) {
        return (this.modFlag & mod) != 0;
    }

    public boolean contains(int mod) {
        return this.contains((long) mod);
    }

    private long addMissing(Long modInt) {
        if (this.contains("NC") && !this.contains("DT")) modInt += getModInt("DT");
        if (this.contains("PF") && !this.contains("SD")) modInt += getModInt("SD");
        return modInt;
    }

    private long sanitize(Long modInt) {
        if (this.contains("NC") && this.contains("DT")) modInt -= getModInt("DT");
        if (this.contains("PF") && this.contains("SD")) modInt -= getModInt("SD");
        return modInt;
    }

    public void add(String mods) {
        this.add(getModInt(mods));
    }

    public void add(int mods) {
        this.add((long) mods);
    }

    public void add(osuMods mods) {
        this.add(mods.getBitWiseFlag());
    }

    public void add(long mods) {
        this.modFlag = addMissing(this.modFlag | mods);
    }

    public void remove(String mods) {
        this.remove(getModInt(mods));
    }

    public void remove(int mods) {
        this.remove((long) mods);
    }

    public void remove(osuMods mods) {
        this.remove(mods.getBitWiseFlag());
    }

    public void remove(long mods) {
        this.modFlag = addMissing((this.modFlag & ~mods));
    }


    @Override
    public String toString() {
        return String.valueOf(modFlag);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (o instanceof Integer) return this.modFlag == (int) o;

        if (!(o instanceof osuMods)) return false;

        osuMods c = (osuMods) o;

        return this.modFlag.equals(c.modFlag);
    }
}
