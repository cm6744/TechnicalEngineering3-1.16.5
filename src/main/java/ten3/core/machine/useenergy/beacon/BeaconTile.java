package ten3.core.machine.useenergy.beacon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCustomCm;

import java.util.List;

public class BeaconTile extends CmTileMachineRadiused {

    public BeaconTile(String name) {

        super(name);

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 300);
        initialRadius = 32;

        addSlot(new SlotCustomCm(inventory, 0, 79, 31, BrewingRecipeRegistry::isValidInput, false, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        if(energySupportRun()) {
            data.translate(ENERGY, -getActual());

            if(effectApplyTickOnScd(5, 60)) {

                AxisAlignedBB axisalignedbb = (new AxisAlignedBB(pos)).grow(radius).expand(0, world.getHeight(), 0);
                List<PlayerEntity> list = world.getEntitiesWithinAABB(PlayerEntity.class, axisalignedbb);

                ItemStack its = inventory.getStackInSlot(0);
                Potion pt = PotionUtils.getPotionFromItem(its);

                if(its.isEmpty()) return;

                for(PlayerEntity playerentity : list) {
                    playerentity.addPotionEffect(new EffectInstance(pt.getEffects().get(0).getPotion(), 40 * 10, levelIn, true, true));
                }
            }
        }
        else {
            setActive(false);
        }

    }

}
