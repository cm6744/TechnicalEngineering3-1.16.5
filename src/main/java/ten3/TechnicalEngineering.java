package ten3;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ten3.core.network.ZConfigs;
import ten3.init.*;

@Mod(TConst.modid)
public class TechnicalEngineering {

    public static final DeferredRegister<Item>
            ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TConst.modid);
    public static final DeferredRegister<Block>
            BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TConst.modid);
    public static final DeferredRegister<TileEntityType<?>>
            TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TConst.modid);
    public static final DeferredRegister<ContainerType<?>>
            CONS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TConst.modid);

    public static final DeferredRegister<IRecipeSerializer<?>>
            RECIPES_SERIALS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TConst.modid);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TConst.modid);

    public TechnicalEngineering() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ZConfigs.config);

        BlockInit.regAll();//reg block firstly, or itemBlock will throw npe
        TileInit.regAll();
        ContInit.regAll();

        ItemInit.regAll();
        RecipeInit.regAll();
        SoundInit.regAll();

        BLOCKS.register(bus);
        TILES.register(bus);
        CONS.register(bus);

        ITEMS.register(bus);
        RECIPES_SERIALS.register(bus);
        SOUNDS.register(bus);

    }

}
