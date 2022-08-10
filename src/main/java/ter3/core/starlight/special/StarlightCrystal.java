package ten3.core.starlight.special;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import ten3.core.starlight.CrystalItem;

import java.util.List;
import java.util.Random;

public class StarlightCrystal extends StarlightBase {

    VoxelShape shape = BushBlock.makeCuboidShape(3, 0, 3, 13, 13, 13);

    public StarlightCrystal() {
        super(build(0.1, 0.1, Material.GLASS, SoundType.GLASS, ToolType.PICKAXE, -1, 4, false));
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return useContext.getItem().isEmpty() || useContext.getItem().getItem() != asItem();
    }

    @Override
    public boolean isReplaceable(BlockState state, Fluid fluid) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        StarlightBase.ctick(worldIn, pos, random);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Lists.newArrayList();
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        for(int i = 0; i < 5; i++) {
            worldIn.addParticle(typeData, pos.getX()+0.5, pos.getY()+0.9, pos.getZ()+0.5, 0, 10, 0);
            //worldIn.addParticle(typeData, pos.getX()+0.5, pos.getY()+1.2, pos.getZ()+0.5, 0, 10, 0);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if(!CrystalItem.check(worldIn.getBlockState(pos.down()).getBlock())) {
            worldIn.destroyBlock(pos, true);
        }
    }

}
