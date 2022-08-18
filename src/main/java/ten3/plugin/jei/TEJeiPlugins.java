package ten3.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import ten3.TConst;
import ten3.core.machine.useenergy.compressor.CompressorScreen;
import ten3.core.machine.useenergy.psionicant.PsionicantScreen;
import ten3.core.machine.useenergy.pulverizer.PulverizerScreen;
import ten3.core.machine.useenergy.smelter.FurnaceScreen;
import ten3.init.ItemInit;
import ten3.init.RecipeInit;

@JeiPlugin
public class TEJeiPlugins implements IModPlugin {

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        addRecipe(registration, "pulverizer");
        addRecipe(registration, "compressor");
        addRecipeSM(registration, "smelter");
        addRecipe(registration, "psionicant");
    }

    private void addRecipe(IRecipeRegistration registration, String name) {
        RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();
        IRecipeType<? extends IRecipe<IInventory>> type = RecipeInit.getRcpType(name);
        registration.addRecipes(manager.getRecipesForType(type), new ResourceLocation(TConst.modid, name));
    }

    private void addRecipeSM(IRecipeRegistration registration, String name) {
        RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();
        IRecipeType<? extends IRecipe<IInventory>> type = IRecipeType.SMELTING;
        registration.addRecipes(manager.getRecipesForType(type), new ResourceLocation(TConst.modid, name));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new TECategorySgAddition( "pulverizer", 27, 32));
        registration.addRecipeCategories(new TECategorySg( "compressor", 27, 63));
        registration.addRecipeCategories(new TECategorySmelt("smelter", 27, 0));
        registration.addRecipeCategories(new TECategorySgMTS("psionicant", 27, 0));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        addArea(registration, PulverizerScreen.class, "pulverizer");
        addArea(registration, CompressorScreen.class, "compressor");
        addArea(registration, FurnaceScreen.class, "smelter");
        addArea(registration, PsionicantScreen.class, "psionicant");
    }

    private void addArea(IGuiHandlerRegistration registration, Class<? extends ContainerScreen<?>> clazz, String name) {
        registration.addRecipeClickArea(clazz, 76, 35, 22, 16,
                new ResourceLocation(TConst.modid, name));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        addCatalyst(registration, "pulverizer");
        addCatalyst(registration, "compressor");
        addCatalyst(registration, "smelter");
        addCatalyst(registration, "psionicant");
    }

    private void addCatalyst(IRecipeCatalystRegistration registration, String name) {
        registration.addRecipeCatalyst(ItemInit.getItem("machine_" + name).getDefaultInstance(),
                new ResourceLocation(TConst.modid, name));
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation("jei", TConst.modid);
    }

}
