package ten3.lib.capability.item;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ten3.lib.tile.CmTileEntity;
import ten3.lib.wrapper.SlotCm;

import java.util.ArrayList;
import java.util.List;

public class InventoryCm extends Inventory {

    List<SlotCm> slots;
    CmTileEntity tile;

    public InventoryCm(int size, CmTileEntity t) {

        super(size);
        slots = t.slots;
        tile = t;
    }

    public InventoryCm copy() {
        InventoryCm inv = new InventoryCm(getSizeInventory(), tile);
        for(int i = 0; i < getSizeInventory(); i++) {
            inv.setInventorySlotContents(i, getStackInSlot(i));
        }
        return inv;
    }

    //used in InvWrapper, by FORGE.
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c.isItemValidInHandler(stack);
            }
        }
        return false;
    }

    public List<ItemStack> getStackInRange(int fr, int to) {
        List<ItemStack> lst = new ArrayList<>();
        for(int i = fr; i<= to; i++) {
            if(!getStackInSlot(i).isEmpty()) {
                lst.add(getStackInSlot(i));
            }
        }
        return lst;
    }

    public SlotCm match(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public boolean isIn(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c.canHandlerIn();
            }
        }
        return false;
    }

    public boolean isExt(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c.canHandlerExt();
            }
        }
        return false;
    }

    public boolean isUsed(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return true;
            }
        }
        return false;
    }

}
