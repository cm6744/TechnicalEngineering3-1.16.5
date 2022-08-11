package ten3.lib.capability.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DireUtil;

import static ten3.lib.tile.CmTileMachine.energy;

@SuppressWarnings("all")
public class EnergyTransferor {

    final CmTileMachine t;

    public EnergyTransferor(CmTileMachine t) {
        this.t = t;
    }

    public static IEnergyStorage handlerOf(TileEntity e, Direction d) {

        return e.getCapability(CapabilityEnergy.ENERGY, d).orElse(null);

    }

    private TileEntity checkTile(Direction d) {

        return checkTile(t.getPos().offset(d));

    }

    private TileEntity checkTile(BlockPos pos) {

        return t.getWorld().getTileEntity(pos);

    }

    public void transferTo(BlockPos p, Direction d, int v) {

        if(FaceOption.isPassive(t.direCheckEnergy(d))) return;
        if(!FaceOption.isOut(t.direCheckEnergy(d))) return;

        TileEntity tile = checkTile(p);

        if(tile != null) {
            IEnergyStorage e = handlerOf(tile, DireUtil.safeOps(d));
            if(e == null) return;
            if(e.canReceive()) {
                int diff = e.receiveEnergy(Math.min(v, t.data.get(energy)), false);
                if(diff != 0) {
                    t.data.translate(energy, -diff);
                    t.markDirty();
                }
            }
        }

    }

    public void transferFrom(BlockPos p, Direction d, int v) {

        if(FaceOption.isPassive(t.direCheckEnergy(d))) return;
        if(!FaceOption.isIn(t.direCheckEnergy(d))) return;

        TileEntity tile = checkTile(p);

        if(tile != null) {
            IEnergyStorage e = handlerOf(tile, DireUtil.safeOps(d));
            if(e == null) return;
            if(e.canExtract()) {
                int diff = e.extractEnergy(Math.min(v, t.maxStorage - t.data.get(energy)), false);
                if(diff != 0) {
                    t.data.translate(energy, diff);
                    t.markDirty();
                }
            }
        }

    }

    public void transferTo(Direction d, int v) {

        transferTo(t.getPos().offset(d), d, v);

    }

    public void transferFrom(Direction d, int v) {

        transferFrom(t.getPos().offset(d), d, v);

    }

    public int getSizeCan() {

        int size = 0;

        TileEntity tile;

        for(Direction d : Direction.values()) {
            tile = checkTile(d);
            if(tile != null) {
                if(handlerOf(tile, DireUtil.safeOps(d)) != null) {
                    size++;
                }
            }
        }

        return size;

    }

    public void loopTransferTo(int v) {

        int size = getSizeCan();

        int k = Math.min(v, t.data.get(energy));
        if(v < size || size == 0) return;

        for(Direction d : Direction.values()) {
            if(checkTile(d) != null) {
                transferTo(d, k / size);
            }
        }

    }

    public void loopTransferFrom(int v) {

        int size = getSizeCan();

        int k = Math.min(v, t.maxStorage - t.data.get(energy));
        if(v < size || size == 0) return;

        for(Direction d : Direction.values()) {
            if(checkTile(d) != null) {
                transferFrom(d, k / size);
            }
        }

    }

}
