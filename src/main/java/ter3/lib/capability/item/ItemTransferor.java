package ten3.lib.capability.item;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DireUtil;

@SuppressWarnings("all")
public class ItemTransferor {

    CmTileMachine t;
    public static int cap = 16;

    public ItemTransferor(CmTileMachine t) {
        this.t = t;
    }

    private TileEntity checkTile(Direction d) {

        return checkTile(t.getPos().offset(d));

    }

    private TileEntity checkTile(BlockPos pos) {

        return t.getWorld().getTileEntity(pos);

    }

    public static IItemHandler handlerOf(TileEntity t, Direction d) {

        return t.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d).orElse(null);

    }

    //don't use in other places without checking.
    private static void backItemForcely(Inventory i, int slot, ItemStack mg) {

        ItemStack s = i.getStackInSlot(slot);
        ItemStack md = new ItemStack(s.getItem(), s.getCount() + mg.getCount());
        i.setInventorySlotContents(slot, md);

    }

    public void transferTo(BlockPos p, Direction d) {

        if(FaceOption.isPassive(t.direCheckItem(d))) return;
        if(!FaceOption.isOut(t.direCheckItem(d))) return;

        TileEntity tile = checkTile(p);
        if(tile != null) {
            IItemHandler src = handlerOf(t, d);
            if(src == null) return;
            IItemHandler dest = handlerOf(tile, DireUtil.safeOps(d));
            if(dest == null) return;

            srcToDest(src, dest);
        }

    }

    public void transferFrom(BlockPos p, Direction d) {

        if(FaceOption.isPassive(t.direCheckItem(d))) return;
        if(!FaceOption.isIn(t.direCheckItem(d))) return;

        TileEntity tile = checkTile(p);
        if(tile != null) {
            IItemHandler src = handlerOf(tile, DireUtil.safeOps(d));
            if(src == null) return;
            IItemHandler dest = handlerOf(t, d);
            if(dest == null) return;

            srcToDest(src, dest);
        }

    }

    public void transferTo(Direction d) {

        transferTo(t.getPos().offset(d), d);

    }

    public void transferFrom(Direction d) {

        transferFrom(t.getPos().offset(d), d);

    }

    //s - the extract item from src
    //return - src's max cap for <s>
    public int getRemainSize(IItemHandler src, ItemStack s) {

        ItemStack sin = s.copy();

        for(int i = 0; i < src.getSlots(); i++) {
            sin = src.insertItem(i, sin, true);
            if(sin.isEmpty()) break;
        }

        return s.getCount() - sin.getCount();

    }

    public void srcToDest(IItemHandler src, IItemHandler dest) {

        ItemStack s = ItemStack.EMPTY;
        int i = -1;
        while(s.isEmpty()) {
            i++;
            if(i >= src.getSlots()) break;

            s = src.extractItem(i, Math.min(cap, getRemainSize(dest, src.getStackInSlot(i))), false);
        }

        int k = -1;
        while(true) {
            k++;
            if(k >= dest.getSlots()) break;

            s = dest.insertItem(k, s, false);
            if(s.isEmpty()) break;
        }

    }

    //return : stack is completely given.
    public boolean selfGive(ItemStack stack, int from, int to, boolean sim) {

        InventoryWrapperCm dest = (InventoryWrapperCm) handlerOf(t, null);
        if(dest == null) return false;

        int k = from - 1;
        while(true) {
            k++;
            if(k > to) break;

            stack = dest.forceInsert(k, stack, sim);
            if(stack.isEmpty()) break;
        }

        return stack.isEmpty();

    }

    public boolean selfGive(ItemStack stack, boolean sim) {

        return selfGive(stack, 0, t.inventory.getSizeInventory() - 1, sim);

    }

    public ItemStack selfGet(int max, int from, int to, boolean sim) {

        InventoryWrapperCm src = (InventoryWrapperCm) handlerOf(t, null);
        if(src == null) return ItemStack.EMPTY;

        ItemStack s = ItemStack.EMPTY;
        int i = from - 1;
        while(s.getCount() < max || s.isEmpty()) {
            i++;
            if(i > to) break;

            s = src.forceExtract(i, max, sim);
        }

        return s;

    }

    public ItemStack selfGet(int max, boolean sim) {

        return selfGet(max, 0, t.inventory.getSizeInventory() - 1, sim);

    }

}
