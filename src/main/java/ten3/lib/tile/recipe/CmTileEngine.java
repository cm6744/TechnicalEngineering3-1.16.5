package ten3.lib.tile.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import ten3.core.machine.engine.MatchFuel;
import ten3.core.recipe.OpportunityRecipe;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;

import javax.annotation.Nonnull;

public abstract class CmTileEngine extends CmTileMachine {

    public CmTileEngine(String name) {
        super(name);
    }

    @Override
    public Type typeOf() {
        return Type.GENERATOR;
    }

    @Override
    public void update() {

        super.update();

        if(!checkCanRun()) return;

        if(energySupportRun()) {
            ItemStack ext = inventory.getStackInSlot(0);

            if(data.get(fuel) > 0) {
                data.translate(energy, getActual());//up energy
                data.translate(fuel, -getActual());//down fuel
            }
            else {
                int fuel_p = matchFuelAndShrink(ext, true);
                if(fuel_p > 0) {
                    data.set(fuel, fuel_p);
                    data.set(maxFuel, fuel_p);
                }
            }
        }

    }

    @Override
    protected boolean can(Capability<?> cap, Direction d) {
        if(cap == CapabilityEnergy.ENERGY) {
            return d == null || d == Direction.UP;
        }
        return d != Direction.UP;
    }

    public abstract int matchFuelAndShrink(ItemStack stack, boolean simulate);

}
