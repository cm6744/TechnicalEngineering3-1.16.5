package ten3.lib.client.element;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import ten3.util.ExcUtil;
import ten3.util.KeyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementBarUpgrades extends ElementBar {

    public ElementBarUpgrades(int xr, int y, int h, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(xr, y, h, xOff, yOff, resourceLocation);

    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        //no act
    }

    @Override
    public boolean checkInstr(int mouseX, int mouseY) {
        return mouseX >= x - bw && mouseY >= y && mouseX <= x - bw + barSize && mouseY <= y + barSize;
    }

    @Override
    public void addToolTip(List<ITextComponent> tooltips) {

        tooltips.add(KeyUtil.translated(KeyUtil.GOLD, "ten3.info.bar_upgrade"));

    }

}
