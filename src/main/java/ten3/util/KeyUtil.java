package ten3.util;

import net.minecraft.util.text.*;
import ten3.TConst;

@OnlyCore
public class KeyUtil {

    @OnlyCore
    public static String getKey(String s) {

        String test = TConst.modid + "." + s;
        return test;

    }

    @OnlyCore
    public static String translateInfoBarKey(String s) {

        if(s.startsWith("cell_")) {
            return "cell";
        }

        return s.replace("machine_", "");

    }

    public static IFormattableTextComponent translated(String... ss) {

        TranslationTextComponent t1 = null;

        for(String s : ss) {
            if(t1 == null) {
                t1 = new TranslationTextComponent(s);
            }
            else {
                t1.appendSibling(new TranslationTextComponent(s));
            }
        }

        return t1;

    }

    public static IFormattableTextComponent make(String... ss) {

        StringTextComponent t1 = null;

        for(String s : ss) {
            if(t1 == null) {
                t1 = new StringTextComponent(s);
            }
            else {
                t1.appendSibling(new StringTextComponent(s));
            }
        }

        return t1;

    }

    public static TextFormatting GOLD = TextFormatting.GOLD;
    public static TextFormatting GREEN = TextFormatting.GREEN;
    public static TextFormatting RED = TextFormatting.RED;

    public static IFormattableTextComponent translated(TextFormatting color, String... ss) {

        return translated(ss).mergeStyle(color);

    }

    public static ITextComponent make(TextFormatting color, String... ss) {

        return make(ss).mergeStyle(color);

    }

}
