package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.crafting.IRecipe;

public interface IBaseRecipeCm<T extends IInventory> extends IRecipe<T> {

    int time();

    String condition();

}
