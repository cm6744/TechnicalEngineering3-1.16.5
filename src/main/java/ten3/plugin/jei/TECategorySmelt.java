package ten3.plugin.jei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.crafting.FurnaceRecipe;

public class TECategorySmelt extends CmCtgr<FurnaceRecipe> {

    public TECategorySmelt(String name, int ru, int rv)
    {
        super(name, 0, 0, 105, 60, ru, rv, 40, 23);
    }

    @Override
    public void setIngredients(FurnaceRecipe t, IIngredients iIngredients)
    {
        iIngredients.setInputIngredients(t.getIngredients());
        iIngredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(t.getRecipeOutput().copy()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, FurnaceRecipe t, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 8, 22);
        guiItemStacks.init(1, false, 79, 22);
        guiItemStacks.set(iIngredients);
    }

    @Override
    public void draw(FurnaceRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        ((ProcessDraw) dr).cacheTime(recipe.getCookTime());
        dr.draw(matrixStack, 0, 0);
    }

    @Override
    public Class<? extends FurnaceRecipe> getRecipeClass()
    {
        return FurnaceRecipe.class;
    }

}
