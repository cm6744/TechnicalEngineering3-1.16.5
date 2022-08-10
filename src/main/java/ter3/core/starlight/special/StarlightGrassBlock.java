package ten3.core.starlight.special;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;

import java.util.List;

public class StarlightGrassBlock extends StarlightBase {

    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

    public StarlightGrassBlock() {

        super(build(0.6, 0.6, Material.PLANTS, SoundType.PLANT, ToolType.SHOVEL, -1, 0, true).tickRandomly());

    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Lists.newArrayList(Items.DIRT.getDefaultInstance());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }

}
