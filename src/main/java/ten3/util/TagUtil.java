package ten3.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;

public class TagUtil {

    public static boolean containsItem(Item t, String s) {
        return TagCollectionManager.getManager().getItemTags().get(new ResourceLocation(s)).contains(t);
    }

    public static boolean containsBlock(Block t, String s) {
        try {
            return TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation(s)).contains(t);
        } catch(NullPointerException e) {
            return false;
        }
    }

}
