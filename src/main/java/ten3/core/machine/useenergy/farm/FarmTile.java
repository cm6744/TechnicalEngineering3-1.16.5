package ten3.core.machine.useenergy.farm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.WorkUtil;

public class FarmTile extends CmTileMachine {

    public FarmTile(String name) {

        super(name);

        setCap(kFE(0.1), kFE(0.1), kFE(20), FaceOption.BE_IN, FaceOption.OUT, 10);

        SlotCustomCm.Condition onlySeed = (s) -> {
            if(s.getItem() instanceof BlockItem) {
                return ((BlockItem) s.getItem()).getBlock() instanceof CropsBlock;
            }
            return false;
        };

        addSlot(new SlotCustomCm(inventory, 0, 43, 16, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 61, 16, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 2, 43, 34, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 3, 61, 34, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 4, 43, 52, onlySeed, false, true));
        addSlot(new SlotCustomCm(inventory, 5, 61, 52, onlySeed, false, true));

        addSlot(new SlotCm(inventory, 6, 97, 16, null, true, false));
        addSlot(new SlotCm(inventory, 7, 115, 16, null, true, false));
        addSlot(new SlotCm(inventory, 8, 97, 34, null, true, false));
        addSlot(new SlotCm(inventory, 9, 115, 34, null, true, false));
        addSlot(new SlotCm(inventory, 10, 97, 52, null, true, false));
        addSlot(new SlotCm(inventory, 11, 115, 52, null, true, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    int radius = 4;

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        radius = (levelIn + 1) * 4;

        if(energySupportRun()) {
            data.translate(energy, -getActual());
            if(effectApplyTickOn(4, 100)) {
            WorkUtil.runIn(radius, pos, (pin) -> {
                BlockPos pd = pin.down();
                BlockState s = world.getBlockState(pin);
                BlockState sd = world.getBlockState(pd);

                if(s.getBlock() instanceof CropsBlock) {
                    CropsBlock cr = (CropsBlock) s.getBlock();
                    int age = s.get(cr.getAgeProperty());
                    if(age >= cr.getMaxAge()) {
                        for(ItemStack stack : s.getDrops(new LootContext.Builder((ServerWorld) world)
                                .withRandom(world.rand)
                                .withParameter(LootParameters.ORIGIN, Vector3d.copyCentered(pos))
                                .withParameter(LootParameters.TOOL, ItemStack.EMPTY))) {
                            if(itr.selfGive(stack, true)) {
                                itr.selfGive(stack, false);
                                world.destroyBlock(pin, false);
                                return;
                            }
                        }
                    }
                }

                if(sd.matchesBlock(Blocks.FARMLAND) && s.isAir()) {
                    ItemStack seedSim = itr.selfGet(1, 0, 5, true);
                    if(seedSim.getItem() instanceof BlockItem) {
                        Block plant = ((BlockItem) seedSim.getItem()).getBlock();
                        if(plant instanceof CropsBlock) {
                            world.setBlockState(pin, plant.getDefaultState());
                            itr.selfGet(1, 0, 5, false);
                            return;
                        }
                    }
                }
                //end
            });
            }
        }
        else {
            setActive(false);
        }

    }

}
