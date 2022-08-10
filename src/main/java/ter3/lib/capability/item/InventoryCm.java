package ten3.lib.capability.item;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ten3.lib.tile.CmTileEntity;
import ten3.lib.wrapper.SlotCm;

import java.util.List;

public class InventoryCm extends Inventory {

    List<SlotCm> slots;

    public InventoryCm(int size) {

        super(size);

    }

    public void postList(CmTileEntity t) {
        slots = t.slots;
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
