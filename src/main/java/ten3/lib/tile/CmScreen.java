package ten3.lib.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import ten3.TConst;
import ten3.lib.client.GuiHelper;
import ten3.lib.client.RenderHelper;
import ten3.lib.client.element.ElementBase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ten3.lib.tile.CmTileMachine.*;

public class CmScreen<T extends CmContainer> extends ContainerScreen<T> {

    //all widgets
    public static final ResourceLocation handler = TConst.guiHandler;
    public final ResourceLocation BG;
    protected int texh;
    protected int texw;

    protected final ArrayList<ElementBase> widgets = new ArrayList<>();
    protected final List<ITextComponent> tooltips = new LinkedList<>();

    public T container;

    public CmScreen(T container, PlayerInventory inv, ITextComponent titleIn, String path, int textureW, int textureH) {

        super(container, inv, titleIn);
        this.xSize = textureW;
        this.ySize = textureH;
        BG = new ResourceLocation(TConst.modid, path);
        texh = textureH;
        texw = textureW;
        this.container = container;

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTick) {

        renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTick);

        RenderHelper.drawAll(widgets, matrixStack);
        RenderHelper.updateAll(widgets);
        RenderHelper.hangingAll(widgets, true, mouseX, mouseY);

        ElementBase element = getElementFromLocation(mouseX, mouseY);
        if (element != null) {
            element.addToolTip(tooltips);
        }

        RenderHelper.renderToolTips(matrixStack, tooltips, mouseX, mouseY, this.width, this.height);

        tooltips.clear();
        renderHoveredTooltip(matrixStack, mouseX, mouseY);

    }

    boolean init;

    public void addWidgets() {}

    @Override
    protected void init() {

        super.init();

        if(!init) addWidgets();
        init = true;

        int i = GuiHelper.getI(width, xSize);
        int j = GuiHelper.getJ(height, ySize);

        ElementBase e;
        for(int k = 0; k < widgets.size(); k++) {
            e = widgets.get(k);
            e.updateLocWhenFrameResize(i, j);
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        int i = GuiHelper.getI(width, xSize);
        int j = GuiHelper.getJ(height, ySize);

        renderBackground(matrixStack);

        RenderHelper.renderBackGround(matrixStack, i, j, texw, texh, BG);

    }

    public ElementBase getElementFromLocation(int mouseX, int mouseY) {

        for (int i = 0; i < widgets.size(); i++) {
            ElementBase element = widgets.get(i);
            if (element.checkInstr(mouseX, mouseY) && element.isVisible()) {
                return element;
            }
        }
        return null;

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            RenderHelper.clickAll(widgets, (int) mouseX, (int) mouseY);
        }

        return super.mouseClicked(mouseX, mouseY, button);

    }

    public double pFuel() {

        IIntArray data = getContainer().data;

        if(data.get(MAX_FUEL) != 0) {
            return ((double) data.get(FUEL)) / data.get(MAX_FUEL);
        }

        return 0;

    }

    public double pProgress() {

        IIntArray data = getContainer().data;

        if(data.get(MAX_PROGRESS) != 0) {
            return ((double) data.get(PROGRESS)) / data.get(MAX_PROGRESS);
        }

        return 0;

    }

    public double pEnergy() {

        IIntArray data = getContainer().data;

        if(data.get(MAX_ENERGY) != 0) {
            return ((double) data.get(ENERGY)) / data.get(MAX_ENERGY);
        }

        return 0;

    }

    public int energy() {

        IIntArray data = getContainer().data;

        return data.get(ENERGY);

    }

    public int maxEnergy() {

        IIntArray data = getContainer().data;

        return data.get(MAX_ENERGY);

    }

}
