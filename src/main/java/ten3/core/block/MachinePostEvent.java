package ten3.core.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import ten3.core.item.upgrades.LevelupItem;
import ten3.lib.tile.option.RedstoneMode;
import ten3.util.ItemUtil;
import ten3.core.network.Network;
import ten3.core.network.packets.PTCInfoClientPack;
import ten3.init.ItemInit;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.KeyUtil;

import static ten3.lib.tile.CmTileMachine.redMode;

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
            int res = tile.data.get(redMode);
            itm++;
            ene++;
            res++;
            if(itm >= FaceOption.size()) itm = 0;
            if(ene >= FaceOption.size()) ene = 0;
            if(res >= RedstoneMode.size()) res = 0;

            if(ItemUtil.getTag(i, "mode") == 0) {
                tile.setOpenEnergy(d, ene);
            } else if(ItemUtil.getTag(i, "mode") == 1) {
                tile.setOpenItem(d, itm);
            } else if(ItemUtil.getTag(i, "mode") == 2) {
                tile.data.set(redMode, res);
            }
            updateToClient(tile.direCheckEnergy(d), tile.direCheckItem(d), tile.data.get(redMode), tile.levelIn, pos, d);
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
            updateToClient(tile.direCheckEnergy(d), tile.direCheckItem(d), tile.data.get(redMode), tile.levelIn, pos, d);
            return false;
        }
        /*
        else if(i.getItem() instanceof UpgradeItem) {
            boolean ins = (i.getItem() instanceof LevelupItem);//is levelup, -> no limit

            if(tile.upgrades.size() < tile.getMaxUpgrade() || ins) {
                boolean success = ((UpgradeItem) i.getItem()).effect(tile);
                if(success) {
                    if(!ins) {
                        tile.upgrades.add((UpgradeItem) i.getItem());
                        sendHudUpg(i.getItem().getTranslationKey(), pos, false);//send packet to screen
                    }
                    /*
                     * See: CmTileMachine#remove


                    player.sendStatusMessage(KeyUtil.translated(KeyUtil.GREEN, i.getTranslationKey(), "ten3.info.upgrade_successfully"),
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
                return false;
            }
            player.sendStatusMessage(
                    KeyUtil.translated(KeyUtil.RED, "ten3.info.too_much_upgrades"),
                    false
            );
            return false;

        }
        */

        return true;

    }

    public static void updateToClient(int ene, int itm, int res, int lv, BlockPos pos, Direction d) {
        Network.sendToClient(new PTCInfoClientPack(ene, itm, res, lv, pos, d));
    }

}
