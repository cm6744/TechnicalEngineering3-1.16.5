package ten3.core.machine.useenergy.compressor;

import net.minecraft.inventory.IInventory;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.init.RecipeInit;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessedSingle;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.KeyUtil;

public class CompressorTile extends CmTileMachineProcessedSingle {

    public CompressorTile(String name) {

        super(name, false, 1);

        setCap(kFE(0.1), kFE(0.1), kFE(20), FaceOption.BE_IN, FaceOption.OFF, 15);

        recipeType = RecipeInit.getRcpType(KeyUtil.translateInfoBarKey(id));

        addSlot(new SlotCustomCm(inventory, 0, 42, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public int getTimeCook() {
        return ((IBaseRecipeCm<IInventory>) recipeNow).time();
    }

    @Override
    protected boolean canUseRecipeNow() {
        return super.canUseRecipeNow();
    }

}