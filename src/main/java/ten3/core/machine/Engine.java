package ten3.core.machine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class Engine extends Machine {

    static VoxelShape bound = Block.makeCuboidShape(0, 0, 0, 16, 9, 16);

    public Engine(String name) {

        this(false, name);

    }

    public Engine(boolean solid, String name) {

        this(Material.IRON, SoundType.STONE, name, solid);

    }

    public Engine(Material m, SoundType s, String name, boolean solid) {

        super(m, s, solid, name);

    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return bound;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return bound;
    }

}
