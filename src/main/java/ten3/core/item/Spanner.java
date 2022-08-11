package ten3.core.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ten3.core.block.Machine;
import ten3.core.machine.cable.Cable;
import ten3.init.ItemInit;
import ten3.init.template.DefItem;
import ten3.util.ItemUtil;

import javax.annotation.Nullable;

public class Spanner extends DefItem {

    public static int modes = 4;

    public Spanner() {

        super(1);

    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {

        return (state.getBlock() instanceof Machine) || (state.getBlock() instanceof Cable) ? 16 : 1;

    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState state) {

        return (state.getBlock() instanceof Machine) || (state.getBlock() instanceof Cable) ? 3 : -1;

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if(playerIn.isSneaking() && handIn == Hand.MAIN_HAND && !worldIn.isRemote()) {
            int i = ItemUtil.getTag(playerIn.getHeldItemMainhand(), "mode");
            i++;
            if(i > 2) {
                i = 0;
            }
            ItemUtil.setTag(playerIn.getHeldItemMainhand(), "mode", i);
            return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
        }

        return ActionResult.resultFail(playerIn.getHeldItemMainhand());

    }

}
