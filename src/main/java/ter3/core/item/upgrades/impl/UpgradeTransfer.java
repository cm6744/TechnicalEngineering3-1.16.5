package ten3.core.item.upgrades.impl;

import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.CmTileMachine;
import ten3.util.DecimalUtil;

public class UpgradeTransfer extends UpgradeItem {

    public boolean effect(CmTileMachine tile) {

        tile.maxReceive += tile.initialReceive;
        tile.maxExtract += tile.initialExtract;
        return true;

    }

    @Override
    public void reset(CmTileMachine tile) {

        tile.maxReceive -= tile.initialReceive;
        tile.maxExtract -= tile.initialExtract;

    }

    @Override
    public int limit() {
        return 2;
    }

}
