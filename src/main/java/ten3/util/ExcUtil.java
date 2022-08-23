package ten3.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class ExcUtil {

    public static String regNameOf(IForgeRegistryEntry<?> entry) {

        return Objects.requireNonNull(entry.getRegistryName()).getPath();

    }

    public static<C extends IInventory, T extends IRecipe<C>> Optional<T> safeGetRecipe(World world, IRecipeType<T> type, C i) {

        if(world == null) return Optional.empty();

        RecipeManager rm = world.getRecipeManager();
        return rm.getRecipe(type, i, world);

    }

    public static Collection<? extends IRecipe<IInventory>> safeGetRecipes(World world, IRecipeType<? extends IRecipe<IInventory>> type) {

        if(world == null) return new ArrayList<>();

        RecipeManager rm = world.getRecipeManager();
        return rm.getRecipesForType(type);

    }

    public static boolean hasRcpUseThisItem(World world, IRecipeType<? extends IRecipe<IInventory>> type, ItemStack stack) {
        return hasRcpUseThisItem(world, type, safeGetRecipes(world, type), stack);
    }

    public static boolean hasRcpUseThisItem(World world, IRecipeType<? extends IRecipe<IInventory>> type, Collection<? extends IRecipe<IInventory>> recipes, ItemStack stack) {
        final boolean[] ret = new boolean[1];
        recipes.forEach((r) -> {
            NonNullList<Ingredient> ings = r.getIngredients();
            ings.forEach((i) -> {
                if(i.test(stack)) {
                    ret[0] = true;
                }
            });
        });
        return ret[0];
    }

    public static Collection<IRecipe<IInventory>> getRcpUseThisItem(World world, IRecipeType<? extends IRecipe<IInventory>> type, ItemStack stack) {
        Collection<? extends IRecipe<IInventory>> recipes = ExcUtil.safeGetRecipes(world, type);
        Collection<IRecipe<IInventory>> ret = new ArrayList<>();
        recipes.forEach((r) -> {
            NonNullList<Ingredient> ings = r.getIngredients();
            ings.forEach((i) -> {
                if(i.test(stack)) {
                    ret.add(r);
                }
            });
        });
        return ret;
    }

    static Random random = new Random();

    @SuppressWarnings("all")
    public static <T> T randomInCollection(Collection<T> col) {

        Object[] items = col.toArray();
        Object j = Util.getRandomObject(items, random);
        return (T) j;

    }

    public static int safeInt(Integer i) {

        if(i == null) return 0;

        return i;

    }

}
