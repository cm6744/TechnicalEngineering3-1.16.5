package ten3.core.network.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import ten3.lib.tile.CmTileMachine;

import java.util.function.Supplier;

public class PTSRedStatePack extends ServerIntSetterPacket {

    public PTSRedStatePack(PacketBuffer b) {
        super(b);
    }

    public PTSRedStatePack(int mode, BlockPos pos) {
        super(mode, pos);
    }

    @Override
    protected void run(TileEntity tile) {
        ((CmTileMachine) tile).data.set(CmTileMachine.redMode, mode);
    }

}
