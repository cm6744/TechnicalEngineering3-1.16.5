package ten3.core.starlight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ten3.core.starlight.special.*;
import ten3.init.template.DefItem;

public class CrystalItem extends DefItem {

    Block cropBlock = StarlightBase.cry;

    public void shrink(ItemUseContext context) {

        if(!context.getPlayer().isCreative()) {
            context.getItem().shrink(1);
        }

    }

    public ActionResultType onItemUse(ItemUseContext context) {

        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getPos());

        if(canPlace(new BlockItemUseContext(context), state.getBlock(), context.getPos(), world)) {
            BlockState s = cropBlock.getStateForPlacement(new BlockItemUseContext(context));
            s = s != null ? s : cropBlock.getDefaultState();
            world.setBlockState(context.getPos().up(), s, 0);
            shrink(context);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;

    }

    public boolean canPlace(BlockItemUseContext context, Block block, BlockPos pos, World world) {

        if(!world.getFluidState(pos.up()).isEmpty()) {
            return false;
        }

        if(!world.getBlockState(pos.up()).isReplaceable(context)) {
            return false;
        }

        return check(block);

    }

    public static boolean check(Block block) {

        return (block instanceof StarlightStone
                || block instanceof StarlightGrassBlock
                || block instanceof StarlightSand
                || block instanceof PoweredAeroliumOre
                || block instanceof StarlightLeave
                || block instanceof StarlightLog
                );

    }

}
