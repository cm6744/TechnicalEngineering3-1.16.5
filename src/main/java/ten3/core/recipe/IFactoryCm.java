package ten3.core.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IFactoryCm<T extends SingleRecipe> {
    T create(ResourceLocation reg, ResourceLocation idIn, CmItemList ingredientIn,
             ItemStack resultIn, ItemStack add, int cookTimeIn, int count, double cc);
}
