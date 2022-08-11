package ten3.lib.tile;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.TER;
import ten3.core.block.Machine;
import ten3.core.block.MachinePostEvent;
import ten3.core.item.upgrades.LevelupItem;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.option.Level;
import ten3.util.DireUtil;
import ten3.lib.capability.energy.EnergyTransferor;
import ten3.lib.capability.energy.FEStorageTile;
import ten3.lib.capability.item.InventoryWrapperCm;
import ten3.lib.capability.item.ItemTransferor;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.RedstoneMode;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.*;
import ten3.util.ExcUtil;
import ten3.util.KeyUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class CmTileMachine extends CmTileEntity {

    public int CHOOSE_TYPE = -1;

    public final EnergyTransferor etr;
    public final ItemTransferor itr;

    public static int kFE(double k) {
        return (int) (1000 * k);
    }

    @Nullable
    public<T extends IRecipe<IInventory>> T getRcp(IRecipeType<T> type, ItemStack stack) {
        return ExcUtil.safeGetRecipe(world, type, new Inventory(stack)).orElse(null);
    }

    //data poses.
    //how to use them:
    //data.set(energy<-index, xxx<-value)
    public static final int energy = 2;
    public static final int maxEnergy = 3;
    public static final int progress = 0;
    public static final int maxProgress = 1;
    public static final int fuel = 4;
    public static final int maxFuel = 5;
    public static final int receive = 6;
    public static final int extract = 7;

    public static final int redMode = 10;
    public static final int facing = 11;
    public static final int actualEff = 12;
    public static final int efficient = 13;
    public static final int level = 14;
    //public static int tranMode = 11;

    public int efficientIn;
    public int initialEfficientIn;//initial vars won't be change!

    public IntArrayCm energyAllow = new IntArrayCm(6);
    public IntArrayCm itemAllow = new IntArrayCm(6);

    IFactoryContainer<? extends CmContainerMachine> fact;

    //canRewrite
    public int maxReceive;
    public int maxExtract;
    public int maxStorage;
    public int defaultModeEne;
    public int defaultModeItm;
    public int levelIn = 0;

    public int initialStorage;//initial vars won't be change!
    public int initialReceive;//initial vars won't be change!
    public int initialExtract;//initial vars won't be change!
    public int initialFacing;

    public int upgSlotFrom = 34;
    public int upgSlotTo = 39;

    public MacNBTManager nbtManager;
    public Progressor progressor = new Progressor();

    public CmTileMachine(String key) {

        super(key);
        fact = CmContainerMachine::new;

        etr = new EnergyTransferor(this);
        itr = new ItemTransferor(this);
        nbtManager = new MacNBTManager(this);

        addSlot(new SlotUpgCm(inventory, 34, 32, -28));
        addSlot(new SlotUpgCm(inventory, 35, 51, -28));
        addSlot(new SlotUpgCm(inventory, 36, 70, -28));
        addSlot(new SlotUpgCm(inventory, 37, 89, -28));
        addSlot(new SlotUpgCm(inventory, 38, 108, -28));
        addSlot(new SlotUpgCm(inventory, 39, 127, -28));

    }

    public void setCap(int rc, int ex, int store, int defene, int defitm, int eff) {

        initialReceive = maxReceive = rc;
        initialExtract = maxExtract = ex;
        initialStorage = maxStorage = store;
        defaultModeEne = defene;
        defaultModeItm = defitm;
        initialEfficientIn = efficientIn = eff;

    }

    public boolean customFitStackIn(ItemStack s, int slot) {
        return true;
    }

    public abstract Type typeOf();

    @Override
    public void init() {

        for(Direction d : Direction.values()) {
            setOpenEnergy(d, defaultModeEne);
            setOpenItem(d, defaultModeItm);
        }

        initialFacing = DireUtil.direToInt(directionOf());

    }

    @Override
    public void packets() {

        for(Direction d : Direction.values()) {
            MachinePostEvent.updateToClient(direCheckEnergy(d), direCheckItem(d), data.get(redMode), levelIn, pos, d);
        }

    }

    public void setActive(boolean a) {
        world.setBlockState(pos, getBlockState().with(Machine.active, a));
    }

    public void setFace(Direction f) {
        world.setBlockState(pos, getBlockState().with(Machine.dire, f));
    }

    public boolean isActive() {
        return getBlockState().hasProperty(Machine.active) ? getBlockState().get(Machine.active) : false;
    }

    protected Direction directionOf() {
        return getBlockState().hasProperty(Machine.dire) ? getBlockState().get(Machine.dire) : Direction.NORTH;
    }

    public void rdt(CompoundNBT nbt) {
        super.rdt(nbt);
        nbtManager.rdt(nbt);
    }

    public void wdt(CompoundNBT nbt) {
        super.wdt(nbt);
        nbtManager.wdt(nbt);
    }

    public PacketCapData packData() {
        return PacketCapData.of(maxExtract, maxReceive, maxStorage, efficientIn, levelIn);
    }

    public void postProgressUp() {

        progressor.progressOn(data, getActual());

    }

    private Queue<Direction> qr = Queues.newArrayDeque(Lists.newArrayList(Direction.values()));
    double actualEffPercent;

    public ITextComponent getDisplayWith() {

        return KeyUtil.translated(TER.modid + "." + id, TER.modid + ".level." + levelIn);

    }

    @Override
    protected boolean canDrop(ItemStack stack, int slot) {
        //do not drop upgrades
        if(slot >= upgSlotFrom && slot <= upgSlotTo) {
            return false;
        }
        if((stack.getItem() instanceof UpgradeItem) && !(stack.getItem() instanceof LevelupItem)) {
            return false;
        }

        return true;

    }

    public int nowUpgSize() {
        return (levelIn + 1) * 2;
    }

    public int maxUpgSize() {
        return 6;
    }

    private void upgradeUpdate(boolean startTick) {

        for(int i = 0; i < nowUpgSize(); i++) {
            ItemStack iks = inventory.getStackInSlot(i + upgSlotFrom);
            Item ik = iks.getItem();

            if(ik instanceof UpgradeItem) {
                SlotCm sl = inventory.match(i + upgSlotFrom);
                if(sl instanceof SlotUpgCm) {
                    ((SlotUpgCm) sl).lock(startTick);
                }
                if(startTick) {
                    ((UpgradeItem) ik).effect(this);
                }
                else {
                    efficientIn = initialEfficientIn;
                    maxExtract = initialExtract;
                    maxReceive = initialReceive;
                    maxStorage = initialStorage;//reset all, but data storage them to client!
                }
            }
        }

    }

    public boolean hasUpgrade(UpgradeItem item) {

        for(int i = 0; i < nowUpgSize(); i++) {
            ItemStack iks = inventory.getStackInSlot(i + upgSlotFrom);
            Item ik = iks.getItem();
            if(ik == item) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void update() {

        upgradeUpdate(true);

        component = getDisplayWith();

        //to client
        data.set(maxEnergy, maxStorage);
        data.set(receive, maxReceive);
        data.set(extract, maxExtract);
        data.set(efficient, efficientIn);
        data.set(level, levelIn);

        data.set(facing, initialFacing);
        setFace(DireUtil.intToDire(initialFacing));

        //avoid out of cap
        if(data.get(energy) > maxStorage) {
            data.set(energy, maxStorage);
        }

        //set actual efficientIn
        if(getTileAliveTime() % 4 == 0) {
            CHOOSE_TYPE = -1;//post to client render
            actualEffPercent = EfficientCalculator.gen(typeOf(), data);
            data.set(actualEff, (int) (actualEffPercent * efficientIn));
        }

        setActive(getActualPercent() > 0 && checkCanRun());

        //tran items
        if(checkCanRun() && isActive()) {
            //if(hasCapabilityOutputItem) {
            transferEnergy();
            transferItem();
        }

    }

    protected boolean can(Capability<?> cap, Direction d) {
        return true;
    }

    protected boolean canOpenItem(Direction d) {
       return true;
    }

    protected boolean canOpenEnergy(Direction d) {
        return true;
    }

    protected void transferEnergy() {
        etr.loopTransferFrom((maxReceive));
        etr.loopTransferTo((maxExtract));
    }

    protected void transferItem() {
        if(getTileAliveTime() % 10 == 0) {
            qr.offer(qr.remove());
            for(Direction d : qr) {
                itr.transferTo(d);
                itr.transferFrom(d);
            }
        }
    }

    //the son classes will override update,
    //and super.update will run firstly.
    //if it's not in endTick, upgrades won't have effects on.
    @Override
    public void endTick() {
        upgradeUpdate(false);
    }

    public int getActual() {

        return data.get(actualEff);

    }

    public double getActualPercent() {

        return actualEffPercent;

    }

    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return fact.create(p_createMenu_1_, p_createMenu_2_, this, pos, data);
    }

    public interface IFactoryContainer<T extends Container> {
        T create(int p_createMenu_1_, PlayerInventory p_createMenu_2_, CmTileMachine pt, BlockPos p_createMenu_3_, IIntArray data);
    }

    /*caps*/

    @Nonnull
    @Override
    @SuppressWarnings("all")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {

        if(Objects.equals(cap, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            if(side == null) return (LazyOptional<T>) crtLazyItm(side);

            return direCheckItem(side) != FaceOption.OFF && can(cap, side)
                    ? (LazyOptional<T>) crtLazyItm(side) : LazyOptional.empty();
        }

        if(Objects.equals(cap, CapabilityEnergy.ENERGY)) {
            if(side == null) return (LazyOptional<T>) crtLazyEne(null);

            return direCheckEnergy(side) != FaceOption.OFF && can(cap, side)
                    ? (LazyOptional<T>) crtLazyEne(side) : LazyOptional.empty();
        }

        return LazyOptional.empty();

    }

    public LazyOptional<IItemHandler> crtLazyItm(Direction d) {

        return LazyOptional.of(() -> new InventoryWrapperCm(d, this));

    }
    public LazyOptional<IEnergyStorage> crtLazyEne(Direction d) {

        return LazyOptional.of(() -> new FEStorageTile(d, this));

    }

    public boolean checkCanRun() {
        boolean power = world.isBlockPowered(pos);

        switch(data.get(redMode)) {
            case RedstoneMode.LOW:
                return !power;
            case RedstoneMode.HIGH:
                return power;
            case RedstoneMode.OFF:
                return true;
        }

        return false;
    }

    public boolean energySupportRun() {

        if(typeOf() == Type.MACHINE_PROCESS) return data.get(energy) >= efficientIn;
        if(typeOf() == Type.MACHINE_EFFECT) return data.get(energy) >= efficientIn;
        if(typeOf() == Type.GENERATOR) return data.get(energy) + getActual() <= maxStorage;
        return true;

    }

    public boolean effectApplyTickOn(double min, double max) {

         if(typeOf() != Type.MACHINE_EFFECT) return true;

         int k22 = ((int) ((1 - getActualPercent()) * max + min * (1 - efficientIn / (double)initialEfficientIn)));
         if(k22 <= 0) return true;

         return getTileAliveTime() % k22 == 0;

    }

    public int direCheckItem(Direction d) {
        if(!can(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d)) {
            return FaceOption.NONE;
        }
        return itemAllow.get(DireUtil.direToInt(d));
    }

    public void setOpenItem(Direction d, int mode) {
        if(canOpenItem(d))
        itemAllow.set(DireUtil.direToInt(d), mode);
    }

    public int direCheckEnergy(Direction d) {
        if(!can(CapabilityEnergy.ENERGY, d)) {
            return FaceOption.NONE;
        }
        return energyAllow.get(DireUtil.direToInt(d));
    }

    public void setOpenEnergy(Direction d, int mode) {
        if(canOpenEnergy(d))
        energyAllow.set(DireUtil.direToInt(d), mode);
    }

}
