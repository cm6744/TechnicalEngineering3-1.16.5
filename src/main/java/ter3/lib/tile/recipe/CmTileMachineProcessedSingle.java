package ten3.lib.tile.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import ten3.core.recipe.OpportunityRecipe;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;

public abstract class CmTileMachineProcessedSingle extends CmTileMachine {

    public IRecipeType<? extends IRecipe<IInventory>> recipeType;

    public IRecipe<IInventory> recipeNow;
    IRecipe<IInventory> last;

    @Override
    public Type typeOf() {
        return Type.MACHINE_PROCESS;
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot) {
        IRecipe<IInventory> rc = getRcp(recipeType, s);
        if(slot == 0) {
            return world != null && rc != null && (rc.matches(new Inventory(s), world));
        }
        return false;
    }

    int lastAdt;
    boolean hasAdt;

    public CmTileMachineProcessedSingle(String key, boolean hasAddition, int additionLast) {

        super(key);

        lastAdt = additionLast;
        hasAdt = hasAddition;

    }

    public abstract int getTimeCook();

    @Override
    public void update() {

        super.update();

        if(!checkCanRun()) return;

        ItemStack ext = inventory.getStackInSlot(0);

        recipeNow = getRcp(recipeType, ext);
        if(last != recipeNow) data.set(progress, 0);
        last = recipeNow;

        if(recipeNow == null) {
            setActive(false);
            return;
        }

        if(energySupportRun()) {

            ItemStack result = recipeNow.getCraftingResult(inventory);
            ItemStack addition = ItemStack.EMPTY;
            if(hasAdt && recipeNow instanceof OpportunityRecipe) {
                addition = ((OpportunityRecipe<IInventory>) recipeNow).additionalOutput();
            }
            data.set(maxProgress, getTimeCook());

            if(!itr.selfGive(result, 1, lastAdt, true)
            && !itr.selfGive(addition, 1, lastAdt, true)) return;

            data.translate(energy, -getActual());
            postProgressUp();

            if(data.get(progress) > data.get(maxProgress)) {
                itr.selfGive(result, 1, lastAdt, false);
                itr.selfGive(addition, 1, lastAdt, false);
                ext.shrink(1);
                data.set(progress, 0);
            }
            setActive(true);
        }
        else {
            setActive(false);
        }

    }

    protected boolean canUseRecipeNow() {
        return true;
    }

}
