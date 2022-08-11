package ten3.lib.capability.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import ten3.core.machine.cable.CableTile;
import ten3.lib.capability.Finder;
import ten3.lib.tile.CmTileMachine;
import ten3.util.DireUtil;

import java.util.*;

//connect generator with machine.
//#find - return a list of all connections
public class FEStorageWayFinding extends FEStorageTile {

    public static Map<BlockPos, List<IEnergyStorage>> nets = new HashMap<>();
    public static Map<BlockPos, Integer> caps = new HashMap<>();

    public static void updateNet(CableTile tile) {
        find(tile);
    }

    public FEStorageWayFinding(Direction d, CmTileMachine t) {

        super(d, t);

    }

    @Override
    public int receiveEnergy(int receive, boolean simulate) {
        if(canReceive()) {
            return listTransferTo(nets.get(tile.getPos()), receive);
        }
        return 0;
    }

    @Override
    public int extractEnergy(int extract, boolean simulate) {
        if(canExtract()) {
            return listTransferFrom(nets.get(tile.getPos()), extract);
        }
        return 0;
    }

    @Override
    public boolean canReceive() {
        return tile.checkCanRun() && tile.maxReceive > 0;
    }

    @Override
    public boolean canExtract() {
        return tile.checkCanRun() && tile.maxExtract > 0;
    }

    @Override
    public int getEnergyStored() {
        return listGetStored(nets.get(tile.getPos()));
    }

    @Override
    public int getMaxEnergyStored() {
        return listGetStoredMax(nets.get(tile.getPos()));
    }

    @SuppressWarnings("all")
    private static void find(CableTile start, List<IEnergyStorage> init, HashMap<BlockPos, TileEntity> fouInit, Direction fr, int cap, boolean callFirst) {

        Finder.find(nets, caps,
                (t, d) -> EnergyTransferor.handlerOf(t, d),
                (t) -> ((CableTile) t).getCapacity(),
                (t) -> t instanceof CableTile,
                start, init, fouInit, fr, cap, callFirst);

    }

    private static void find(CableTile start) {
        find(start, null, null, null, Integer.MAX_VALUE, true);
    }

    public int listGetStored(List<IEnergyStorage> es) {

        int total = 0;

        if(es == null) return 0;

        for(IEnergyStorage e : es) {
            int diff = e.getEnergyStored();
            total += diff;
        }

        return total;

    }

    public int listGetStoredMax(List<IEnergyStorage> es) {

        int total = 0;

        if(es == null) return 0;

        for(IEnergyStorage e : es) {
            int diff = e.getMaxEnergyStored();
            total += diff;
        }

        return total;

    }


    public int listTransferTo(List<IEnergyStorage> es, int v) {

        int size = getSizeCanTrsIn(es);

        if(es == null) return 0;
        if(v < size || size == 0) return 0;

        int total = 0;

        for(IEnergyStorage e : es) {
            if(!e.canReceive()) continue;
            int diff = e.receiveEnergy(Math.min(caps.get(tile.getPos()), v / size), false);
            total += diff;
        }

        return total;

    }

    public int listTransferFrom(List<IEnergyStorage> es, int v) {

        int size = getSizeCanTrsOut(es);

        if(es == null) return 0;
        if(v < size || size == 0) return 0;

        int total = 0;

        for(IEnergyStorage e : es) {
            if(!e.canExtract()) continue;
            int diff = e.extractEnergy(Math.min(caps.get(tile.getPos()), v / size), false);
            total += diff;
        }

        return total;

    }

    public int getSizeCanTrsOut(List<IEnergyStorage> es) {
        int size = 0;
        if(es == null) return 0;
        for(IEnergyStorage e : es) {
            if(e.canExtract()) {
                size++;
            }
        }
        return size;
    }

    public int getSizeCanTrsIn(List<IEnergyStorage> es) {
        int size = 0;
        if(es == null) return 0;
        for(IEnergyStorage e : es) {
            if(e.canReceive()) {
                size++;
            }
        }
        return size;
    }

}
