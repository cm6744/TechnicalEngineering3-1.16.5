package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface OpportunityRecipe<T extends IInventory> extends IBaseRecipeCm<T> {

    ItemStack additionalOutput();

}
