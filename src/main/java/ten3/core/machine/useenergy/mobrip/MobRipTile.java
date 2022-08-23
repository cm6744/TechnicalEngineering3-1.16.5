package ten3.core.machine.useenergy.mobrip;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;
import ten3.util.ItemUtil;

import java.util.List;

public class MobRipTile extends CmTileMachineRadiused {

    public MobRipTile(String name) {

        super(name);
        initialRadius = 8;

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 15);

        addSlot(new SlotCustomCm(inventory, 0, 79, 31, (e) -> {
            return e.getItem() instanceof ToolItem || e.getItem() instanceof SwordItem;
        }, false, false));

    }

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        if(energySupportRun()) {
            data.translate(ENERGY, -getActual());
            if(effectApplyTickOnScd(0.5, 12)) {

                AxisAlignedBB axisalignedbb = (new AxisAlignedBB(pos)).grow(radius);
                List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);

                ItemStack st1 = inventory.getStackInSlot(0);

                if(list.size() == 0) return;

                LivingEntity entity = ExcUtil.randomInCollection(list);

                if(entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative()) return;

                    float damage = 0.5f;
                    if(!st1.isEmpty()) {
                        Item iti = st1.getItem();
                        if(iti instanceof ToolItem) {
                            damage = ((ToolItem) iti).getAttackDamage();
                        }
                        if(iti instanceof SwordItem) {
                            damage = ((SwordItem) iti).getAttackDamage();
                        }
                    }
                    entity.attackEntityFrom(DamageSource.MAGIC, damage);
                    ItemUtil.damage(st1, world, 1);
            }
        }
        else {
            setActive(false);
        }

    }

}
