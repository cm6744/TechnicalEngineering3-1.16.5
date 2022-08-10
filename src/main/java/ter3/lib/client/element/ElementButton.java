package ten3.lib.client.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import ten3.lib.client.RenderHelper;
import ten3.util.KeyUtil;

import java.util.List;

public class ElementButton extends ElementBase {

    B_PACK run_met;
    public boolean state;

    public ElementButton(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, B_PACK run) {

        super(x, y, width, height, xOff, yOff, resourceLocation);

        run_met = run;

    }

    ITextComponent text;

    public void setTxt(String... key) {

        text = KeyUtil.translated(KeyUtil.GOLD, key);

    }

    @Override
    public void addToolTip(List<ITextComponent> tooltips) {
        if(text != null)
        tooltips.add(text);
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        run_met.click();
    }

    boolean nc;

    public ElementButton withNoChange() {
        nc = true;
        return this;
    }

    @Override
    public void draw(MatrixStack matrixStack) {

        if(nc) {
            RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);
            return;
        }

        if(hanging) {
            if(state) {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height * 3, resourceLocation);
            }
            else {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height, resourceLocation);
            }
        }
        else {
            if(state) {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height * 2, resourceLocation);
            }
            else {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);
            }
        }

    }

    public interface B_PACK {

        void click();

    }

}
