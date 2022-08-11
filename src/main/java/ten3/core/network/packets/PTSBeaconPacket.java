package ten3.core.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import ten3.core.machine.useenergy.beacon.BeaconTile;
import ten3.lib.tile.CmTileMachine;

public class PTSBeaconPacket extends ServerIntSetterPacket {

    public PTSBeaconPacket(PacketBuffer b) {
        super(b);
    }

    public PTSBeaconPacket(int mode, BlockPos pos) {
        super(mode, pos);
    }

}
