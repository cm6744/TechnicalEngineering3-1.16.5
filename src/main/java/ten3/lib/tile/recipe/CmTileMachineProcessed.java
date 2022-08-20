package ten3.lib.tile.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import ten3.core.recipe.IBaseRecipeCm;
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
            return world != null && rc != null;
        }
        return false;
    }

    SlotInfo slotInfo;
    boolean hasAdt;

    public CmTileMachineProcessed(String key, boolean hasAddition, SlotInfo info) {

        super(key);

        slotInfo = info;
        hasAdt = hasAddition;

    }

    public abstract int getTimeCook();

    //need to override sometimes
    public void cacheRcp() {
        ItemStack ext = inventory.getStackInSlot(0);
        recipeNow = getRcp(recipeType, ext);
    }

    public void shrinkItems() {
        for(int i = slotInfo.ins; i <= slotInfo.ine; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            int c1 = ((IBaseRecipeCm<?>) recipeNow).inputLimit(stack);
            if(!stack.getContainerItem().isEmpty()) {
                Block.spawnAsEntity(world, pos, stack.getContainerItem());
            }
            stack.shrink(c1);
        }
    }

    @Override
    public void update() {

        super.update();

        if(!checkCanRun()) return;

        cacheRcp();
        if(last != recipeNow || recipeNow == null) data.set(PROGRESS, 0);
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

            if(!itr.selfGive(result, slotInfo.ots, slotInfo.ote, true)
            || !itr.selfGive(fullAdt, slotInfo.ots, slotInfo.ote, true)) return;

            data.translate(ENERGY, -getActual());
            postProgressUp();

            if(data.get(PROGRESS) > getTimeCook()) {
                itr.selfGive(result, slotInfo.ots, slotInfo.ote, false);
                itr.selfGive(addition, slotInfo.ots, slotInfo.ote, false);
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
