package ten3.core.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IFactoryMTSCm<T extends SingleRecipe> {
    T create(ResourceLocation reg, ResourceLocation idIn, List<CmItemList> ings,
             ItemStack resultIn, ItemStack add, int cookTimeIn, int count, double cc);
}
