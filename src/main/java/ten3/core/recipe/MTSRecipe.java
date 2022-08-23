package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTSRecipe extends SingleRecipe {
    
    public List<CmItemList> ingredients;

    public MTSRecipe(ResourceLocation regName, ResourceLocation idIn,
                     List<CmItemList> ings, ItemStack resultIn, ItemStack add,
                     int cookTimeIn, int countOut, double cc) {

        super(regName, idIn, ings.get(0), resultIn, add, cookTimeIn, countOut, cc);
        this.ingredients = ings;
    }

    @Override
    public int time()
    {
        return time;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        List<ItemStack> invs = new ArrayList<>();
        for(int i = 0; i < ingredients.size(); i++) {
            invs.add(inv.getStackInSlot(i));
        }
        for(int i = 0; i < ingredients.size(); i++) {
            if(!ingredients.get(i).hasValidIn(invs.toArray(new ItemStack[0]))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> nonNullList = NonNullList.create();//fucking mojang extends AbstractList
        for(CmItemList lst : ingredients) {
            nonNullList.add(lst.vanillaIngre());
        }
        return nonNullList;
    }

    @Override
    public int inputLimit(ItemStack stack)
    {
        for(CmItemList lst : ingredients) {
            if(lst.hasValidIn(stack)) {
                return lst.limit;
            }
        }
        return 0;
    }

}
