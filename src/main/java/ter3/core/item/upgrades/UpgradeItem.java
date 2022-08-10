package ten3.core.item.upgrades;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import ten3.init.template.DefItem;
import ten3.lib.tile.CmTileMachine;
import ten3.util.KeyUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class UpgradeItem extends DefItem {

    public UpgradeItem() {

        super(1);

    }

    public abstract boolean effect(CmTileMachine tile);

    @Deprecated
    public void reset(CmTileMachine tile) {}

    @Deprecated
    public int limit() {
        return 0;
    }

}
