package ten3.lib.client.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import ten3.lib.client.RenderHelper;

public class ElementImage extends ElementBase {

    public ElementImage(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(x, y, width, height, xOff, yOff, resourceLocation);
    }

    @Override
    public void draw(MatrixStack matrixStack) {

        RenderHelper.bindTexture(resourceLocation);
        RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);

    }

}
