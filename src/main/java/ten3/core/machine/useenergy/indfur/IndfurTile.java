package ten3.core.machine.useenergy.indfur;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.items.wrapper.InvWrapper;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.init.RecipeInit;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.tile.recipe.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;
import ten3.util.ItemUtil;
import ten3.util.KeyUtil;

import java.util.Collection;
import java.util.List;

import static ten3.util.ExcUtil.hasRcpUseThisItem;

public class IndfurTile extends CmTileMachineProcessed {

    public IndfurTile(String name) {

        super(name, false, new SlotInfo(0, 2, 3, 3));

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 35);

        recipeType = RecipeInit.getRcpType(KeyUtil.translateInfoBarKey(id));

        addSlot(new SlotCustomCm(inventory, 0, 33, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 51, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 2, 69, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 3, 127, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        List<ItemStack> lst = inventory.getStackInRange(0, 2);
        for(ItemStack s2 : lst) {
            if(s2.getItem() == s.getItem()) return false;
        }
        if(lst.size() == 0) {
            return ExcUtil.hasRcpUseThisItem(world, recipeType, s);
        }
        Collection<IRecipe<IInventory>> recs = ExcUtil.getRcpUseThisItem(world, recipeType, lst.get(0));
        return ExcUtil.hasRcpUseThisItem(world, recipeType, recs, s);
    }

    @Override
    public int getTimeCook() {
        return ((IBaseRecipeCm<?>) recipeNow).time();
    }

}
