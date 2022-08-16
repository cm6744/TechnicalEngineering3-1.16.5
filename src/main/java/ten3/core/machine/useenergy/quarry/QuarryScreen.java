package ten3.core.machine.useenergy.quarry;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class QuarryScreen extends CmScreenMachine {

    public QuarryScreen(CmContainerMachine screenContainer, PlayerInventory inv, ITextComponent titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/quarry.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = getDefaultEne());

    }

    public void tick() {

        super.tick();

        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());

    }

}
