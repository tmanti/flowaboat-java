package me.tmanti.flowaboat.utils.osu.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class osuMods {
    public int modFlag;
    private static final String[] DIFF_MODS = new String[]{"HR", "EZ", "DT", "HT", "NC", "FL", "HD", "NF"};
    private static final String[] TIME_MODS = new String[]{"DT", "HT", "NC"};

    private static final Map<String, Integer> MOD_FLAGS = new HashMap<String, Integer>(){{
                put("NF", 1 << 0);
                put("EZ", 1 << 1);
                put("TD", 1 << 2);
                put("HD", 1 << 3);
                put("HR", 1 << 4);
                put("SD", 1 << 5);
                put("DT", 1 << 6);
                put("RX", 1 << 7);
                put("HT", 1 << 8);
                put("NC", 1 << 9);
                put("FL", 1 << 10);
                put("AT", 1 << 11);
                put("SO", 1 << 12);
                put("AP", 1 << 13);
                put("PF", 1 << 14);
                put("4K", 1 << 15);
                put("5K", 1 << 16);
                put("6K", 1 << 17);
                put("7K", 1 << 18);
                put("8K", 1 << 19);
                put("FI", 1 << 20);
                put("RD", 1 << 21);
                put("LM", 1 << 22);
                put("TR", 1 << 23);
                put("9K", 1 << 24);
                put("10K", 1 << 25);
                put("1K", 1 << 26);
                put("3K", 1 << 27);
                put("2K", 1 << 28);
                put("V2", 1 << 29);
    }};
    private static final Map<Integer, String> MOD_FLAGS_INV = new HashMap<Integer, String>(){{
        for(Map.Entry<String, Integer> entry : MOD_FLAGS.entrySet()){
            put(entry.getValue(), entry.getKey());
        }
    }};

    private static String generateModString(){
        StringBuilder string = new StringBuilder();
        string.append("(");  // ^(
        boolean first = true;
        for (String i : MOD_FLAGS.keySet()){
            if (!first){
                string.append("|");
            }
            first = false;
            string.append(i);
        }
        string.append(")");  // )+$
        return string.toString();
    }
    private static final Pattern MODS_RE = Pattern.compile(generateModString());

    public static int getModInt(String mod){
        if (!MOD_FLAGS.containsKey(mod)) return 0;
        return MOD_FLAGS.get(mod.toUpperCase());
    }

    public osuMods(int mods) {
        this.modFlag = mods;
    }

    public osuMods() {
        this(0);
    }

    public static osuMods fromString(String string){
        if (string.equals("") || string.toLowerCase().equals("nomod")) return new osuMods();

        string = string.replace("+","");

        Matcher mods_included = MODS_RE.matcher(string);

        //if (!mods_included.matches()) return new osuMods();

        int mods = 0;
        while (mods_included.find()){
            int curr = getModInt(mods_included.group());
            if ((mods & curr) == 0){
                mods+=curr;
            }
        }
        return new osuMods(mods);
    }

    public String getModString(){
        StringBuilder string = new StringBuilder();
        for (int i : MOD_FLAGS_INV.keySet()) if((this.modFlag & i) != 0) string.append(MOD_FLAGS_INV.get(i));
        return string.toString();
    }

    public int modsInt() {
        return this.modFlag;
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

        return this.modFlag == c.modFlag;
    }
}
