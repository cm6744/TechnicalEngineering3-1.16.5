package ten3.core.network.check;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import ten3.core.network.Network;

import java.util.function.Supplier;

public class PTCCheckPack {

    public PTCCheckPack(PacketBuffer b) {}

    public PTCCheckPack() {}

    public void writeBuffer(PacketBuffer b) {}

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler();
        });
        cs.get().setPacketHandled(true);

    }

    public void handler() {

        Network.sendToServer(new PTSCheckPack());

    }

}
