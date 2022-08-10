package ten3.core.item.upgrades.impl;

import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.CmTileMachine;
import ten3.util.DecimalUtil;

public class UpgradeSpeed extends UpgradeItem {

    public boolean effect(CmTileMachine tile) {

        tile.efficientIn += tile.initialEfficientIn;
        return true;

    }

    @Override
    public void reset(CmTileMachine tile) {

        tile.efficientIn -= tile.initialEfficientIn;

    }

    @Override
    public int limit() {
        return 2;
    }

}
