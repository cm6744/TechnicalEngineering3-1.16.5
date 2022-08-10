package ten3.core.starlight;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import ten3.core.starlight.special.StarlightBase;
import ten3.init.template.DefItem;

public class SSeedItem extends DefItem {

    public void shrink(ItemUseContext context) {

        if(!context.getPlayer().isCreative()) {
            context.getItem().shrink(1);
        }

    }

    public ActionResultType onItemUse(ItemUseContext context) {

        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getPos());

        BlockState back = StarlightBase.matchCorrupt(world, context.getPos(), state);
        if(back != state) {
            world.setBlockState(context.getPos(), back);
            shrink(context);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;

    }

}
