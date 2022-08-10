package ten3.lib.client.element;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import ten3.util.KeyUtil;

import java.util.ArrayList;
import java.util.List;

public class ElementBarEnergy extends ElementImage {

    List<ITextComponent> list = new ArrayList<>();
    int auc;
    int mxe;
    int in;
    int out;

    public ElementBarEnergy(int xr, int y, int w, int h, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(xr, y, w, h, xOff, yOff, resourceLocation);

    }

    public void update(int ch, int fet, int i, int o) {
        auc = fet;
        mxe = ch;
        in = i;
        out = o;
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        //no act
    }

    @Override
    public void addToolTip(List<ITextComponent> tooltips) {

        list.add(KeyUtil.translated(KeyUtil.GOLD, "ten3.info.bar_energy"));

        list.add(KeyUtil.translated("ten3.info.bar_energy_fact"));
        list.add(KeyUtil.translated(KeyUtil.RED, Math.abs(mxe) + " FE/t"));
        list.add(KeyUtil.translated("ten3.info.bar_energy_max"));
        list.add(KeyUtil.translated(KeyUtil.RED, auc + " FE/t"));

        list.add(KeyUtil.translated("ten3.info.bar_energy_in_max"));
        list.add(KeyUtil.translated(KeyUtil.RED, in + " FE/t"));
        list.add(KeyUtil.translated("ten3.info.bar_energy_out_max"));
        list.add(KeyUtil.translated(KeyUtil.RED, out + " FE/t"));

        tooltips.addAll(list);

        list.clear();

    }

}
