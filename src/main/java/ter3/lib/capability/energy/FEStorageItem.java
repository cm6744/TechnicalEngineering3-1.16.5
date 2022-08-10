package ten3.lib.capability.energy;

import net.minecraft.item.ItemStack;
import ten3.util.ItemUtil;

public class FEStorageItem extends FEStorage {

    ItemStack stackIn;

    public FEStorageItem(int capacity, int maxReceive, int maxExtract, ItemStack stack) {
        super(capacity, maxReceive, maxExtract);
        stackIn = stack;
    }

    @Override
    public int getEnergyStored() {
        return ItemUtil.getTag(stackIn, "energy");
    }

    public void translateEnergy(int diff) {
        ItemUtil.tranTag(stackIn, "energy", diff);
    }

    public void setEnergy(int diff) {
        ItemUtil.setTag(stackIn, "energy", diff);
    }

}
