package ten3.init.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import ten3.TConst;
import ten3.init.ItemInit;

public class DefGroup extends ItemGroup {

    public static DefGroup BLOCK = new DefGroup("block");
    public static DefGroup ITEM = new DefGroup("item");

    public DefGroup(String id) {

        super(TConst.modid + "." + id);

    }

    public ItemStack createIcon() {

        if(this == BLOCK) {
            return ItemInit.getItem("technical_block").getDefaultInstance();
        }
        if(this == ITEM) {
            return ItemInit.getItem("technical_item").getDefaultInstance();
        }
        return ItemStack.EMPTY;

    }

}
