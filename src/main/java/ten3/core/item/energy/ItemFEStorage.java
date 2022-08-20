package ten3.core.item.energy;

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
import ten3.init.template.DefItem;
import ten3.lib.tile.PacketCapData;
import ten3.util.ItemUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFEStorage extends DefItem {

    int sto, rec, ext;

    public ItemFEStorage(int s, int r, int e) {
        super(1);
        sto = s; rec = r; ext = e;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return EnergyItemHelper.getState(this, sto, rec, ext);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ItemEnergyCapProvider(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return ItemUtil.getTag(stack, "energy") != 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - ItemUtil.getTag(stack, "energy") / (double) sto;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.rgb(1f, 0f, 0f);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {

        if(isInGroup(group)) {
            EnergyItemHelper.fillEmpty(this, items, sto, rec, ext);
            EnergyItemHelper.fillFull(this, items, sto, rec, ext);
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        EnergyItemHelper.addTooltip(tooltip, stack);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        EnergyItemHelper.setState(stack, sto, rec, ext);
    }

}
