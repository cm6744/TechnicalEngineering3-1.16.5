package ten3.core.machine.useenergy.pulverizer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class PulverizerScreen extends CmScreenMachine {

    public PulverizerScreen(CmContainerMachine screenContainer, PlayerInventory inv, ITextComponent titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/pulverizer.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;
    ElementBurnLeft left;
    ElementProgress progress;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = new ElementBurnLeft(9, 18, 14, 46, 0, 0, handler, true));
        widgets.add(left = new ElementBurnLeft(45, 48, 13, 13, 14, 0, handler));
        widgets.add(progress = new ElementProgress(76, 35, 22, 16, 27, 32, handler));

    }

    public void tick() {

        super.tick();

        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());
        left.setPer(pEnergy());
        progress.setPer(pProgress());

    }

}
