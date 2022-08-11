package ten3.lib.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public abstract class CmContainer extends Container {

    public final IIntArray data;
    public CmTileMachine tileEntity;
    public PlayerEntity player;
    public String name;
    public BlockPos pos;

    public CmContainer(ContainerType c, int id, PlayerInventory pi, CmTileMachine tile, BlockPos pos, IIntArray data) {

        super(c, id);

        this.data = data;
        this.pos = pos;
        tileEntity = tile;
        player = pi.player;

        layoutPlayerInventorySlots(pi, 8, 84);

        if(data != null) {
            trackIntArray(data);
        }

        name = tile.id;

        for(int i = 0; i < tile.slots.size(); i++) {
            addSlot(tile.slots.get(i));
        }

    }

    public void layoutPlayerInventorySlots(IInventory inventory, int left, int top) {

        int x;
        int y;
        for (x = 0; x < 3; ++x) {
            for(y = 0; y < 9; ++y) {//9~27
                addSlot(new Slot(inventory, y + (x + 1) * 9, left + y * 18, top + x * 18));
            }
        }
        for (x = 0; x < 9; ++x) {//0~9
            addSlot(new Slot(inventory, x, left + x * 18, top + 58));
        }

    }

    @Override
    public NonNullList<ItemStack> getInventory() {
        return super.getInventory();
    }

    public ItemStack getStack(int i) {

        return getInventory().get(i);

    }

    public IIntArray getData() {

        return data;

    }

    public boolean canInteractWith(PlayerEntity player) {

        return true;

    }

}
