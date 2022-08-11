package ten3.core.starlight.special;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import ten3.util.KeyUtil;
import ten3.util.ExcUtil;

import java.util.List;
import java.util.Random;

import static ten3.init.template.DefBlock.build;

//special:don't extend
public class StarlightSand extends FallingBlock {

    public StarlightSand() {

        super(build(0.5, 0.5, Material.SAND, SoundType.SAND, ToolType.SHOVEL, -1, 0, true).tickRandomly());

    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        StarlightBase.ctick(worldIn, pos, random);
        StarlightBase.growCrystal(worldIn, pos, random);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public String getTranslationKey() {
        return KeyUtil.getKey(ExcUtil.regNameOf(this));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return StarlightBase.dropGet();
    }

}
