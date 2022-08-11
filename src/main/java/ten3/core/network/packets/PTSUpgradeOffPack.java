package ten3.core.network.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.CmTileMachine;

import java.util.function.Supplier;

public class PTSUpgradeOffPack {

    ItemStack stack;
    BlockPos pos;

    public PTSUpgradeOffPack(PacketBuffer b) {

        stack = b.readItemStack();
        pos = b.readBlockPos();

    }

    public PTSUpgradeOffPack(ItemStack off, BlockPos pos) {

        stack = off;
        this.pos = pos;

    }

    public void writeBuffer(PacketBuffer b) {

        b.writeItemStack(stack);
        b.writeBlockPos(pos);

    }

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler(cs.get().getSender());
        });
        cs.get().setPacketHandled(true);

    }

    public void handler(PlayerEntity player) {

        PlayerEntity pl = player;
        World world = pl.world;
        TileEntity e = world.getTileEntity(pos);
        if(e != null && stack.getItem() instanceof UpgradeItem) {
            ((UpgradeItem) stack.getItem()).reset((CmTileMachine) e);
        }

    }

}
