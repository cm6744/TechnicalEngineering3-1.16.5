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

public class OreCm extends DefBlock {

    public OreCm(double hs) {

        super(build(hs, hs, Material.ROCK, SoundType.STONE, ToolType.PICKAXE, 2, 0, true));

    }

}
