package ten3.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import ten3.TechnicalEngineering;
import ten3.core.machine.engine.biomass.BiomassTile;
import ten3.core.machine.engine.extractor.ExtractorTile;
import ten3.core.machine.engine.metalizer.MetalizerTile;
import ten3.core.machine.cable.CableTile;
import ten3.core.machine.cell.CellTile;
import ten3.core.machine.pipe.PipeTile;
import ten3.core.machine.useenergy.beacon.BeaconTile;
import ten3.core.machine.useenergy.compressor.CompressorTile;
import ten3.core.machine.useenergy.farm.FarmTile;
import ten3.core.machine.useenergy.indfur.IndfurTile;
import ten3.core.machine.useenergy.mobrip.MobRipTile;
import ten3.core.machine.useenergy.psionicant.PsionicantTile;
import ten3.core.machine.useenergy.pulverizer.PulverizerTile;
import ten3.core.machine.useenergy.quarry.QuarryTile;
import ten3.core.machine.useenergy.smelter.FurnaceTile;
import ten3.lib.tile.CmTileEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TileInit {

    static Map<String, RegistryObject<TileEntityType<?>>> regs = new HashMap<>();
    public static Map<String, TileEntityType<?>> tiles = new HashMap<>();

    public static void regAll() {
        regTile(() -> new ExtractorTile("engine_extraction"));
        regTile(() -> new MetalizerTile("engine_metal"));
        regTile(() -> new BiomassTile("engine_biomass"));

        regTile(() -> new FurnaceTile("machine_smelter"));
        regTile(() -> new FarmTile("machine_farm_manager"));
        regTile(() -> new PulverizerTile("machine_pulverizer"));
        regTile(() -> new CompressorTile("machine_compressor"));
        regTile(() -> new BeaconTile("machine_beacon_simulator"));
        regTile(() -> new MobRipTile("machine_mob_ripper"));
        regTile(() -> new QuarryTile("machine_quarry"));
        regTile(() -> new PsionicantTile("machine_psionicant"));
        regTile(() -> new IndfurTile("machine_induction_furnace"));

        regTile(() -> new CableTile("cable"));
        regTile(() -> new PipeTile("pipe"));
        regTile(() -> new CellTile("cell"));
    }

    public static void regTile(Supplier<CmTileEntity> im) {

        String id = im.get().id;
        regTile(id, im);

    }

    @Deprecated
    public static void regTile(String id, Supplier<CmTileEntity> im) {

        TileEntityType<?> type = TileEntityType.Builder.create(im, BlockInit.getBlock(id)).build(null);
        RegistryObject<TileEntityType<?>> reg = TechnicalEngineering.TILES.register(id, () -> type);
        regs.put(id, reg);
        tiles.put(id, type);

    }

    public static TileEntityType<?> getType(String id) {

        return tiles.get(id);

    }

}
