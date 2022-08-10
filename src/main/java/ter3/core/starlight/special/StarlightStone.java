package ten3.core.starlight.special;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class StarlightStone extends StarlightBase {

    public StarlightStone() {

        super(build(1.5, 1.5, Material.ROCK, SoundType.STONE, ToolType.PICKAXE, 0, 0, true).tickRandomly());

    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if(Math.random() < 0.3) {
            StarlightBase.ctick(worldIn, pos, random);
        }
        StarlightBase.growCrystal(worldIn, pos, random);
    }

}
