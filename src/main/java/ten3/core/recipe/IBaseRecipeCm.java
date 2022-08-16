package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;
import java.util.Map;

public interface IBaseRecipeCm<T extends IInventory> extends IRecipe<T> {

    int time();

    String condition();

    List<ItemStack> input();

    List<ItemStack> output();

}
