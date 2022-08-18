package ten3.core.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IFactoryMTSCm<T extends SingleRecipe> {
    T create(ResourceLocation reg, ResourceLocation idIn, CmItemList ingredientIn, CmItemList ingredientIn2,
             ItemStack resultIn, ItemStack add, int cookTimeIn, int count, double cc);
}
