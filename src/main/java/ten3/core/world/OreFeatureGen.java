package ten3.core.world;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import ten3.TER;
import ten3.init.BlockInit;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TER.modid, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreFeatureGen {

    public static final ConfiguredFeature<?, ?> tin_ore = register(
            "tin_ore",
            Feature.ORE.withConfiguration(
                    new OreFeatureConfig(
                            OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                            BlockInit.getBlock("tin_ore").getDefaultState(),
                            8
                    )
            )
                    .range(36)
                    .square()
                    .count(9)
    );
    public static final ConfiguredFeature<?, ?> copper_ore = register(
            "copper_ore",
            Feature.ORE.withConfiguration(
                            new OreFeatureConfig(
                                    OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                                    BlockInit.getBlock("copper_ore").getDefaultState(),
                                    8
                            )
                    )
                    .range(54)
                    .square()
                    .count(12)
    );
    //as rare as emerand
    public static final ConfiguredFeature<?, ?> nickel_ore = register(
            "nickel_ore",
            Feature.ORE.withConfiguration(
                            new OreFeatureConfig(
                                    OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                                    BlockInit.getBlock("nickel_ore").getDefaultState(),
                                    8
                            )
                    )
                    .range(16)
                    .square()
    );


    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature){
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder genEvent = event.getGeneration();
        RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName()));

        if (BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.OVERWORLD)) {

            genEvent.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, tin_ore);
            genEvent.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, copper_ore);
            genEvent.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, nickel_ore);

        }
    }

}
