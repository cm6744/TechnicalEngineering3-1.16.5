package ten3.lib.client.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import ten3.lib.client.RenderHelper;
import ten3.util.PatternUtil;

import java.util.List;

public class ElementBurnLeft extends ElementBase {

    double p;
    boolean dv;

    public ElementBurnLeft(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(x, y, width, height, xOff, yOff, resourceLocation);

    }

    public ElementBurnLeft(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, boolean displayValue) {

        super(x, y, width, height, xOff, yOff, resourceLocation);
        dv = displayValue;

    }

    @Override
    public void draw(MatrixStack matrixStack) {

        int h = (int) (height * (1 - p));
        RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);

        //RenderHelper.bindTexture(resourceLocation);
        RenderHelper.render(matrixStack, x, y + h, width, height - h, textureW, textureH, xOff, yOff + height + (int) (height * (1 - p)), resourceLocation);

    }

    @Override
    public void addToolTip(List<ITextComponent> tooltips) {

        if(!dv) {
            tooltips.add(new StringTextComponent((int) (p * 100) + "%"));
        }
        else {
            tooltips.add(PatternUtil.join(val, m_val));
        }

    }

    int val;
    int m_val;

    public void setValue(int v, int mv) {

        val = v;
        m_val = mv;

    }

    public void setPer(double per) {

        p = per;

    }

}
