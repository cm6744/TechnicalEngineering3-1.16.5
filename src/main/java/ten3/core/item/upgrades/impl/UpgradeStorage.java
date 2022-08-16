package ten3.core.item.upgrades.impl;

import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.CmTileMachine;
import ten3.util.DecimalUtil;

public class UpgradeStorage extends UpgradeItem {

    public boolean effect(CmTileMachine tile) {

        tile.maxStorage += tile.initialStorage;
        return true;

    }

}
