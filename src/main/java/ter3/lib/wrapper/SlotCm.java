package ten3.lib.wrapper;

import com.google.common.collect.Lists;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SlotCm extends Slot {

    public static List<Item> DISABLE_ALL_INPUT = Lists.newArrayList();
    public static List<Item> RECEIVE_ALL_INPUT = null;

    List<Item> list;

    boolean isRes;

    boolean ext;
    boolean in;

    public SlotCm(Inventory i, int id, int x, int y, List<Item> valid, boolean ext, boolean in) {

        //bug fixed
        super(i, id, x+1, y+1);

        list = valid;

        this.ext = ext;
        this.in = in;

    }

    public SlotCm withIsResultSlot() {

        isRes = true;
        return this;

    }

    //check player input, vanilla method(I cannot change it)
    @Override
    public boolean isItemValid(ItemStack stack) {

        return isItemValidInHandler(stack) && !isRes;

    }

    //check handler input
    public boolean isItemValidInHandler(ItemStack stack) {

        if(list == null) return true;

        return (list.contains(stack.getItem()));

    }

    public boolean canHandlerExt() {

        return ext;

    }

    public boolean canHandlerIn() {

        return in;

    }

}
