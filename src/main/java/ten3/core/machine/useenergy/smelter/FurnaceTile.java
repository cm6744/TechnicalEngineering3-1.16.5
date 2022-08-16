package ten3.core.machine.useenergy.smelter;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileMachineProcessedSingle;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;

public class FurnaceTile extends CmTileMachineProcessedSingle {

    public FurnaceTile(String name) {

        super(name, false, 1);

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 15);

        recipeType = IRecipeType.SMELTING;

        addSlot(new SlotCustomCm(inventory, 0, 42, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public int getTimeCook() {
        return ((FurnaceRecipe) recipeNow).getCookTime();
    }

}
