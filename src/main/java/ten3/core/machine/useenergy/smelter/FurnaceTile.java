package ten3.core.machine.useenergy.smelter;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.tile.recipe.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;

public class FurnaceTile extends CmTileMachineProcessed {

    public FurnaceTile(String name) {

        super(name, false, new SlotInfo(0, 0, 1, 1));

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 15);

        recipeType = IRecipeType.SMELTING;

        addSlot(new SlotCustomCm(inventory, 0, 42, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public void shrinkItems()
    {
        inventory.getStackInSlot(0).shrink(1);
    }

    @Override
    public int getTimeCook() {
        return ((FurnaceRecipe) recipeNow).getCookTime() / 2;
    }

}
