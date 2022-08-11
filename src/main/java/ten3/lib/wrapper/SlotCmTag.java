package ten3.lib.wrapper;

import net.minecraft.data.TagsProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.List;

public class SlotCmTag extends SlotCm {

    ITag<Item> tag;

    public SlotCmTag(Inventory i, int id, int x, int y, String valid, boolean ext, boolean in) {

        //bug fixed
        super(i, id, x, y, null, ext, in);

        tag = ItemTags.getCollection().getTagByID(new ResourceLocation(valid));

        this.ext = ext;
        this.in = in;

    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        if(tag == null) {
            return true;
        }

        return tag.contains(stack.getItem());

    }

}
