package ten3.core.item.energy;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ten3.core.machine.cell.Cell;
import ten3.init.template.DefItemBlock;
import ten3.init.TileInit;
import ten3.lib.tile.CmTileMachine;
import ten3.util.ItemUtil;

import javax.annotation.Nullable;
import java.util.List;

public class BlockItemFEStorage extends DefItemBlock {

    //in game item do

    private CmTileMachine getBind() {
        return (CmTileMachine) TileInit.getType(getRegistryName().getPath()).create();
    }

    public BlockItemFEStorage(Block b) {
        super(b, 1);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        int level = ItemUtil.getTag(stack, "level");
        CmTileMachine t = getBind();
        t.levelIn = level;
        return t.getDisplayWith();
    }

    //in init item do(Tab or crafting...)

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ItemEnergyCapProvider(stack);
    }

    @Override
    public ItemStack getDefaultInstance() {
        CmTileMachine t = getBind();
        return EnergyItemHelper.getState(this, t.maxStorage, t.maxReceive, t.maxExtract);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return ItemUtil.getTag(stack, "energy") != 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if(ItemUtil.getTag(stack, "maxEnergy") == 0) return 0;
        return 1 - ItemUtil.getTag(stack, "energy") / (double) ItemUtil.getTag(stack, "maxEnergy");
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.rgb(1f, 0f, 0f);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {

        if(isInGroup(group)) {
            CmTileMachine t = getBind();
            EnergyItemHelper.fillEmpty(this, items, t.maxStorage, t.maxReceive, t.maxExtract);

            if(getBlock() instanceof Cell) {
                EnergyItemHelper.fillFull(this, items, t.maxStorage, t.maxReceive, t.maxExtract);
            }
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        EnergyItemHelper.addTooltip(tooltip, stack);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        CmTileMachine t = getBind();
        EnergyItemHelper.setState(stack, t.maxStorage, t.maxReceive, t.maxExtract);
    }

}
