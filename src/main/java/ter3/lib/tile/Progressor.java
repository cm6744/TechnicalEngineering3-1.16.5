package ten3.lib.tile;

import ten3.lib.wrapper.IntArrayCm;

import static ten3.lib.tile.CmTileMachine.maxProgress;
import static ten3.lib.tile.CmTileMachine.progress;

public class Progressor {

    double timeProgressSpeed;//it decreases, speed increases
    double stressedProgress;

    public void progressOn(IntArrayCm data, int eff) {

            int max = data.get(maxProgress);
            timeProgressSpeed = max * eff * 0.0003 + 0.2;

            if(timeProgressSpeed >= 1) {
                data.translate(progress, (int) timeProgressSpeed);
            }
            else {
                stressedProgress += timeProgressSpeed;
                if(stressedProgress >= 1) {
                    stressedProgress -= 1;
                    data.translate(progress, 1);
                }
            }

    }

}
