package ten3.core.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ten3.init.RecipeInit;

public class SingleRecipe implements OpportunityRecipe<IInventory> {

    protected final ResourceLocation id;
    protected CmItemList ingredient;
    protected final ItemStack result;
    protected final int time;
    protected final ResourceLocation reg;
    protected final int count;
    protected final double chance;
    protected final ItemStack addition;

    public SingleRecipe(ResourceLocation regName, ResourceLocation idIn,
                        CmItemList ingredient, ItemStack resultIn, ItemStack add,
                        int cookTimeIn, int countOut, double cc) {

        this.id = idIn;
        this.ingredient = ingredient;
        this.result = resultIn;
        this.time = cookTimeIn;
        reg = regName;
        count = countOut;
        chance = cc;
        addition = add;

    }

    @Override
    public int time() {
        return time;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {

        return ingredient.test(inv.getStackInSlot(0));

    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {

        ItemStack s = getRecipeOutput().copy();
        s.setCount(count);

        return s;

    }

    @Override
    public ItemStack additionalOutput() {
        if(Math.random() < chance) {
            return addition.copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeInit.getRcpType(reg.getPath());
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeInit.getRcp(getId().getPath());
    }

    @Override
    public String condition() {
        return null;
    }

}
