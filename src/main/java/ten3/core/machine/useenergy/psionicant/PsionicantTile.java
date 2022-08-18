package ten3.core.machine.useenergy.psionicant;

import net.minecraft.item.ItemStack;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.core.recipe.OpportunityRecipe;
import ten3.init.RecipeInit;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.KeyUtil;

public class PsionicantTile extends CmTileMachineProcessed {

    public PsionicantTile(String name) {

        super(name, false, 2, 2);

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 50);

        recipeType = RecipeInit.getRcpType(KeyUtil.translateInfoBarKey(id));

        addSlot(new SlotCustomCm(inventory, 0, 33, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 51, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 2, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
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
