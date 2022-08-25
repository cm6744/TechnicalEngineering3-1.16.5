package ten3.core.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import ten3.core.machine.Machine;
import ten3.core.machine.Cable;
import ten3.init.template.DefItem;
import ten3.util.ItemUtil;

import javax.annotation.Nullable;

public class Spanner extends DefItem {

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

        if(handIn == Hand.MAIN_HAND && !worldIn.isRemote()) {
            if(playerIn.isSneaking()) {
                ItemStack stack = playerIn.getHeldItemMainhand();
                int i = ItemUtil.getTag(stack, "mode");
                i++;
                if(i >= Modes.size()) {
                    i = 0;
                }
                ItemUtil.setTag(stack, "mode", i);
                return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
            }
        }

        return ActionResult.resultFail(playerIn.getHeldItemMainhand());

    }

    public enum Modes {
        ENERGY(0),
        ITEM(1),
        REDSTONE(2);

        int index;
        Modes(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static int size() {
            return 3;
        }
    }

}
