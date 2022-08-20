package ten3.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import ten3.TechnicalEngineering;
import ten3.core.item.energy.BlockItemFEStorage;
import ten3.core.item.energy.ItemFEStorage;
import ten3.core.item.upgrades.*;
import ten3.core.item.*;
import ten3.init.template.DefItem;
import ten3.init.template.DefItemBlock;
import ten3.init.template.InvisibleItem;
import ten3.lib.tile.PacketCapData;

import java.util.HashMap;
import java.util.Map;

import static ten3.lib.tile.CmTileMachine.kFE;

public class ItemInit {

    static Map<String, RegistryObject<Item>> regs = new HashMap<>();
    public static Map<String, Item> items = new HashMap<>();

    public static void regAll() {

        //Protected:
        regItem("pedia", new InvisibleItem());
        regItem("technical_item", new InvisibleItem());
        regItem("technical_block", new InvisibleItem());

        regItem("spanner", new Spanner());
        regItem("energy_capacity", new ItemFEStorage(kFE(500), kFE(5), kFE(5)));

        //produced things
        //regItemDef("energy_core");
       // regItemDef("machine_frame");
        regItemDef("redstone_conductor");
        regItemDef("redstone_converter");
        regItemDef("redstone_storer");
        regItemDef("bizarrerie");
        //too imba
        //regItem("world_bag", new WorldBag());

        //base materials
        regPairMetal("iron", true);
        regPairMetal("gold", true);
        regPairMetal("copper", false);
        regPairMetal("tin", false);
        regPairMetal("nickel", false);
        regPairMetal("powered_tin", false);
        regPairMetal("chlorium", false);
        regItemDef("starlight_dust");

        //upgrades
        regItem("augmented_levelup", new LevelupAug());
        regItem("powered_levelup", new LevelupPower());
        regItem("relic_levelup", new LevelupAnc());

        //ores
        regItemBlockDef("tin_ore");
        regItemBlockDef("copper_ore");
        regItemBlockDef("nickel_ore");

        //machines
        regItemMachineWithoutID("engine_extraction");
        regItemMachineWithoutID("engine_metal");
        regItemMachineWithoutID("engine_biomass");
        regItemMachine("smelter");
        regItemMachine("farm_manager");
        regItemMachine("pulverizer");
        regItemMachine("compressor");
        regItemMachine("beacon_simulator");
        regItemMachine("mob_ripper");
        regItemMachine("quarry");
        regItemMachine("psionicant");

        regItemMachineWithoutID("cell_glass");
        regItemMachineWithoutID("cell_golden");

        regItemBlockDef("cable_glass");
        regItemBlockDef("cable_golden");

    }

    public static void regPairMetal(String id, boolean vanilla) {

        if(!vanilla) {
            regItemDef(id + "_ingot");
        }

        regItemDef(id + "_dust");
        regItemDef(id + "_plate");
        regItemDef(id + "_gear");

    }

    public static void regItemBlockDef(String id) {

        regItem(id, new DefItemBlock(BlockInit.getBlock(id)));

    }

    public static void regItemMachine(String id) {
        String idi = "machine_" + id;
        regItem(idi, new BlockItemFEStorage(BlockInit.getBlock(idi)));
    }
    public static void regItemMachineWithoutID(String id) {
        regItem(id, new BlockItemFEStorage(BlockInit.getBlock(id)));
    }

    public static void regItemDef(String id) {

        regItem(id, new DefItem());

    }

    public static void regItem(String id, Item im) {

        RegistryObject<Item> reg = TechnicalEngineering.ITEMS.register(id, () -> im);
        regs.put(id, reg);
        items.put(id, im);

    }

    public static Item getItem(String id) {

        return items.get(id);

    }

}
