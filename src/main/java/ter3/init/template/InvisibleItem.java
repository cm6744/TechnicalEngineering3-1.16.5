package ten3.init.template;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InvisibleItem extends DefItem {

    public InvisibleItem() {

        super(true);

    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        stack.setCount(0);
    }

}
