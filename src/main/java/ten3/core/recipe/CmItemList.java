package ten3.core.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
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

    public List<ItemStack> stackLstOf() {
        List<ItemStack> ss = new ArrayList<>();
        for(Item i : matches) {
            ss.add(i.getDefaultInstance());
        }
        return ss;
    }

    public CmItemList(Item s, ResourceLocation rl) {
        matches = Lists.newArrayList(s);
        type = "item";
        loc = rl;
    }

    public CmItemList(ITag<Item> s, ResourceLocation rl) {
        matches = s.getAllElements();
        type = "tag";
        tag = s;
        loc = rl;
    }

    public CmItemList() {
        matches = Lists.newArrayList(Items.AIR);
        type = "mark_empty";
    }

    public boolean hasValidIn(ItemStack... s) {
        for(ItemStack k : s) {
            if(matches.contains(k.getItem())) {
                return true;
            }
        }
        return false;
    }

    public Ingredient vanillaIngre() {
        return tag == null ? Ingredient.fromStacks(stackLstOf().stream()) : Ingredient.fromTag(tag);
    }

    private static CmItemList GETITEM(String i) {
        ResourceLocation rl = new ResourceLocation(i);
        Item item = Registry.ITEM.getOptional(rl).orElse(null);
        return new CmItemList(item, rl);
    }

    private static CmItemList GETTAG(String i) {
        ResourceLocation rl = new ResourceLocation(i);
        ITag<Item> tag = TagCollectionManager.getManager().getItemTags().get(rl);
        if (tag == null) {
            throw new JsonSyntaxException("Unknown item tag '" + rl + "'");
        } else {
            return new CmItemList(tag, rl);
        }
    }

    public static CmItemList parseFrom(JsonObject json) {

        if(json.has("mark_empty")) {
            return new CmItemList();//cannot check item!
        }

        if (json.has("item")) {
            return GETITEM(JSONUtils.getString(json, "item"));
        } else if (json.has("tag")) {
            return GETTAG(JSONUtils.getString(json, "tag"));
        }

        return new CmItemList();//cannot check item!

    }

    public static CmItemList parseFrom(PacketBuffer buffer) {

        String type = buffer.readString();
        ResourceLocation rl = buffer.readResourceLocation();

        if(type.equals("mark_empty")) {
            return new CmItemList();//cannot check item!
        }

        if(type.equals("item")) {
            return GETITEM(rl.toString());
        }
        else if(type.equals("tag")) {
            return GETTAG(rl.toString());
        }

        return new CmItemList();//cannot check item!

    }

    public void writeTo(PacketBuffer buffer) {

        buffer.writeString(type);
        buffer.writeResourceLocation(loc);

    }

}
