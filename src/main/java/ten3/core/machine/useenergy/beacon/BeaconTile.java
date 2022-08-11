package ten3.core.machine.useenergy.beacon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.potion.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.WorkUtil;

import java.util.List;

public class BeaconTile extends CmTileMachine {

    public BeaconTile(String name) {

        super(name);

        setCap(kFE(0.1), kFE(0.1), kFE(20), FaceOption.BE_IN, FaceOption.OFF, 300);

        addSlot(new SlotCustomCm(inventory, 0, 79, 31, BrewingRecipeRegistry::isValidInput, false, false));

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

        radius = (levelIn + 1) * 30;

        if(energySupportRun()) {
            data.translate(energy, -getActual());
            if(effectApplyTickOn(7.5, 100)) {
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
