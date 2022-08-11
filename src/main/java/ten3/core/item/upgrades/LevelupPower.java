package ten3.core.item.upgrades;

import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Level;

public class LevelupPower extends LevelupItem {

    @Override
    public boolean effect(CmTileMachine tile) {

        if(tile.levelIn != Level.AUGMENTED) return false;

        tile.levelIn = Level.POWERED;
        return true;

    }

}
