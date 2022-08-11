package ten3.lib.client.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ElementBase {

    int ix;
    int iy;
    int x;
    int y;
    int textureW;
    int textureH;
    int xOff;
    int yOff;
    int width;
    int height;
    boolean visible = true;
    public boolean hanging;

    ResourceLocation resourceLocation;

    public void updateLocWhenFrameResize(int i, int j) {

        x = ix + i;
        y = iy + j;

    }

    //image
    public ElementBase(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        this.x = ix = x;
        this.y = iy = y;

        this.width = width;
        this.height = height;
        this.xOff = xOff;
        this.yOff = yOff;
        this.textureW = 256;
        this.textureH = 256;
        this.resourceLocation = resourceLocation;

    }

    public void draw(MatrixStack matrixStack) {}

    public void update() {}

    public void addToolTip(List<ITextComponent> tooltips) {}

    public void onMouseClicked(int mouseX, int mouseY) {}

    public void hangingEvent(boolean hang, int mouseX, int mouseY) {

        hanging = hang && checkInstr(mouseX, mouseY);

    }

    public boolean checkInstr(int mouseX, int mouseY) {

        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;

    }

    public void setVisible(boolean v) {

        this.visible = v;

    }

    public boolean isVisible() {

        return visible;

    }

}
