package ten3.lib.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.fml.client.gui.GuiUtils;
import ten3.lib.client.element.ElementBase;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.gui.AbstractGui.*;

public class RenderHelper {

    public static void drawAll(ArrayList<ElementBase> widgets, MatrixStack matrixStack) {

        for(int i = 0; i < widgets.size(); i++) {
            if(widgets.get(i).isVisible())
                widgets.get(i).draw(matrixStack);
        }

    }

    public static void updateAll(ArrayList<ElementBase> widgets) {

        for(int i = 0; i < widgets.size(); i++) {
            if(widgets.get(i).isVisible())
            widgets.get(i).update();
        }

    }

    public static void hangingAll(ArrayList<ElementBase> widgets, boolean hang, int mouseX, int mouseY) {

        for(int i = 0; i < widgets.size(); i++) {
            if(widgets.get(i).isVisible())
            widgets.get(i).hangingEvent(hang, mouseX, mouseY);
        }

    }

    public static void clickAll(ArrayList<ElementBase> widgets, int mouseX, int mouseY) {

        //ItemStack ret = stack;

        for(int i = 0; i < widgets.size(); i++) {
            if (widgets.get(i).checkInstr(mouseX, mouseY) && widgets.get(i).isVisible()) {
                /*
                if(widgets.getRcp(i) instanceof ElementSlot) {
                    ItemStack in = ((ElementSlot) widgets.getRcp(i)).item;
                    if(!stack.isEmpty() && in.isEmpty()) {
                        in = stack.copy();
                        ret = ItemStack.EMPTY;
                    }
                    else if(stack.isEmpty() && !in.isEmpty()) {
                        ret = in.copy();
                        in.setCount(0);
                    }
                    else if(!stack.isEmpty() && !in.isEmpty()) {
                        if(stack.getRcp() != in.getRcp()) {
                            ItemStack cac = stack.copy();
                            ret = in.copy();
                            in = cac;
                        }
                    }
                }
                 */
                widgets.get(i).onMouseClicked(mouseX, mouseY);
            }
        }

        //return ret;

    }

    public static void renderToolTips(MatrixStack matrixStack, List<? extends ITextProperties> tooltip, int mouseX, int mouseY, int screenW, int screenH) {

        FontRenderer font = Minecraft.getInstance().fontRenderer;
        GuiUtils.drawHoveringText(matrixStack, tooltip, mouseX + 1, mouseY - 1, screenW, screenH, -1, font);

    }

    public static void bindTexture(ResourceLocation resourceLocation) {

        Minecraft.getInstance().getTextureManager().bindTexture(resourceLocation);

    }

    public static void renderBackGround(MatrixStack matrixStack, int i, int j, int textureW, int textureH, ResourceLocation resourceLocation) {

        bindTexture(resourceLocation);
        render(matrixStack,i, j, 176, 166, textureW, textureH, 0, 0, resourceLocation);

    }

    public static void renderBackGround(MatrixStack matrixStack, int w, int h, int i, int j, int textureW, int textureH, ResourceLocation resourceLocation) {

        bindTexture(resourceLocation);
        render(matrixStack,i, j, w, h, textureW, textureH, 0, 0, resourceLocation);

    }

    public static void render(MatrixStack matrixStack, int x, int y, int width, int height, int textureW, int textureH, int xOff, int yOff, ResourceLocation resourceLocation) {

        bindTexture(resourceLocation);
        blit(matrixStack, x, y, xOff, yOff, width, height, textureW, textureH);

    }

    public static void renderString(MatrixStack matrixStack, int x, int y, int color, ITextComponent str) {

        FontRenderer font = Minecraft.getInstance().fontRenderer;
        drawString(matrixStack, font, str, x, y, color);

    }

    public static void renderCString(MatrixStack matrixStack, int x, int y, int color, ITextComponent str) {

        FontRenderer font = Minecraft.getInstance().fontRenderer;
        drawCenteredString(matrixStack, font, str, x, y, color);

    }

}
