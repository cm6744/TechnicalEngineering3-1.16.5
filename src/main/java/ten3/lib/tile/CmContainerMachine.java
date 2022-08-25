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

    public static boolean isInBackpack(int slot) {
        return slot >= playerMin && slot < playerMax;
    }

    public static boolean isInFastBar(int slot) {
        return slot >= fastMin && slot < fastMax;
    }

    public static boolean isInTile(int slot) {
        return slot >= playerMax;
    }

    static int playerMin=0;
    static int playerMax=36;
    static int fastMin=0;
    static int fastMax=9;

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if(slot != null) {
            if(slot.getHasStack()) {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();
                if (isInBackpack(index)) {
                    int[] iarr = tileEntity.getItemFirstTransferSlot(itemstack1.getItem());
                    if(iarr.length == 2) {
                        int p1 = inventorySlots.indexOf(ti.match(iarr[0]));
                        int p2 = inventorySlots.indexOf(ti.match(iarr[1] + 1));
                        if(!mergeItemStack(itemstack1, p1, p2, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    if(!this.mergeItemStack(itemstack1, playerMax, inventorySlots.size(), false)) {
                        if(!isInFastBar(index)) {
                            if(!this.mergeItemStack(itemstack1, fastMin, fastMax, false)) {
                                //to fast bar
                                return ItemStack.EMPTY;
                            }
                        } else if(!this.mergeItemStack(itemstack1, fastMax, playerMax, false)) {
                            //to other backpack slots
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (!this.mergeItemStack(itemstack1, playerMin, playerMax, false)) {
                    return ItemStack.EMPTY;
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
