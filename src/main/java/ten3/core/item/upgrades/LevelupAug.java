package ten3.core.item.upgrades;

import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Level;

public class LevelupAug extends LevelupItem {

    @Override
    public boolean effect(CmTileMachine tile) {

        if(tile.levelIn != Level.COMMON) return false;

        tile.levelIn = Level.AUGMENTED;
        return true;

    }

}
