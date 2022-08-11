package ten3.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class PatternUtil {

    public static ITextComponent join(double e, double me) {

        if(e >= 0) {
            if(e < 1000) {
                return (KeyUtil.make(KeyUtil.RED, e + " FE / " + String.format("%.1f", me / 1000) + " kFE"));
            }
            else {
                return (KeyUtil.make(KeyUtil.RED, String.format("%.1f", e / 1000) + " kFE / " + String.format("%.1f", me / 1000) + " kFE"));
            }
        }

        return KeyUtil.make("");

    }

}
