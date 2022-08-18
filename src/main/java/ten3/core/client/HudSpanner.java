package ten3.core.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ten3.lib.tile.option.RedstoneMode;
import ten3.lib.tile.recipe.CmTileMachineRadiused;
import ten3.util.*;
import ten3.core.item.Spanner;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.client.RenderHelper;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HudSpanner {

    static int w;
    static int h;

    static void render(boolean catchIt, PlayerEntity player, MatrixStack s, BlockPos pos, TileEntity t, Direction d) {

        w = Minecraft.getInstance().getMainWindow().getScaledWidth();
        h = Minecraft.getInstance().getMainWindow().getScaledHeight();

        s.push();

        ITextComponent tc = KeyUtil.translated("ten3.info.spanner.mode", "ten3.info.mode." + ItemUtil.getTag(player.getHeldItemMainhand(), "mode"));

        int hp = player.isCreative() ? (int) (h / 3 * 2.6) : (int) (h / 3 * 2.42);
        //RenderHelper.render(s, w / 2 - 29, hp - 3, 58, 13, 256, 256, 0, 198, TConst.guiHandler);
        RenderHelper.renderCString(s, w / 2, hp, ExcUtil.safeInt(KeyUtil.GOLD.getColor()), tc);

        if(!catchIt) return;

        IFormattableTextComponent c1 = KeyUtil.translated("ten3.info.spanner.dire.energy");
        IFormattableTextComponent c2 = KeyUtil.translated("ten3.info.spanner.dire.item");
        IFormattableTextComponent c3 = KeyUtil.translated("ten3.info.spanner.dire.redstone");
        ((CmTileMachine) t).levelIn = ExcUtil.safeInt(ClientHolder.level.get(pos));
        ITextComponent c0 = ((CmTileMachine) t).getDisplayWith();

        ArrayList<Integer> ene = ClientHolder.energy.get(pos);
        ArrayList<Integer> itm = ClientHolder.item.get(pos);
        int red = ExcUtil.safeInt(ClientHolder.redstone.get(pos));

        int di = DireUtil.direToInt(d);

        if(ene != null && ene.get(di) != null) {
            c1.appendSibling(KeyUtil.translated("ten3.info." + FaceOption.toStr(ene.get(di))));
        }

        if(itm != null && itm.get(di) != null) {
            c2.appendSibling(KeyUtil.translated("ten3.info." + FaceOption.toStr(itm.get(di))));
        }

        if(red == RedstoneMode.LOW) {
            c3.appendSibling(KeyUtil.translated("ten3.info.low"));
        }
        else if(red == RedstoneMode.HIGH) {
            c3.appendSibling(KeyUtil.translated("ten3.info.high"));
        }
        else {
            c3.appendSibling(KeyUtil.translated("ten3.info.off"));
        }

        List<ITextComponent> tooltips = new ArrayList<>();

        //int rgb = MathHelper.rgb(207, 234, 255);
        tooltips.add(c0);
        tooltips.add(c1);
        tooltips.add(c2);
        tooltips.add(c3);

        RenderHelper.renderToolTips(s, tooltips, w / 2, h / 10 + h / 2, w, h);

        s.pop();

    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent e) {

        PlayerEntity player = Minecraft.getInstance().player;
        if(player == null) return;

        ItemStack i = player.getHeldItemMainhand();
        if(!(i.getItem() instanceof Spanner)) return;

        if(e.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        World world = player.world;
        if(world == null) return;
        RayTraceResult result = Minecraft.getInstance().objectMouseOver;
        if(result instanceof BlockRayTraceResult) {
            BlockRayTraceResult r = (BlockRayTraceResult) result;

            Direction d = r.getFace();
            BlockPos hitPos = r.getPos();
            TileEntity t = world.getTileEntity(hitPos);
            render(t instanceof CmTileMachine, player, e.getMatrixStack(), hitPos, t, d);

            if(r.getType() == RayTraceResult.Type.MISS) return;

            //RENDER BINDING
            if(!world.getBlockState(hitPos).isSolid()) {
                hitPos = hitPos.down();//if not solid, like crop, render it under a block
            }
            BlockPos biPos = new BlockPos(
                    ItemUtil.getTag(i, "bindX"),
                    ItemUtil.getTag(i, "bindY"),
                    ItemUtil.getTag(i, "bindZ")
            );
            TileEntity t2 = world.getTileEntity(biPos);

            if(t2 instanceof CmTileMachineRadiused) {
                CmTileMachineRadiused ttr = (CmTileMachineRadiused) t2;
                int rad = ttr.getRadiusFromLevel(ExcUtil.safeInt(ClientHolder.level.get(biPos)));
                if(biPos.withinDistance(hitPos, rad)) {
                    WorkUtil.runInFlat(3, hitPos, (pin) -> {
                        if(pin.withinDistance(biPos, rad) && Math.random() < 0.3) {
                            ParticleSpawner.spawnClt(ParticleSpawner.RANGE,
                                    pin.getX() + Math.random(),
                                    pin.getY() + 1.2,
                                    pin.getZ() + Math.random(),
                                    0
                            );
                        }
                        return false;
                    });

                }
            }
        }

    }

}
