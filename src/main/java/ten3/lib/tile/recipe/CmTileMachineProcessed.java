package ten3.lib.tile.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import ten3.core.recipe.OpportunityRecipe;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;

public abstract class CmTileMachineProcessed extends CmTileMachine {

    @Override
    public boolean hasRecipe() {
        return true;
    }

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
    int firstAdt;
    boolean hasAdt;

    public CmTileMachineProcessed(String key, boolean hasAddition, int start, int additionLast) {

        super(key);

        firstAdt = start;
        lastAdt = additionLast;
        hasAdt = hasAddition;

    }

    public abstract int getTimeCook();

    public void cacheRcp() {
        ItemStack ext = inventory.getStackInSlot(0);
        recipeNow = getRcp(recipeType, ext);
    }

    public void shrinkItems() {
        inventory.getStackInSlot(0).shrink(1);
    }

    @Override
    public void update() {

        super.update();

        if(!checkCanRun()) return;

        cacheRcp();
        if(last != recipeNow) data.set(PROGRESS, 0);
        last = recipeNow;

        if(recipeNow == null) {
            setActive(false);
            return;
        }

        if(energySupportRun()) {

            ItemStack result = recipeNow.getCraftingResult(inventory);
            ItemStack addition = ItemStack.EMPTY;
            ItemStack fullAdt = addition;
            if(hasAdt && recipeNow instanceof OpportunityRecipe) {
                addition = ((OpportunityRecipe<IInventory>) recipeNow).generateAddition();
                fullAdt = ((OpportunityRecipe<IInventory>) recipeNow).getAdditionOutput();
            }
            data.set(MAX_PROGRESS, getTimeCook());

            if(!itr.selfGive(result, firstAdt, lastAdt, true)
            || !itr.selfGive(fullAdt, firstAdt, lastAdt, true)) return;

            data.translate(ENERGY, -getActual());
            postProgressUp();

            if(data.get(PROGRESS) > getTimeCook()) {
                itr.selfGive(result, firstAdt, lastAdt, false);
                itr.selfGive(addition, firstAdt, lastAdt, false);
                shrinkItems();
                data.set(PROGRESS, 0);
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
