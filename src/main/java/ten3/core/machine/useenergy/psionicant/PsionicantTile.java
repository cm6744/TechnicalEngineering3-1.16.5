package ten3.core.machine.useenergy.psionicant;

import net.minecraft.item.ItemStack;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.init.RecipeInit;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.tile.recipe.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ItemUtil;
import ten3.util.KeyUtil;

import java.util.Arrays;
import java.util.List;

public class PsionicantTile extends CmTileMachineProcessed {

    public PsionicantTile(String name) {

        super(name, false, new SlotInfo(0, 1, 2, 2));

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 50);

        recipeType = RecipeInit.getRcpType(KeyUtil.translateInfoBarKey(id));

        addSlot(new SlotCustomCm(inventory, 0, 33, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 51, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 2, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot) {
        List<ItemStack> lst = inventory.getStackInRange(0, 1);
        if(lst.size() >= 2) {
            return true;//if full, true
        }
        if(lst.size() == 0) {
            return hasRcpUseThisItem(recipeType, s);
        }
        ItemStack[] sarr = ItemUtil.merge(s, lst.get(0));
        if(sarr.length == 1) {
            return hasRcpUseThisItem(recipeType, sarr[0]);
        }
        return getRcp(recipeType, sarr) != null;
    }

    @Override
    public void cacheRcp()
    {
        ItemStack i1 = inventory.getStackInSlot(0);
        ItemStack i2 = inventory.getStackInSlot(1);
        recipeNow = getRcp(recipeType, i1, i2);
    }

    @Override
    public int getTimeCook() {
        return ((IBaseRecipeCm<?>) recipeNow).time();
    }

}
