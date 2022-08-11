package ten3.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import ten3.init.template.DefBlock;

import java.util.Random;

public class AeroliumOre extends OreCm {

    public AeroliumOre() {

        super(3);

    }

    private IParticleData typeData = new RedstoneParticleData(0.5f, 0.5f, 0.5f, 0.5f);

    @Override
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {

        worldIn.addParticle(typeData, pos.getX() + rand.nextDouble() * 5, pos.getY() + rand.nextDouble() * 5, pos.getZ() + rand.nextDouble() * 5, 0, 0, 0);
        spawnParticles(worldIn, pos, typeData);

    }

    public static void spawnParticles(World world, BlockPos pos, IParticleData type) {

        Random random = world.rand;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getXOffset() : (double)random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getYOffset() : (double)random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getZOffset() : (double)random.nextFloat();
                world.addParticle(type, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

}