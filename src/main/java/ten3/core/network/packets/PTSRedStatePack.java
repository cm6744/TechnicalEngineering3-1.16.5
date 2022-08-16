package ten3.core.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import ten3.lib.tile.CmTileMachine;

public class PTSRedStatePack extends ServerIntSetterPacket {

    public PTSRedStatePack(PacketBuffer b) {
        super(b);
    }

    public PTSRedStatePack(int mode, BlockPos pos) {
        super(mode, pos);
    }

    @Override
    protected void run(TileEntity tile) {
        ((CmTileMachine) tile).data.set(CmTileMachine.RED_MODE, mode);
    }

}
