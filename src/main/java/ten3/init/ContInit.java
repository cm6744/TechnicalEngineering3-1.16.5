package ten3.init;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ten3.TechnicalEngineering;
import ten3.core.machine.engine.EngineScreen;
import ten3.core.machine.engine.biomass.BiomassTile;
import ten3.core.machine.engine.extractor.ExtractorTile;
import ten3.core.machine.engine.metalizer.MetalizerTile;
import ten3.core.machine.cell.CellScreen;
import ten3.core.machine.cell.CellTile;
import ten3.core.machine.useenergy.beacon.BeaconScreen;
import ten3.core.machine.useenergy.beacon.BeaconTile;
import ten3.core.machine.useenergy.compressor.CompressorScreen;
import ten3.core.machine.useenergy.compressor.CompressorTile;
import ten3.core.machine.useenergy.farm.FarmScreen;
import ten3.core.machine.useenergy.farm.FarmTile;
import ten3.core.machine.useenergy.indfur.IndfurScreen;
import ten3.core.machine.useenergy.indfur.IndfurTile;
import ten3.core.machine.useenergy.mobrip.MobRipScreen;
import ten3.core.machine.useenergy.mobrip.MobRipTile;
import ten3.core.machine.useenergy.psionicant.PsionicantScreen;
import ten3.core.machine.useenergy.psionicant.PsionicantTile;
import ten3.core.machine.useenergy.pulverizer.PulverizerScreen;
import ten3.core.machine.useenergy.pulverizer.PulverizerTile;
import ten3.core.machine.useenergy.quarry.QuarryScreen;
import ten3.core.machine.useenergy.quarry.QuarryTile;
import ten3.core.machine.useenergy.smelter.FurnaceScreen;
import ten3.core.machine.useenergy.smelter.FurnaceTile;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreen;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.wrapper.IntArrayCm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContInit {

    static Map<String, RegistryObject<ContainerType<?>>> regs = new HashMap<>();
    public static Map<String, ContainerType<? extends CmContainerMachine>> cons = new HashMap<>();

    public static void regAll() {
        regCont(new ExtractorTile("engine_extraction"));
        regCont(new MetalizerTile("engine_metal"));
        regCont(new BiomassTile("engine_biomass"));

        regCont(new FurnaceTile("machine_smelter"));
        regCont(new FarmTile("machine_farm_manager"));
        regCont(new PulverizerTile("machine_pulverizer"));
        regCont(new CompressorTile("machine_compressor"));
        regCont(new BeaconTile("machine_beacon_simulator"));
        regCont(new MobRipTile("machine_mob_ripper"));
        regCont(new QuarryTile("machine_quarry"));
        regCont(new PsionicantTile("machine_psionicant"));
        regCont(new IndfurTile("machine_induction_furnace"));

        regCont(new CellTile("cell"));

    }

    public static IntArrayCm crt() {
        return new IntArrayCm(40);
    }

    public static void regCont(CmTileMachine f) {

        regCont(f.id, f);

    }

    public static void regCont(String id, CmTileMachine f) {

        ContainerType<? extends CmContainerMachine> type = IForgeContainerType.create((windowId, inv, data) ->
                new CmContainerMachine(windowId, inv, f, data.readBlockPos(), crt()));
        RegistryObject<ContainerType<?>> reg = TechnicalEngineering.CONS.register(id, () -> type);
        regs.put(id, reg);
        cons.put(id, type);

    }

    public static ContainerType<? extends CmContainerMachine> getType(String id) {

        return cons.get(id);

    }

    static List<String> translucent = new ArrayList<>();
    static List<String> cutout = new ArrayList<>();

    @SubscribeEvent
    @SuppressWarnings("all")
    public static void doBinding(FMLClientSetupEvent e) {

        translucent.add("cable");
        translucent.add("pipe");
        translucent.add("cell");

        cutout.add("engine_metal");
        cutout.add("engine_extraction");
        cutout.add("engine_biomass");

        bindScr("engine_metal", EngineScreen::new);
        bindScr("engine_extraction", EngineScreen::new);
        bindScr("engine_biomass", EngineScreen::new);

        bindScr("machine_smelter", FurnaceScreen::new);
        bindScr("machine_farm_manager", FarmScreen::new);
        bindScr("machine_pulverizer", PulverizerScreen::new);
        bindScr("machine_compressor", CompressorScreen::new);
        bindScr("machine_beacon_simulator", BeaconScreen::new);
        bindScr("machine_mob_ripper", MobRipScreen::new);
        bindScr("machine_quarry", QuarryScreen::new);
        bindScr("machine_psionicant", PsionicantScreen::new);
        bindScr("machine_induction_furnace", IndfurScreen::new);

        bindScr("cell", CellScreen::new);

        for(String s : translucent) {
            RenderTypeLookup.setRenderLayer(BlockInit.getBlock(s), RenderType.getTranslucent());
        }
        for(String s : cutout) {
            RenderTypeLookup.setRenderLayer(BlockInit.getBlock(s), RenderType.getCutout());
        }

    }

    @SuppressWarnings("all")
    private static<M extends Container, U extends Screen & IHasContainer<M>> void bindScr(String s, ScreenManager.IScreenFactory<M, U> fac) {
        ScreenManager.registerFactory((ContainerType<? extends M>) getType(s), fac);
    }

}
