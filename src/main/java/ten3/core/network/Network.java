package ten3.core.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import ten3.TER;
import ten3.core.network.check.PTCCheckPack;
import ten3.core.network.check.PTSCheckPack;
import ten3.core.network.packets.PTCInfoClientPack;
import ten3.core.network.packets.PTSRedStatePack;
import ten3.core.network.packets.PTSUpgradeOffPack;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Network {

    public static SimpleChannel instance;
    static int id;

    public static void register() {

        instance = NetworkRegistry.newSimpleChannel
                (
                        new ResourceLocation(TER.modid, "ten3_network_handler"),
                        () -> "1.0",
                        (v) -> true,
                        (v) -> true
                );
        instance.registerMessage
                (
                        id++,
                        PTSRedStatePack.class,
                        PTSRedStatePack::writeBuffer,
                        PTSRedStatePack::new,
                        PTSRedStatePack::run
                );
        instance.registerMessage
                (
                        id++,
                        PTSCheckPack.class,
                        PTSCheckPack::writeBuffer,
                        PTSCheckPack::new,
                        PTSCheckPack::run
                );
        instance.registerMessage
                (
                        id++,
                        PTCCheckPack.class,
                        PTCCheckPack::writeBuffer,
                        PTCCheckPack::new,
                        PTCCheckPack::run
                );
        instance.registerMessage
                (
                        id++,
                        PTCInfoClientPack.class,
                        PTCInfoClientPack::writeBuffer,
                        PTCInfoClientPack::new,
                        PTCInfoClientPack::run
                );
        instance.registerMessage
                (
                        id++,
                        PTSUpgradeOffPack.class,
                        PTSUpgradeOffPack::writeBuffer,
                        PTSUpgradeOffPack::new,
                        PTSUpgradeOffPack::run
                );

    }

    public static void sendToServer(Object o) {
        instance.sendToServer(o);
    }

    public static void sendToClient(Object o) {
        instance.send(PacketDistributor.ALL.noArg(), o);
    }

    @SubscribeEvent
    public static void doReg(FMLCommonSetupEvent e) {

        register();

    }

}
