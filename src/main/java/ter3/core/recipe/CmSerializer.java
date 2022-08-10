package ten3.core.recipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public interface CmSerializer<T extends IRecipe<?>> extends IRecipeSerializer<T> {

    String id();

}
