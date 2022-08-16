package ten3.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WorkUtil {

    public static void runIn(int radius, BlockPos center, RunWithPos r) {

        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();

        int rr = radius % 2 == 0 ? radius - 1 : radius;

        for(int i = -rr; i < radius; i++) {
            for(int j = -rr; j < radius; j++) {
                for(int k = -rr; k < radius; k++) {
                    BlockPos pin = new BlockPos(x+i, y+j, z+k);
                    if(center.withinDistance(pin, radius)) {
                        if(r.run(pin)) {
                            return;
                        }
                    }
                }
            }
        }

    }

    public static void runInFlat(int radius, BlockPos center, RunWithPos r) {

        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();

        int rr = radius % 2 == 0 ? radius - 1 : radius;

        for(int i = -rr; i < radius; i++) {
            for(int k = -rr; k < radius; k++) {
                BlockPos pin = new BlockPos(x+i, y, z+k);
                if(center.withinDistance(pin, radius)) {
                    if(r.run(pin)) {
                        return;
                    }
                }
            }
        }

    }

    public static LootContext.Builder getLoot(World world, BlockPos pos) {
        return new LootContext.Builder((ServerWorld) world)
                .withRandom(world.rand)
                .withParameter(LootParameters.ORIGIN, Vector3d.copyCentered(pos))
                .withParameter(LootParameters.TOOL, ItemStack.EMPTY);
    }

    public interface RunWithPos {
        //is stopping
        boolean run(BlockPos pos);
    }

}
