package ten3.core.machine.useenergy.quarry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ItemUtil;
import ten3.util.TagUtil;
import ten3.util.WorkUtil;

import java.util.List;

public class QuarryTile extends CmTileMachineRadiused {

    public QuarryTile(String name) {

        super(name);

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OUT, 10);
        initialRadius = 3;

        List<Item> v1 = SlotCm.RECEIVE_ALL_INPUT;

        addSlot(new SlotCm(inventory, 0, 79, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 1, 97, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 2, 79, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 3, 97, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 4, 79, 52, v1, true, false));
        addSlot(new SlotCm(inventory, 5, 97, 52, v1, true, false));
        addSlot(new SlotCm(inventory, 6, 115, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 7, 133, 16, v1, true, false));
        addSlot(new SlotCm(inventory, 8, 115, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 9, 133, 34, v1, true, false));
        addSlot(new SlotCm(inventory, 10, 115, 52, v1, true, false));
        addSlot(new SlotCm(inventory, 11, 133, 52, v1, true, false));

        addSlot(new SlotCustomCm(inventory, 12, 43, 34,
                (s) -> s.getItem() instanceof PickaxeItem, false, false));

    }

    @Override
    public int[] getItemFirstTransferSlot(Item i)
    {
        if(i instanceof PickaxeItem) {
            return new int[] {12, 12};
        }
        return super.getItemFirstTransferSlot(i);
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

            if(effectApplyTickOnScd(1, 10)) {

                BlockPos pos2 = pos.add(
                        Math.random() * radius - (radius - 1) / 2D,
                        -Math.random() * (pos.getY() - 1),
                        Math.random() * radius - (radius - 1) / 2D
                        );
                BlockState bs = world.getBlockState(pos2);
                Block b = bs.getBlock();
                if(TagUtil.containsBlock(b, "ten3:quarry_valids")
                        && inventory.getStackInSlot(12).canHarvestBlock(bs)) {
                    List<ItemStack> ss = bs.getDrops(WorkUtil.getLoot(world, pos2));
                    if(itr.selfGiveList(ss, true)) {
                        itr.selfGiveList(ss, false);
                        world.destroyBlock(pos2, false);
                        ItemUtil.damage(inventory.getStackInSlot(12), world, 1);
                    }
                }
            }
        }
        else {
            setActive(false);
        }

    }

}
