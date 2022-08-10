package ten3.core.item.energy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.PacketCapData;
import ten3.util.ItemUtil;
import ten3.util.PatternUtil;

import java.util.List;

import static ten3.lib.tile.CmTileMachine.energy;

public class EnergyItemHelper {

    public static void addTooltip(List<ITextComponent> tooltips, ItemStack stack) {

        double e = ItemUtil.getTag(stack, "energy");
        double me = ItemUtil.getTag(stack, "storage");

        tooltips.add(PatternUtil.join(e, me));

    }

    public static void setState(ItemStack s, PacketCapData data) {

        ItemUtil.setTag(s, "receive", data.rec);
        ItemUtil.setTag(s, "extract", data.ext);
        ItemUtil.setTag(s, "storage",data.sto);
        ItemUtil.setTagD(s, "efficient", data.eff);
        ItemUtil.setTag(s, "level", data.lv);

    }

    public static ItemStack getState(Item i, PacketCapData data) {

        ItemStack def = new ItemStack(i);
        setState(def, data);

        return def;

    }

    public static void fillFull(Item i, NonNullList<ItemStack> stacks, PacketCapData data) {

        ItemStack full = getState(i, data);
        ItemUtil.setTag(full, "energy", data.sto);
        stacks.add(full);

    }

    public static void fillEmpty(Item i, NonNullList<ItemStack> stacks, PacketCapData data) {

        ItemStack def = getState(i, data);
        stacks.add(def);

    }

    //MACHINES:

    public static ItemStack fromMachine(CmTileMachine tile, ItemStack stack) {

        ItemUtil.setTag(stack, "energy", tile.data.get(energy));
        tile.nbtManager.writeNBTUpg(stack.getOrCreateTag());

        for(int i = tile.upgSlotFrom; i <= tile.upgSlotTo; i++) {
            stack.getOrCreateTag().put("upg" + i, tile.inventory.getStackInSlot(i).serializeNBT());
        }

        return stack;

    }

    public static void pushToTile(CmTileMachine tile, ItemStack stack) {

        tile.data.set(energy, ItemUtil.getTag(stack, "energy"));
        tile.nbtManager.readNBTUpg(stack.getOrCreateTag());

        for(int i = tile.upgSlotFrom; i <= tile.upgSlotTo; i++) {
            CompoundNBT nbt = stack.getOrCreateTag().getCompound("upg" + i);
            tile.inventory.setInventorySlotContents(i, ItemStack.read(nbt));
        }

    }

}
