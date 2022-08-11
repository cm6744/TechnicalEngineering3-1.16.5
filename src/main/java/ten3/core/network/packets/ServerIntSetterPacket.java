package ten3.core.network.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import ten3.lib.tile.CmTileMachine;

import java.util.function.Supplier;

public class ServerIntSetterPacket {

    int mode;
    BlockPos pos;

    public ServerIntSetterPacket(PacketBuffer b) {

        mode = b.readInt();
        pos = b.readBlockPos();

    }

    public ServerIntSetterPacket(int mode, BlockPos pos) {

        this.mode = mode;
        this.pos = pos;

    }

    public final void writeBuffer(PacketBuffer b) {

        b.writeInt(mode);
        b.writeBlockPos(pos);

    }

    public final void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler(cs.get().getSender());
        });
        cs.get().setPacketHandled(true);

    }

    protected final void handler(PlayerEntity player) {

        World world = player.world;
        TileEntity e = world.getTileEntity(pos);
        if(e != null) {
            run(e);
        }

    }

    protected void run(TileEntity tile) {

    }

}
