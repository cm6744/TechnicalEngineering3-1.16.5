package ten3.core.machine.engine;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;

public class MatchFuel {

    //a coal:32 kFE
    //a gold: 20 kFE
    //a netherite: 84 kFE

    public static int matchFuelAndShrink(ItemStack i, boolean shr) {

        if(i.getCount() <= 0 || i.isEmpty()) return 0;

        int time = ForgeHooks.getBurnTime(i, IRecipeType.SMELTING);

        if(i.getItem() == Items.LAVA_BUCKET) {
            return 0;
        }

        if(time > 0 && shr) {
            i.shrink(1);
        }

        return time * 20;

    }

    public static int matchFuelAndShrinkMetal(ItemStack i, boolean shr) {

        if(i.getCount() <= 0 || i.isEmpty()) return 0;

        int time = 0;

        //ITag<Item> ig = ItemTags.getCollection().getTagByID(new ResourceLocation("forge:ingots"));
        ITag<Item> cn = ItemTags.getCollection().getTagByID(new ResourceLocation("ten3:common_ingots"));
        ITag<Item> uc = ItemTags.getCollection().getTagByID(new ResourceLocation("ten3:uncommon_ingots"));
        ITag<Item> vc = ItemTags.getCollection().getTagByID(new ResourceLocation("ten3:valuable_ingots"));

        if(cn.contains(i.getItem())) {
            time = 700;
        }
        else if(uc.contains(i.getItem())) {
            time = 1000;
        }
        else if(vc.contains(i.getItem())) {
            time = 4200;
        }

        if(time > 0 && shr) {
            i.shrink(1);
        }

        return time * 20;

    }

}