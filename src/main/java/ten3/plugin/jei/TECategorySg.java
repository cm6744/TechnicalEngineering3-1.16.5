package ten3.plugin.jei;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import ten3.core.recipe.SingleRecipe;

public class TECategorySg extends TECategory<SingleRecipe> {

    public TECategorySg(String name, int ru, int rv)
    {
        super(name, 0, 0, 105, 60, ru, rv, 40, 23);
    }

    @Override
    public Class<? extends SingleRecipe> getRecipeClass()
    {
        return SingleRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SingleRecipe t, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 8, 22);
        guiItemStacks.init(1, false, 79, 22);
        guiItemStacks.set(iIngredients);
    }

}
