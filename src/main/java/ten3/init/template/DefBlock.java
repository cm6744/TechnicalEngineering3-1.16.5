package ten3.init.template;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraftforge.common.ToolType;
import ten3.util.KeyUtil;
import ten3.util.ExcUtil;

import java.util.ArrayList;
import java.util.List;

public class DefBlock extends Block {

    public static Properties build(double h, double r, Material m, SoundType s, ToolType t, int l, int light, boolean solid) {

        Properties p = Properties
                .create(m)
                .harvestTool(t)
                .hardnessAndResistance((float) h, (float) r)
                .harvestLevel(l)
                .setRequiresTool()
                .setLightLevel((state) -> light)
                .sound(s);
        if(!solid) p.notSolid();

        return p;

    }

    public DefBlock(double h, double r, Material m, SoundType s, ToolType t, int l, int light, boolean solid) {

        super(build(h, r, m, s, t, l, light, solid));

    }

    public DefBlock(Properties p) {

        super(p);

    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        ArrayList<ItemStack> l = new ArrayList<>();
        l.add(asItem().getDefaultInstance());
        return l;

    }

    @Override
    public String getTranslationKey() {

        return KeyUtil.getKey(ExcUtil.regNameOf(this));

    }

}
