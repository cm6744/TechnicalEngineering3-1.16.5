package ten3.lib.capability;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import ten3.core.machine.cable.CableTile;
import ten3.util.DireUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finder {

    @SuppressWarnings("all")
    public static <T> void find(Map<BlockPos, List<T>> nets, Map<BlockPos, Integer> caps,
                                 GetHandler<T> getter, GetCap capper, IsType isType,
                                 TileEntity start,
                                 List<T> init, HashMap<BlockPos, TileEntity> fouInit,
                                 Direction fr, int cap, boolean callFirst) {

        BlockPos pos = start.getPos();

        List<T> result = init != null ? init : new ArrayList();
        HashMap<BlockPos, TileEntity> found = fouInit != null ? fouInit : new HashMap<>();

        found.put(pos, start);

        for(Direction d : Direction.values()) {
            if(fr == d) continue;//if is the calling from dire, not run
            BlockPos p1 = pos.offset(d);
            TileEntity t = start.getWorld().getTileEntity(p1);
            if(t == null || found.get(p1) != null) continue;
            found.put(p1, t);
            T e = getter.get(t, DireUtil.safeOps(d));
            T e2 = getter.get(start, d);
            if(e == null || e2 == null) continue;//have no cap, next
            if(isType.is(t)) {
                cap = Math.min(capper.get(t), capper.get(start));
                find(nets, caps, getter, capper, isType, t, result, found, DireUtil.safeOps(d), cap, false);
            } else {
                result.add(e);
            }
        }

        if(callFirst) {
            nets.put(start.getPos(), result);
            caps.put(start.getPos(), cap);
        }

    }

    public interface GetHandler<T> {
        T get(TileEntity t, Direction d);
    }

    public interface GetCap {
        int get(TileEntity tile);
    }

    public interface IsType {
        boolean is(TileEntity tile);
    }

}
