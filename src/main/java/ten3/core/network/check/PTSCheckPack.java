package ten3.core.network.check;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import ten3.lib.tile.CmTileMachine;

import java.util.function.Supplier;

public class PTSCheckPack {

    public static boolean GET = false;

    public PTSCheckPack(PacketBuffer b) {}

    public PTSCheckPack() {}

    public void writeBuffer(PacketBuffer b) {}

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler();
        });
        cs.get().setPacketHandled(true);

    }

    public void handler() {

        GET = true;

    }

}
