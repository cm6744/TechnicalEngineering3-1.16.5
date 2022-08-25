package ten3.core.machine.pipe;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import ten3.core.machine.Pipe;
import ten3.lib.capability.item.ItemStorageWayFinding;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;

public class PipeTile extends CmTileMachine {

    public PipeTile(String name) {

        super(name);

        setCap(getCapacity(), FaceOption.OFF, FaceOption.BOTH, getCapacity());

    }

    @Override
    public Type typeOf() {
        return Type.CABLE;
    }

    @Override
    public LazyOptional<IEnergyStorage> crtLazyEne(Direction d) {
        return LazyOptional.empty();
    }

    @Override
    public LazyOptional<IItemHandler> crtLazyItm(Direction d) {
        return LazyOptional.of(() -> new ItemStorageWayFinding(d, this));
    }


    public int getCapacity() {

        return 6;

    }

    @Override
    public void update() {

        if(getTileAliveTime() % 5 == 0) {
            ((Pipe) getBlockState().getBlock()).update(world, pos);
            ItemStorageWayFinding.updateNet(this);
        }

    }

    @Override
    protected boolean can(Capability<?> cap, Direction d) {
        if(cap == CapabilityEnergy.ENERGY) {
            return false;
        }
        return super.can(cap, d);
    }

}
