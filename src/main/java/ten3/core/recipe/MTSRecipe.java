package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTSRecipe extends SingleRecipe {
    
    public CmItemList ingredient2;
    public int ing1c;
    public int ing2c;//TODO
    
    public MTSRecipe(ResourceLocation regName, ResourceLocation idIn,
                     CmItemList ingredient, CmItemList ingredient2, ItemStack resultIn, ItemStack add,
                     int cookTimeIn, int countOut, double cc) {

        super(regName, idIn, ingredient, resultIn, add, cookTimeIn, countOut, cc);
        this.ingredient2 = ingredient2;
    }

    @Override
    public int time()
    {
        return time;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        return (ingredient.hasValidIn(inv.getStackInSlot(0))
                && ingredient2.hasValidIn(inv.getStackInSlot(1)))
                || (ingredient.hasValidIn(inv.getStackInSlot(1))
                && ingredient2.hasValidIn(inv.getStackInSlot(0)));

    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return NonNullList.from(Ingredient.EMPTY, ingredient.vanillaIngre(), ingredient2.vanillaIngre());
    }

    @Override
    public int inputLimit(ItemStack stack)
    {
        if(ingredient.hasValidIn(stack))
        return ingredient.limit;
        else
        return ingredient2.limit;
    }

}
