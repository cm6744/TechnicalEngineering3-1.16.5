package ten3.core.machine.engine.metalizer;

import net.minecraft.item.ItemStack;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileEngine;
import ten3.lib.wrapper.SlotCustomCm;

public class MetalizerTile extends CmTileEngine {

    public MetalizerTile(String name) {

        super(name);

        setCap(kFE(0.2), kFE(0.2), kFE(80), FaceOption.OUT, FaceOption.IN, 80);

        addSlot(new SlotCustomCm(inventory, 0, 42, 36, (s) -> MatchFuel.matchFuelAndShrinkMetal(s, false) > 0, false, true));

    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrinkMetal(stack, simulate);
    }

}
