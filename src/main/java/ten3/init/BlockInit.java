package ten3.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import ten3.TechnicalEngineering;
import ten3.core.block.*;
import ten3.core.machine.Engine;
import ten3.core.machine.Machine;
import ten3.core.machine.cable.Cable;
import ten3.core.machine.cell.Cell;

import java.util.HashMap;
import java.util.Map;

public class BlockInit {

    static Map<String, RegistryObject<Block>> regs = new HashMap<>();
    public static Map<String, Block> blocks = new HashMap<>();

    public static void regAll() {
        //Protects:
        //regBlock("state", new State());

        regBlock("tin_ore", new OreCm(3));
        regBlock("copper_ore", new OreCm(2.5));
        regBlock("nickel_ore", new OreCm(4));

        regEngine("engine_extraction");
        regEngine("engine_metal");
        regEngine("engine_biomass");

        regMachine("smelter");
        regMachine("farm_manager");
        regMachine("pulverizer");
        regMachine("compressor");
        regMachine("beacon_simulator");
        regMachine("mob_ripper");
        regMachine("quarry");
        regMachine("psionicant");
        regMachine("induction_furnace");

        regCable("cable_glass", Material.GLASS, SoundType.GLASS);
        regCable("cable_golden", Material.IRON, SoundType.STONE);

        regCell("cell_glass", Material.GLASS, SoundType.GLASS);
        regCell("cell_golden", Material.IRON, SoundType.STONE);
    }

    public static void regMachine(String id_out) {

        String id = "machine_" + id_out;
        Machine m = new Machine(id);
        RegistryObject<Block> reg = TechnicalEngineering.BLOCKS.register(id, () -> m);
        regs.put(id, reg);
        blocks.put(id, m);

    }

    public static void regEngine(String id) {

        Engine m = new Engine(id);
        RegistryObject<Block> reg = TechnicalEngineering.BLOCKS.register(id, () -> m);
        regs.put(id, reg);
        blocks.put(id, m);

    }

    public static void regCell(String id, Material m, SoundType s) {

        Cell c = new Cell(m, s, id);
        RegistryObject<Block> reg = TechnicalEngineering.BLOCKS.register(id, () -> c);
        regs.put(id, reg);
        blocks.put(id, c);

    }

    public static void regCable(String id, Material m, SoundType s) {

        Cable c = new Cable(m, s, id);
        RegistryObject<Block> reg = TechnicalEngineering.BLOCKS.register(id, () -> c);
        regs.put(id, reg);
        blocks.put(id, c);

    }

    public static void regBlock(String id, Block im) {

        RegistryObject<Block> reg = TechnicalEngineering.BLOCKS.register(id, () -> im);
        regs.put(id, reg);
        blocks.put(id, im);

    }

    public static Block getBlock(String id) {

        return blocks.get(id);

    }

}
