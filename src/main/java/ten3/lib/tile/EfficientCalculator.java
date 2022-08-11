package ten3.lib.tile;

import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.IntArrayCm;

import static ten3.lib.tile.CmTileMachine.*;

public class EfficientCalculator {

    public static double gen(Type type, IntArrayCm data) {

        if(type == Type.GENERATOR) {
            if(data.get(maxFuel) == 0 || data.get(fuel) == 0) {
                data.set(actualEff, 0);
            } else {
                return  (data.get(fuel) / (double) data.get(maxFuel) * 0.9 + 0.1);
            }
        } else if(type == Type.MACHINE_PROCESS || type == Type.MACHINE_EFFECT || type == Type.CELL) {
            if(data.get(maxEnergy) == 0 || data.get(energy) == 0) {
                data.set(actualEff, 0);
            } else {
                return  (data.get(energy) / (double) data.get(maxEnergy) * 0.9 + 0.1);
            }
        }

        return 0;

    }

}
