package ten3.plugin.jei;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.crafting.Ingredient;
import ten3.core.recipe.MTSRecipe;
import ten3.core.recipe.SingleRecipe;

import java.util.List;

public class TECategorySgMTS extends TECategory<MTSRecipe> {

    public TECategorySgMTS(String name, int ru, int rv)
    {
        super(name, 0, 192, 105, 60, ru, rv, 45, 23);
    }

    @Override
    public Class<? extends MTSRecipe> getRecipeClass()
    {
        return MTSRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MTSRecipe t, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 2, 22);
        guiItemStacks.init(1, true, 20, 22);
        guiItemStacks.init(2, false, 79, 22);
        guiItemStacks.set(iIngredients);
    }

}
