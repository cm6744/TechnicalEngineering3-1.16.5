package ten3.lib.capability.item;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import ten3.core.machine.pipe.PipeTile;
import ten3.lib.capability.Finder;
import ten3.lib.tile.CmTileMachine;
import ten3.util.ExcUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//connect generator with machine.
//#find - return a list of all connections
public class ItemStorageWayFinding extends InventoryWrapperCm {
    
    public static Map<BlockPos, List<IItemHandler>> nets = new HashMap<>();
    public static Map<BlockPos, Integer> caps = new HashMap<>();

    public static void updateNet(PipeTile tile) {
        find(tile);
    }

    IItemHandler object;

    public ItemStorageWayFinding(Direction d, CmTileMachine t) {

        super(d, t);
        object = ExcUtil.randomInCollection(nets.get(t.getPos()));

    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        return listTransferTo(nets.get(tile.getPos()), slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return listTransferFrom(nets.get(tile.getPos()), slot, amount, simulate);
    }

    @Override
    public int getSlots()
    {
        return object == null ? 0 : object.getSlots();
    }

    @Override
    public IInventory getInv()
    {
        IItemHandler handler = object;
        if(handler instanceof InvWrapper) {
            return ((InvWrapper) handler).getInv();
        }
        Inventory container = new Inventory(getSlots());
        for(int i = 0; i < container.getSizeInventory(); i++) {
            container.setInventorySlotContents(i, getStackInSlot(i));
        }
        return container;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return object == null ? 0 : object.getSlotLimit(slot);
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return object == null ? ItemStack.EMPTY : object.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
            IItemHandler handler = object;
            if(handler instanceof InvWrapper) {
                ((InvWrapper) handler).setStackInSlot(slot, stack);
            }
    }

    @SuppressWarnings("all")
    private static void find(PipeTile start, List<IItemHandler> init, HashMap<BlockPos, TileEntity> fouInit, Direction fr, int cap, boolean callFirst) {

        Finder.find(nets, caps,
                (t, d) -> ItemTransferor.handlerOf(t, d),
                (t) -> ((PipeTile) t).getCapacity(),
                (t) -> t instanceof PipeTile,
                start, init, fouInit, fr, cap, callFirst);

    }

    private static void find(PipeTile start) {
        find(start, null, null, null, Integer.MAX_VALUE, true);
    }


    public ItemStack listTransferTo(List<IItemHandler> es, int slot, ItemStack stack, boolean sim) {

        int size = getSizeCanTrs(es);

        if(es == null) return stack;
        if(size == 0) return stack;
        if(object == null) return stack;

        return object.insertItem(slot, stack, sim);

    }

    public ItemStack listTransferFrom(List<IItemHandler> es, int slot, int amount, boolean sim) {

        int size = getSizeCanTrs(es);

        if(es == null) return ItemStack.EMPTY;
        if(size == 0) return ItemStack.EMPTY;
        if(object == null) return ItemStack.EMPTY;

        return object.extractItem(slot, amount, sim);

    }

    public int getSizeCanTrs(List<IItemHandler> es) {
        if(es == null) return 0;
        return es.size();
    }

}
