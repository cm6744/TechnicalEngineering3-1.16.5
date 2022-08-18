package ten3.init;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.VanillaIngredientSerializer;
import net.minecraftforge.fml.RegistryObject;
import ten3.TConst;
import ten3.TechnicalEngineering;
import ten3.core.recipe.*;

import java.util.HashMap;
import java.util.Map;

public class RecipeInit {

    static Map<String, RegistryObject<IRecipeSerializer<?>>> regs = new HashMap<>();
    public static Map<String, IRecipeSerializer<?>> serials = new HashMap<>();
    public static Map<String, IRecipeType<IRecipe<IInventory>>> types = new HashMap<>();

    public static void regAll() {

        regRcp(new SingleSerial<>(SingleRecipe::new, "pulverizer"));
        regRcp(new SingleSerial<>(SingleRecipe::new, "compressor"));
        regRcp(new MTSSerial<>(MTSRecipe::new, "psionicant"));
    }

    public static void regRcp(CmSerializer<?> s) {

        String id = s.id();
        RegistryObject<IRecipeSerializer<?>> reg = TechnicalEngineering.RECIPES_SERIALS.register(id, () -> s);
        regs.put(id, reg);
        serials.put(id, s);

        IRecipeType<IRecipe<IInventory>> reg2 = Registry.register(
                Registry.RECIPE_TYPE,
                new ResourceLocation(TConst.modid, id),
                new IRecipeType<IRecipe<IInventory>>() {
                    @Override
                    public String toString() {
                        return id;
                    }
                });

        types.put(id, reg2);

        CraftingHelper.register(new ResourceLocation("ten3:" + id), new VanillaIngredientSerializer());

    }

    public static IRecipeSerializer<?> getRcp(String id) {

        return serials.get(id);

    }

    public static IRecipeType<IRecipe<IInventory>> getRcpType(String id) {

        return types.get(id);

    }

}
