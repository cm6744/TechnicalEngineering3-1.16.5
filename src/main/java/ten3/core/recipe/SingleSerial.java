package ten3.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import ten3.TConst;

public class SingleSerial<T extends SingleRecipe> extends BaseSerial implements CmSerializer<T> {

    public final ResourceLocation regName;
    private final IFactory<T> factory;

    public SingleSerial(IFactory<T> factory, String reg) {

        regName = new ResourceLocation(TConst.modid, reg);
        this.factory = factory;

    }

    @Override
    public String id() {
        return regName.getPath();
    }

    public T read(ResourceLocation recipeId, JsonObject json) {

        ItemStack res;
        CmItemList ingredient;
        ItemStack addition;

        res = getStackJSON(json, "output");
        addition = getStackJSON(json, "addition");
        ingredient = getIngJSON(json);

        int i = JSONUtils.getInt(json, "time", 120);
        int c = JSONUtils.getInt(json, "count", 1);
        float cc = JSONUtils.getFloat(json, "chance", -1);

        return factory.create(regName, recipeId, ingredient, res, addition, i, c, cc);

    }

    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        CmItemList ingredient = CmItemList.parseFrom(buffer);
        ItemStack res = buffer.readItemStack();
        ItemStack add = buffer.readItemStack();
        int cook = buffer.readVarInt();
        int count = buffer.readVarInt();
        double cc = buffer.readDouble();

        return factory.create(regName, recipeId, ingredient, res, add, cook, count, cc);
    }

    public void write(PacketBuffer buffer, T recipe) {

        recipe.ingredient.writeTo(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeItemStack(recipe.addition);
        buffer.writeVarInt(recipe.time);
        buffer.writeVarInt(recipe.count);
        buffer.writeDouble(recipe.chance);

    }

    public interface IFactory<T extends SingleRecipe> {
        T create(ResourceLocation reg, ResourceLocation idIn, CmItemList ingredientIn,
                 ItemStack resultIn, ItemStack add, int cookTimeIn, int count, double cc);
    }

    public static ItemStack getStackJSON(JsonObject json, String name) {

        if(json.has(name)) {
            if(json.get(name).isJsonObject()) {
                return ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, name));
            } else {
                String s1 = JSONUtils.getString(json, name);
                return new ItemStack(Registry.ITEM.getOptional(new ResourceLocation(s1)).orElse(null));
            }
        }

        return ItemStack.EMPTY;

    }

    public static CmItemList getIngJSON(JsonObject json) {

        return CmItemList.parseFrom(JSONUtils.getJsonObject(json, "ingredient"));

    }

}
