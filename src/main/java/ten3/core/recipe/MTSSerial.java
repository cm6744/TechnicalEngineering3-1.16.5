package ten3.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import ten3.TConst;

import static ten3.core.recipe.SingleSerial.getIngJSON;
import static ten3.core.recipe.SingleSerial.getStackJSON;

public class MTSSerial<T extends MTSRecipe> extends BaseSerial implements CmSerializer<T> {

    public final ResourceLocation regName;
    private final IFactoryMTSCm<T> factory;

    public MTSSerial(IFactoryMTSCm<T> factory, String reg) {

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
        CmItemList ingredient2;
        ItemStack addition;

        res = getStackJSON(json, "output");
        addition = getStackJSON(json, "addition");
        ingredient = getIngJSON(json, 0);
        ingredient2 = getIngJSON(json, 1);

        int i = JSONUtils.getInt(json, "time", fallBackTime);
        int c = JSONUtils.getInt(json, "count", 1);
        float cc = JSONUtils.getFloat(json, "chance", -1);

        return factory.create(regName, recipeId, ingredient, ingredient2, res, addition, i, c, cc);

    }

    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        CmItemList ingredient = CmItemList.parseFrom(buffer);
        CmItemList ingredient2 = CmItemList.parseFrom(buffer);
        ItemStack res = buffer.readItemStack();
        ItemStack add = buffer.readItemStack();
        int cook = buffer.readVarInt();
        int count = buffer.readVarInt();
        double cc = buffer.readDouble();

        return factory.create(regName, recipeId, ingredient, ingredient2, res, add, cook, count, cc);
    }

    public void write(PacketBuffer buffer, T recipe) {

        recipe.ingredient.writeTo(buffer);
        recipe.ingredient2.writeTo(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeItemStack(recipe.addition);
        buffer.writeVarInt(recipe.time);
        buffer.writeVarInt(recipe.count);
        buffer.writeDouble(recipe.chance);

    }

}
