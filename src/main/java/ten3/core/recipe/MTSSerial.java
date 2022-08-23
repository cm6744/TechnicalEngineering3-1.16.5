package ten3.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import ten3.TConst;

import java.util.ArrayList;
import java.util.List;

import static ten3.core.recipe.SingleSerial.getIngJSON;
import static ten3.core.recipe.SingleSerial.getStackJSON;

public class MTSSerial<T extends MTSRecipe> extends BaseSerial implements CmSerializer<T> {

    public final ResourceLocation regName;
    private final IFactoryMTSCm<T> factory;
    public int size;

    public MTSSerial(IFactoryMTSCm<T> factory, String reg, int s) {

        regName = new ResourceLocation(TConst.modid, reg);
        this.factory = factory;
        size = s;

    }

    @Override
    public String id() {
        return regName.getPath();
    }

    public T read(ResourceLocation recipeId, JsonObject json) {

        ItemStack res;
        List<CmItemList> igs = new ArrayList<>();
        ItemStack addition;

        res = getStackJSON(json, "output");
        addition = getStackJSON(json, "addition");
        CmItemList lst;
        int tms = 0;
        while((lst = getIngJSON(json, tms)) != null) {
            tms++;
            igs.add(lst);
        }

        int i = JSONUtils.getInt(json, "time", fallBackTime);
        int c = JSONUtils.getInt(json, "count", 1);
        float cc = JSONUtils.getFloat(json, "chance", -1);

        return factory.create(regName, recipeId, igs, res, addition, i, c, cc);

    }

    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        List<CmItemList> igs = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            igs.add(CmItemList.parseFrom(buffer));
        }
        ItemStack res = buffer.readItemStack();
        ItemStack add = buffer.readItemStack();
        int cook = buffer.readVarInt();
        int count = buffer.readVarInt();
        double cc = buffer.readDouble();

        return factory.create(regName, recipeId, igs, res, add, cook, count, cc);
    }

    public void write(PacketBuffer buffer, T recipe) {

        for(CmItemList l : recipe.ingredients) {
            l.writeTo(buffer);
        }
        buffer.writeItemStack(recipe.result);
        buffer.writeItemStack(recipe.addition);
        buffer.writeVarInt(recipe.time);
        buffer.writeVarInt(recipe.count);
        buffer.writeDouble(recipe.chance);

    }

}
