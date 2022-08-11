package ten3.core.machine.cell;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCm;

public class CellTile extends CmTileMachine {

    public CellTile(String name) {

        super(name);

        setCap(getCapacity() / 1000, getCapacity() / 1000, getCapacity(), FaceOption.BE_IN, FaceOption.OFF, 0);

        addSlot(new SlotCm(inventory, 0, 61, 46, null, true, true));
        addSlot(new SlotCm(inventory, 1, 97, 46, null, true, true));

    }

    @Override
    public Type typeOf() {
        return Type.CELL;
    }

    public int getCapacity() {

        return kFE(500);

    }

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        ItemStack stack0 = inventory.getStackInSlot(0);
        ItemStack stack1 = inventory.getStackInSlot(1);

        if(stack0.getCount() == 1) {
            stack0.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (e) -> {
                        if(e.canExtract()) {
                            int diff = e.extractEnergy(Math.min(maxReceive, maxStorage - data.get(energy)), false);
                            if(diff != 0) {
                                data.translate(energy, diff);
                                markDirty();
                            }
                        }
                    }
            );
        }

        if(stack1.getCount() == 1) {
            stack1.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (e) -> {
                        if(e.canReceive()) {
                            int diff = e.receiveEnergy(Math.min(maxExtract, data.get(energy)), false);
                            if(diff != 0) {
                                data.translate(energy, -diff);
                                markDirty();
                            }
                        }
                    }
            );
        }

    }

}
