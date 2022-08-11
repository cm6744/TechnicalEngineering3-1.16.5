package ten3.core.starlight;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import ten3.core.block.AeroliumOre;
import ten3.core.starlight.special.StarlightBase;
import ten3.core.starlight.special.StarlightCrystal;

import java.util.Random;

public class PoweredAeroliumOre extends StarlightBase {

    public PoweredAeroliumOre() {

        super(build(4, 4, Material.ROCK, SoundType.STONE, ToolType.PICKAXE, 3, 10, true));

    }

    public static IParticleData typeData = StarlightCrystal.typeData;

    @Override
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {

        worldIn.addParticle(typeData, pos.getX() + rand.nextDouble() * 5, pos.getY() + rand.nextDouble() * 5, pos.getZ() + rand.nextDouble() * 5, 0, 0, 0);
        AeroliumOre.spawnParticles(worldIn, pos, typeData);

    }

}
