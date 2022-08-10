package ten3;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ten3.core.item.Spanner;
import ten3.core.network.ZConfigs;
import ten3.init.*;
import ten3.util.ItemUtil;

@Mod(TER.modid)
public class TechnicalEngineering {

    public static final DeferredRegister<Item>
            ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TER.modid);
    public static final DeferredRegister<Block>
            BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TER.modid);
    public static final DeferredRegister<TileEntityType<?>>
            TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TER.modid);
    public static final DeferredRegister<ContainerType<?>>
            CONS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TER.modid);

    public static final DeferredRegister<IRecipeSerializer<?>>
            RECIPES_SERIALS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TER.modid);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TER.modid);

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
