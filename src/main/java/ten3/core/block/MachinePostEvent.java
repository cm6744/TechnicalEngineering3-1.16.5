package ten3.core.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import ten3.core.item.Spanner;
import ten3.core.item.upgrades.LevelupItem;
import ten3.lib.tile.option.RedstoneMode;
import ten3.util.ItemUtil;
import ten3.core.network.Network;
import ten3.core.network.packets.PTCInfoClientPack;
import ten3.init.ItemInit;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.KeyUtil;

import static ten3.lib.tile.CmTileMachine.RED_MODE;

public class MachinePostEvent {

    /**
       @return can open gui
     */
    public static boolean clickMachineEvent(World worldIn, BlockPos pos, PlayerEntity player, BlockRayTraceResult hit) {

        CmTileMachine tile = (CmTileMachine) worldIn.getTileEntity(pos);
        Direction d = hit.getFace();
        if(tile == null) {
            return false;
        }
        ItemStack i = player.getHeldItem(Hand.MAIN_HAND);

        if(!player.isSneaking() && i.getItem() == ItemInit.getItem("spanner")) {
            int ene = tile.direCheckEnergy(d);
            int itm = tile.direCheckItem(d);
            int res = tile.data.get(RED_MODE);
            itm++;
            ene++;
            res++;
            if(itm >= FaceOption.size()) itm = 0;
            if(ene >= FaceOption.size()) ene = 0;
            if(res >= RedstoneMode.size()) res = 0;

            if(ItemUtil.getTag(i, "mode") == Spanner.Modes.ENERGY.getIndex()) {
                tile.setOpenEnergy(d, ene);
            } else if(ItemUtil.getTag(i, "mode") == Spanner.Modes.ITEM.getIndex()) {
                tile.setOpenItem(d, itm);
            } else if(ItemUtil.getTag(i, "mode") == Spanner.Modes.REDSTONE.getIndex()) {
                tile.data.set(RED_MODE, res);
            } else if(ItemUtil.getTag(i, "mode") == Spanner.Modes.RANGE.getIndex()) {
                ItemUtil.setTag(i, "bindX", pos.getX());
                ItemUtil.setTag(i, "bindY", pos.getY());
                ItemUtil.setTag(i, "bindZ", pos.getZ());
            }
            updateToClient(tile.direCheckEnergy(d), tile.direCheckItem(d), tile.data.get(RED_MODE), tile.levelIn, pos, d);
            return false;
        }
        else if(i.getItem() instanceof LevelupItem) {
            boolean success = ((LevelupItem) i.getItem()).effect(tile);
            if(success) {
                player.sendStatusMessage(
                        KeyUtil.translated(KeyUtil.GREEN, i.getTranslationKey(), "ten3.info.upgrade_successfully"),
                        false);
                if(!player.isCreative()) {
                    i.shrink(1);
                }
            }
            else {
                player.sendStatusMessage(
                        KeyUtil.translated(KeyUtil.RED, "ten3.info.not_support_upgrade"),
                        false
                );
            }
            updateToClient(tile.direCheckEnergy(d), tile.direCheckItem(d), tile.data.get(RED_MODE), tile.levelIn, pos, d);
            return false;
        }

        return true;

    }

    public static void updateToClient(int ene, int itm, int res, int lv, BlockPos pos, Direction d) {
        Network.sendToClient(new PTCInfoClientPack(ene, itm, res, lv, pos, d));
    }

}
