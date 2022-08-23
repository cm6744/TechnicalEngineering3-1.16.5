package ten3.lib.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IntArray;
import net.minecraft.util.text.ITextComponent;
import ten3.TConst;
import ten3.core.network.Network;
import ten3.core.network.check.PTCCheckPack;
import ten3.core.network.check.PTSCheckPack;
import ten3.init.ContInit;
import ten3.init.TileInit;
import ten3.lib.capability.item.InventoryCm;
import ten3.lib.wrapper.*;
import ten3.util.KeyUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public abstract class CmTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public IntArrayCm data = ContInit.crt();
    public ITextComponent component;
    public String id;
    public ArrayList<SlotCm> slots = new ArrayList<>();
    public InventoryCm inventory = new InventoryCm(40, this);

    boolean init;

    public void addSlot(SlotCm s) {
        slots.add(s);
    }

    public CmTileEntity(String key) {
        super(TileInit.getType(key));
        component = KeyUtil.translated(TConst.modid + "." + key);
        id = key;
    }

    public CmTileEntity(TileEntityType<?> type, String fullTranslationKey) {
        super(type);
        component = KeyUtil.translated(fullTranslationKey);
    }

    @Nonnull
    public int[] getItemFirstTransferSlot(Item i) {
        return new int[] {};
    }

    public void rdt(CompoundNBT nbt) {}

    public void wdt(CompoundNBT nbt) {}

    public void read(BlockState state, CompoundNBT nbt) {

        rdt(nbt);
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            inventory.setInventorySlotContents(i, ItemStack.read(nbt.getCompound("item" + i)));
        }
        init = nbt.getBoolean("init");

        super.read(state, nbt);

    }

    public CompoundNBT write(CompoundNBT compound) {

        wdt(compound);
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            compound.put("item" + i, inventory.getStackInSlot(i).copy().serializeNBT());
        }
        compound.putBoolean("init", init);

        return super.write(compound);

    }

    boolean loaded;

    @Override
    public void onLoad() {

        super.onLoad();
        loaded = true;

    }

    @Override
    public void remove() {

        if(world == null) return;

        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            if(!canDrop(inventory.getStackInSlot(i), i)) continue;
            Block.spawnAsEntity(world, pos, inventory.getStackInSlot(i));
        }

        super.remove();

    }

    protected boolean canDrop(ItemStack stack, int slot) {
        return true;
    }

    public static void sendCheckPack() {

        Network.sendToClient(new PTCCheckPack());

    }

    @Deprecated
    public static int UPDATE_INITIAL_TIME = 30;

    private boolean init_rerun;
    protected int globalTimer = 0;

    public int getTileAliveTime() {
        return globalTimer;
    }

    public void tick() {

        if(world == null) return;
        if(pos == null) return;

        if(!world.isRemote()) {
            globalTimer++;
            if(!init) {
                init = true;
                init();
            }
            if(!init_rerun) {
                if(loaded) {
                    if(PTSCheckPack.GET) {
                        init_rerun = true;
                        packets();
                    }
                    else {
                        sendCheckPack();
                    }
                }
            }
            else {//after init
                update();
                endTick();
            }
        }

        updateRemote();

    }

    public void endTick() {}

    public void init() {}
    public void packets() {}

    public void updateRemote() {}

    public void update() {}

    public ITextComponent getDisplayName() {

        return component;

    }

    protected IntArray createData() {

        return data;

    }

    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {

        return null;

    }

}
