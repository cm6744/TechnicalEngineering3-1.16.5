package ten3.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import ten3.init.template.DefBlock;

public class RedWidget extends DefBlock {

    public RedWidget() {
        super(build(0.3, 1, Material.IRON, SoundType.STONE, ToolType.PICKAXE, 0, 0, false));
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return super.getStrongPower(blockState, blockAccess, pos, side);
    }

}
