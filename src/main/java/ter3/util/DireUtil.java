package ten3.util;

import net.minecraft.util.Direction;

public class DireUtil {

    public static int direToInt(Direction d) {

        if(d == null) return 0;

        return d.getIndex();

    }

    public static Direction intToDire(int d) {

        return Direction.byIndex(d);

    }

    public static Direction safeOps(Direction d) {

        return d == null ? null : d.getOpposite();

    }

}
