package ten3.core.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class CmItemList {

    String type;
    List<Item> matches;
    ITag<Item> tag;
    ResourceLocation loc;
    int limit;

    public List<ItemStack> stackLstOf() {
        List<ItemStack> ss = new ArrayList<>();
        for(Item i : matches) {
            ItemStack p = i.getDefaultInstance();
            p.setCount(limit);
            ss.add(p);
        }
        return ss;
    }

    public CmItemList(Item s, int lm, ResourceLocation rl) {
        matches = Lists.newArrayList(s);
        type = "item";
        loc = rl;
        limit = lm;
    }

    public CmItemList(ITag<Item> s, int lm, ResourceLocation rl) {
        matches = s.getAllElements();
        type = "tag";
        tag = s;
        loc = rl;
        limit = lm;
    }

    public CmItemList() {
        matches = Lists.newArrayList(Items.AIR);
        type = "mark_empty";
        limit = 1;
    }

    public boolean hasValidIn(ItemStack... s) {
        for(ItemStack k : s) {
            if(matches.contains(k.getItem()) && k.getCount() >= limit) {
                return true;
            }
        }
        return false;
    }

    public Ingredient vanillaIngre() {
        return tag == null ? Ingredient.fromStacks(stackLstOf().stream()) : Ingredient.fromTag(tag);
    }

    private static CmItemList GET_ITEM(String i, int im) {
        ResourceLocation rl = new ResourceLocation(i);
        Item item = Registry.ITEM.getOptional(rl).orElse(null);
        return new CmItemList(item, im, rl);
    }

    private static CmItemList GET_TAG(String i, int im) {
        ResourceLocation rl = new ResourceLocation(i);
        ITag<Item> tag = TagCollectionManager.getManager().getItemTags().get(rl);
        if (tag == null) {
            throw new JsonSyntaxException("Unknown item tag '" + rl + "'");
        } else {
            return new CmItemList(tag, im, rl);
        }
    }

    public static CmItemList parseFrom(JsonObject json) {

        if(json.has("mark_empty")) {
            return new CmItemList();//cannot check item!
        }

        int lm = JSONUtils.getInt(json, "count", 1);

        if (json.has("item")) {
            return GET_ITEM(JSONUtils.getString(json, "item"), lm);
        } else if (json.has("tag")) {
            return GET_TAG(JSONUtils.getString(json, "tag"), lm);
        }

        return new CmItemList();//cannot check item!

    }

    public static CmItemList parseFrom(PacketBuffer buffer) {

        String type = buffer.readString();
        ResourceLocation rl = buffer.readResourceLocation();
        int lm = buffer.readInt();

        if(type.equals("mark_empty")) {
            return new CmItemList();//cannot check item!
        }

        if(type.equals("item")) {
            return GET_ITEM(rl.toString(), lm);
        }
        else if(type.equals("tag")) {
            return GET_TAG(rl.toString(), lm);
        }

        return new CmItemList();//cannot check item!

    }

    public void writeTo(PacketBuffer buffer) {

        buffer.writeString(type);
        buffer.writeResourceLocation(loc);
        buffer.writeInt(limit);

    }

}
