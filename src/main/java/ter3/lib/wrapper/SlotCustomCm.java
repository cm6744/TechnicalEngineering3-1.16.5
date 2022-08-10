package ten3.lib.wrapper;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SlotCustomCm extends SlotCm {

    Condition cond;

    public SlotCustomCm(Inventory i, int id, int x, int y, Condition c, boolean ext, boolean in) {

        super(i, id, x, y, null, ext, in);

        cond = c;

        this.ext = ext;
        this.in = in;

    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        return cond.check(stack);

    }

    public interface Condition {
        boolean check(ItemStack stack);
    }

}
