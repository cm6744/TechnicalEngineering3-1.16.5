package ten3.init.template;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;
import ten3.init.tab.DefGroup;
import ten3.util.KeyUtil;
import ten3.util.ExcUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DefItem extends Item {

    public DefItem() {

        this(DefGroup.ITEM);

    }

    public DefItem(int stack) {

        super(new Properties().group(DefGroup.ITEM).maxStackSize(stack));

    }

    public DefItem(ItemGroup g) {

        super(new Properties().group(g));

    }

    public DefItem(boolean noGroup) {

        super(new Properties());

    }

    @Override
    protected String getDefaultTranslationKey() {

        return KeyUtil.getKey(ExcUtil.regNameOf(this));

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        List<ITextComponent> list = new ArrayList<>();

        for(int i = 0; true; i++) {
            //*getPATH!
            String k = "ten3."+ getRegistryName().getPath() +"."+i;
            ITextComponent ttc = KeyUtil.translated(KeyUtil.GOLD, k);
            if(ttc.getString().equals(k)) break;

            list.add(ttc);
        }

        if(shift()) {
            tooltip.addAll(list);
        } else if(list.size() > 0) {
            tooltip.add(KeyUtil.translated(KeyUtil.GOLD, "ten3.shift"));
        }

    }

    public boolean shift() {
        return GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
    }

}
