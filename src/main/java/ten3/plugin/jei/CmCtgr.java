package ten3.plugin.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import ten3.TConst;
import ten3.util.KeyUtil;

import java.util.List;

public abstract class CmCtgr<T> implements IRecipeCategory<T> {

    String n;
    IDrawable dr;
    IDrawable icon;

    public CmCtgr(String name, int u, int v, int w, int h, int ru, int rv, int rx, int ry)
    {
        n = name;
        dr = new ProcessDraw(u, v, w, h, name, ru, rv, rx, ry);
    }

    @Override
    public ResourceLocation getUid()
    {
        return new ResourceLocation(TConst.modid, n);
    }

    @Override
    public String getTitle()
    {
        return getTitleAsTextComponent().getString();
    }

    @Override
    public ITextComponent getTitleAsTextComponent()
    {
        return KeyUtil.translated(TConst.modid + ".machine_" + n);
    }

    @Override
    public IDrawable getBackground()
    {
        return dr;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    protected boolean hasNoEmpty(List<ItemStack> st) {
        for(ItemStack stack : st) {
            if(stack.isEmpty()) return false;
        }
        return true;
    }

}
