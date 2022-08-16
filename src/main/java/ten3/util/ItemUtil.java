package ten3.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class ItemUtil {

    public static void damage(ItemStack stack, World world, int am) {

        if(stack.attemptDamageItem(am, world.rand, null)) {
            stack.setCount(0);
        }

    }

    public static void setTag(ItemStack stack, String name, int cr) {

        setTagD(stack, name, cr);

    }

    public static void tranTag(ItemStack stack, String name, int move) {

        setTagD(stack, name, getTag(stack, name) + move);

    }

    public static int getTag(ItemStack stack, String name) {

        return (int)getTagD(stack, name);

    }

    public static double getTagD(ItemStack stack, String name) {

        if(!stack.hasTag()) {
            return 0;
        }

        if(!stack.getTag().contains(name)) {
            return 0;
        }

        return stack.getTag().getDouble(name);

    }

    public static void setTagD(ItemStack stack, String name, double cr) {

        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putDouble(name, cr);
        stack.setTag(nbt);

    }

    public static void tranTagD(ItemStack stack, String name, double move) {

        setTagD(stack, name, getTagD(stack, name) + move);

    }

}
