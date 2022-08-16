package ten3.lib.wrapper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import ten3.core.client.ClientHolder;
import ten3.core.item.upgrades.LevelupItem;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.core.network.Network;
import ten3.lib.capability.item.InventoryCm;
import ten3.lib.tile.CmTileMachine;
import ten3.util.ExcUtil;

public class SlotUpgCm extends SlotCm {

    boolean lock;

    public SlotUpgCm(Inventory i, int id, int x, int y) {

        super(i, id, x, y, SlotCm.RECEIVE_ALL_INPUT, false, false);

    }

    public void lock(boolean l) {
        lock = l;
    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        if((stack.getItem() instanceof UpgradeItem) && !(stack.getItem() instanceof LevelupItem)) {
            return true;
        }

        return false;

    }

    @Override
    public boolean canTakeStack(PlayerEntity p) {
        return !lock;
    }

}
