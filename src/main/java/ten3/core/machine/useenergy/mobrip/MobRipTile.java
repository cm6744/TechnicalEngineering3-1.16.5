package ten3.core.machine.useenergy.mobrip;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;

import java.util.List;

public class MobRipTile extends CmTileMachine {

    public MobRipTile(String name) {

        super(name);

        setCap(kFE(0.1), kFE(0.1), kFE(20), FaceOption.BE_IN, FaceOption.OFF, 15);

        addSlot(new SlotCustomCm(inventory, 0, 79, 31, (e) -> {
            return e.getItem() instanceof ToolItem || e.getItem() instanceof SwordItem;
        }, false, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    int radius;

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        radius = (levelIn + 1) * 6;

        if(energySupportRun()) {
            data.translate(energy, -getActual());
            if(effectApplyTickOn(0.75, 20)) {

                AxisAlignedBB axisalignedbb = (new AxisAlignedBB(pos)).grow(radius);
                List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);

                ItemStack st1 = inventory.getStackInSlot(0);

                if(list.size() == 0) return;

                LivingEntity entity = ExcUtil.randomInCollection(list);

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
                    st1.damageItem(2, entity, (e) -> {});
            }
        }
        else {
            setActive(false);
        }

    }

}
