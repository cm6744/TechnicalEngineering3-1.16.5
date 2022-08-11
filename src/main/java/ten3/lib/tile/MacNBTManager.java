package ten3.lib.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import ten3.lib.wrapper.IntArrayCm;

import java.util.Map;

import static ten3.lib.tile.CmTileMachine.*;

public class MacNBTManager {

    CmTileMachine t;
    IntArrayCm data;
    IntArrayCm energyAllow;
    IntArrayCm itemAllow;

    public MacNBTManager(CmTileMachine tile) {
        t = tile;
        data = t.data;
        energyAllow = t.energyAllow;
        itemAllow = t.itemAllow;
    }

    public void rdt(CompoundNBT nbt) {

        data.set(progress, nbt.getInt("progress"));
        data.set(maxProgress, nbt.getInt("maxProgress"));
        data.set(energy, nbt.getInt("energy"));
        data.set(maxEnergy, nbt.getInt("maxEnergy"));
        data.set(fuel, nbt.getInt("fuel"));
        data.set(maxFuel, nbt.getInt("maxFuel"));

        data.set(redMode, nbt.getInt("redstoneMode"));

        readNBTUpg(nbt);

        for(int i = 0; i < energyAllow.size(); i++)
            energyAllow.set(i, nbt.getInt("direEnergy" + i));
        for(int i = 0; i < itemAllow.size(); i++)
            itemAllow.set(i, nbt.getInt("direItem" + i));

        t.initialFacing = nbt.getInt("face");

    }

    public void wdt(CompoundNBT nbt) {

        nbt.putInt("progress", data.get(progress));
        nbt.putInt("maxProgress", data.get(maxProgress));
        nbt.putInt("energy", data.get(energy));
        nbt.putInt("maxEnergy", data.get(maxEnergy));
        nbt.putInt("fuel", data.get(fuel));
        nbt.putInt("maxFuel", data.get(maxFuel));

        nbt.putInt("redstoneMode", data.get(redMode));

        writeNBTUpg(nbt);

        for(int i = 0; i < energyAllow.size(); i++)
            nbt.putInt("direEnergy" + i, energyAllow.get(i));
        for(int i = 0; i < itemAllow.size(); i++)
            nbt.putInt("direItem" + i, itemAllow.get(i));

        nbt.putInt("face", t.initialFacing);

    }

    public void writeNBTUpg(CompoundNBT nbt) {

        nbt.putInt("level", t.levelIn);

    }

    public void readNBTUpg(CompoundNBT nbt) {

        t.levelIn = nbt.getInt("level");

    }

}
