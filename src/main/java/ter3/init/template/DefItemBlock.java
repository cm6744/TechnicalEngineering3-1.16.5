package ten3.init.template;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import ten3.TER;
import ten3.init.tab.DefGroup;

public class DefItemBlock extends BlockItem {

    public DefItemBlock(Block b) {

        this(b, DefGroup.BLOCK, 64);

    }

    public DefItemBlock(Block b, int size) {

        this(b, DefGroup.BLOCK, size);

    }

    public DefItemBlock(Block b, ItemGroup g, int size) {

        super(b, new Properties().group(g).maxStackSize(size));

    }

    @Override
    protected String getDefaultTranslationKey() {

        return TER.modid + "." + getRegistryName().getPath();

    }

}
