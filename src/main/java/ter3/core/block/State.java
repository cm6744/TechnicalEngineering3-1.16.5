package ten3.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import ten3.init.BlockInit;
import ten3.init.template.DefBlock;

public class State extends DefBlock {

    public static IntegerProperty active = IntegerProperty.create("active", 0, 2);

    public State() {
        super(build(0, 0, Material.AIR, SoundType.SAND, ToolType.PICKAXE, -1, 0, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(active);
        builder.add(BlockStateProperties.FACING);
    }

    public static BlockState with(int p, Direction d) {

        State ins = (State) BlockInit.getBlock("state");
        BlockState state = ins.getDefaultState();
        state = state.with(active, p);
        state = state.with(BlockStateProperties.FACING, d);

        return state;

    }

}
