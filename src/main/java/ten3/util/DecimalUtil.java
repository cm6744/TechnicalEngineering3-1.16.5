package ten3.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {

    public static int mul(double v, double mul) {

        BigDecimal bd = BigDecimal.valueOf(v);
        return bd.multiply(BigDecimal.valueOf(mul)).intValue();

    }

    public static int div(double v, double mul) {

        BigDecimal bd = BigDecimal.valueOf(v);
        return bd.divide(BigDecimal.valueOf(mul), RoundingMode.DOWN).intValue();

    }

}
