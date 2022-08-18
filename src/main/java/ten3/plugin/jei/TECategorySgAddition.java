package ten3.plugin.jei;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.core.recipe.SingleRecipe;

public class TECategorySgAddition extends TECategory<SingleRecipe> {

    public TECategorySgAddition(String name, int ru, int rv)
    {
        super(name, 0, 64, 105, 60, ru, rv, 34, 23);
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
        guiItemStacks.init(1, false, 65, 13);
        guiItemStacks.init(2, false, 83, 13);
        guiItemStacks.init(3, false, 65, 31);
        guiItemStacks.init(4, false, 83, 31);
        guiItemStacks.set(iIngredients);
    }

}
