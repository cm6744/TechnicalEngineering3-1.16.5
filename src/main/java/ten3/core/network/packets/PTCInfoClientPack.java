package ten3.core.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import ten3.core.client.ClientHolder;
import ten3.util.DireUtil;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PTCInfoClientPack {

    int ene;
    int itm;
    int res;
    int level;
    BlockPos pos;
    Direction d;

    public PTCInfoClientPack(PacketBuffer b) {

        ene = b.readInt();
        itm = b.readInt();
        res = b.readInt();
        level = b.readInt();
        pos = b.readBlockPos();
        d = b.readEnumValue(Direction.class);

    }

    public PTCInfoClientPack(int e, int i, int r, int lv, BlockPos p, Direction d) {

        ene = e;
        itm = i;
        res = r;
        pos = p;
        level = lv;
        this.d = d;

    }

    public void writeBuffer(PacketBuffer b) {

        b.writeInt(ene);
        b.writeInt(itm);
        b.writeInt(res);
        b.writeInt(level);
        b.writeBlockPos(pos);
        b.writeEnumValue(d);

    }

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(this::handler);
        cs.get().setPacketHandled(true);

    }

    public void handler() {

        ArrayList<Integer> energy = ClientHolder.energy.get(pos);
        ArrayList<Integer> item = ClientHolder.item.get(pos);
        int redstone;
        int lev;

        if(energy == null) {
            energy = new ArrayList<>();
            for(int i = 0; i < Direction.values().length; i++)
                energy.add(0);
        }
        if(item == null) {
            item = new ArrayList<>();
            for(int i = 0; i < Direction.values().length; i++)
                item.add(0);
        }

        int i = DireUtil.direToInt(d);
        energy.set(i, ene);
        item.set(i, itm);
        redstone = res;
        lev = level;

        ClientHolder.energy.put(pos, energy);
        ClientHolder.item.put(pos, item);
        ClientHolder.redstone.put(pos, redstone);
        ClientHolder.level.put(pos, lev);

    }

}
