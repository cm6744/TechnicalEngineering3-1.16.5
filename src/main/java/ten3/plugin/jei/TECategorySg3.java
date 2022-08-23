package ten3.plugin.jei;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import ten3.core.recipe.MTSRecipe;

public class TECategorySg3 extends TECategory<MTSRecipe> {

    public TECategorySg3(String name, int ru, int rv)
    {
        super(name, 0, 128, 105, 60, ru, rv, 45, 23);
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
        guiItemStacks.init(0, true, 11, 13);
        guiItemStacks.init(1, true, 2, 31);
        guiItemStacks.init(2, true, 20, 31);
        guiItemStacks.init(3, false, 79, 22);
        guiItemStacks.set(iIngredients);
    }

}
