package ten3.core.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import ten3.init.template.DefItem;
import ten3.util.ExcUtil;

public class WorldBag extends DefItem {

    public WorldBag() {

        super(16);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if(playerIn.isSneaking() && handIn == Hand.MAIN_HAND && !worldIn.isRemote()) {
            Item o = ExcUtil.randomInCollection(ForgeRegistries.ITEMS.getValues());
            ItemHandlerHelper.giveItemToPlayer(playerIn, (o).getDefaultInstance());
            playerIn.getHeldItemMainhand().shrink(1);
        }

        return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());

    }

}
