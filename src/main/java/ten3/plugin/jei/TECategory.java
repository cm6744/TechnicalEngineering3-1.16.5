package ten3.plugin.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import ten3.core.recipe.OpportunityRecipe;

import java.util.ArrayList;
import java.util.List;

public abstract class TECategory<T extends OpportunityRecipe<IInventory>> extends CmCtgr<T> {

    public TECategory(String name, int u, int v, int w, int h, int ru, int rv, int rx, int ry)
    {
        super(name, u, v, w, h, ru, rv, rx, ry);
    }

    @Override
    public void setIngredients(T t, IIngredients iIngredients)
    {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.addAll(t.getIngredients());
        appendListIn(ingredientList, t);
        List<Ingredient> removeLst = new ArrayList<>();
        for(Ingredient ing : ingredientList) {
            for(ItemStack stack : ing.getMatchingStacks()) {
                if(stack.isEmpty()) {
                    removeLst.add(ing);
                }
            }
        }
        ingredientList.removeAll(removeLst);
        iIngredients.setInputIngredients(ingredientList);

        List<ItemStack> so = t.output();
        if(!t.getAdditionOutput().isEmpty()) {
            so.add(t.getAdditionOutput());//must not be empty
        }
        appendListOut(so, t);
        iIngredients.setOutputs(VanillaTypes.ITEM, so);
    }

    public void appendListIn(List<Ingredient> ing, T re) {}

    public void appendListOut(List<ItemStack> ing, T re) {}

    @Override
    public void draw(T recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        ((ProcessDraw) dr).cacheTime(recipe.time());
        ((ProcessDraw) dr).cacheOpt(recipe.chance());//must cache each time
        dr.draw(matrixStack, 0, 0);
    }

}
