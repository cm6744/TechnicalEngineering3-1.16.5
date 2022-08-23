package ten3.lib.tile.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.math.BlockPos;
import ten3.core.recipe.OpportunityRecipe;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;

public abstract class CmTileMachineRadiused extends CmTileMachine {

    public int radius;
    public int initialRadius;

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public CmTileMachineRadiused(String key) {
        super(key);
    }

    @Override
    public void resetAll()
    {
        super.resetAll();
        radius = initialRadius;
    }

}
