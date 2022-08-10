package ten3.core.machine.engine.metalizer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;
import ten3.lib.client.element.ElementBurnLeft;

public class MetalizerScreen extends CmScreenMachine {

    public MetalizerScreen(CmContainerMachine screenContainer, PlayerInventory inv, ITextComponent titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/engine.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;
    ElementBurnLeft left;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = new ElementBurnLeft(117, 22, 14, 46, 0, 0, handler, true));
        widgets.add(left = new ElementBurnLeft(81, 39, 13, 13, 14, 26, handler));

    }

    public void tick() {

        super.tick();

        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());
        left.setPer(pFuel());

    }

}
