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
        for(ItemStack i : t.input()) {
            ingredientList.add(Ingredient.fromStacks(i));
        }
        if(hasNoEmpty(t.input()))
        iIngredients.setInputIngredients(ingredientList);

        List<ItemStack> so = t.output();
        if(!t.getAdditionOutput().isEmpty()) {
            so.add(t.getAdditionOutput());//must not be empty
        }
        if(hasNoEmpty(t.output()))
        iIngredients.setOutputs(VanillaTypes.ITEM, so);
    }

    @Override
    public void draw(T recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        ((ProcessDraw) dr).cacheTime(recipe.time());
        ((ProcessDraw) dr).cacheOpt(recipe.chance());//must cache each time
        dr.draw(matrixStack, 0, 0);
    }

}
