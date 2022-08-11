package ten3.lib.client.element;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import ten3.util.KeyUtil;

import java.util.ArrayList;
import java.util.List;

public class ElementBarIdeas extends ElementImage {

    List<ITextComponent> list = new ArrayList<>();

    public ElementBarIdeas(int xr, int y, int w, int h, int xOff, int yOff, ResourceLocation resourceLocation, String key) {

        super(xr, y, w, h, xOff, yOff, resourceLocation);

        list.add(KeyUtil.translated(KeyUtil.GOLD, "ten3.info.bar_ideas"));

        for(int i = 0; true; i++) {
            String k = "ten3.info." + KeyUtil.translateInfoBarKey(key) + "."+i;
            ITextComponent ttc = KeyUtil.translated(k);
            if(ttc.getString().equals(k)) break;

            list.add(ttc);
        }

    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        //no act
    }

    @Override
    public void addToolTip(List<ITextComponent> tooltips) {

        tooltips.addAll(list);

    }

}
