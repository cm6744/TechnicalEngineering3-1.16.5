package ten3.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.Util;
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
