package ten3.core.item.upgrades;

import ten3.lib.tile.CmTileMachine;

public abstract class LevelupItem extends UpgradeItem {

    @Override
    public void reset(CmTileMachine tile) {}

    @Override
    public int limit() {
        return 0;
    }

}
