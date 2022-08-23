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

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
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
