package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.Map;

public interface OpportunityRecipe<T extends IInventory> extends IBaseRecipeCm<T> {

    default ItemStack generateAddition() {
        if(Math.random() <= chance()) {
            return getAdditionOutput().copy();
        }
        return ItemStack.EMPTY;
    }

    ItemStack getAdditionOutput();

    double chance();

}
