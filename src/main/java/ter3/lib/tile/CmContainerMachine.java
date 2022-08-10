package ten3.lib.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.init.ContInit;
import ten3.lib.capability.item.InventoryCm;

import java.util.List;

public class CmContainerMachine extends CmContainer {

    public InventoryCm ti;

    public CmContainerMachine(int id, PlayerInventory pi, CmTileMachine tile, BlockPos pos, IIntArray data) {

        super(ContInit.getType(tile.id), id, pi, tile, pos, data);
        ti = tile.inventory;

    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if(slot != null) {
            if(slot.getHasStack()) {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();
                if(slot.inventory == ti) {
                    //push to player's inv
                    if(!mergeItemStack(itemstack1, 0, 35, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if(slot.inventory == playerIn.inventory) {
                    //push to machine's inv
                    if(!mergeItemStack(itemstack1, 36, inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if(itemstack1.getCount() == 0) {
                    slot.putStack(ItemStack.EMPTY);
                } else {
                    slot.onSlotChanged();
                }
                if(itemstack1.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(playerIn, itemstack1);
            }

            return itemstack;
        }

        return ItemStack.EMPTY;

    }

}
