package ten3.core.world;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.List;

public class MegastructureData extends WorldSavedData {

    List<Megastructure> megas = new ArrayList<>();

    public static MegastructureData create(ServerWorld world) {

        DimensionSavedDataManager manager = world.getSavedData();
        return manager.getOrCreate(() -> new MegastructureData(), "ten3_megastructure");

    }

    public MegastructureData() {
        super("ten3_megastructure");
    }

    public void addMega(Megastructure mega) {

        megas.add(mega);
        markDirty();

    }

    public void removeMega(int code) {

        for(Megastructure m : megas) {
            if(m.code == code) {
                megas.remove(m);
                markDirty();
                break;
            }
        }

    }

    public List<Megastructure> getOwnerMega(PlayerEntity player) {

        List<Megastructure> rt = new ArrayList<>();

        for(Megastructure m : megas) {
            if(m.owner.equals(player.getName().getString())) {
                rt.add(m);
            }
        }

        return rt;

    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT listNBT = (ListNBT) nbt.get("list");
        if (listNBT != null) {
            for (INBT value : listNBT) {
                CompoundNBT tag = (CompoundNBT) value;
                String owner = tag.getString("mega_owner");
                MegaType type = MegaType.valueOf(MegaType.class, tag.getString("mega_type"));
                int code = tag.getInt("mega_code");
                addMega(new Megastructure(owner, type, code));
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT listNBT = new ListNBT();
        megas.stream().forEach((s) -> {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("mega_owner", s.owner);
            compoundNBT.putInt("mega_code", s.code);
            compoundNBT.putString("mega_type", s.type.toString());
            listNBT.add(compoundNBT);
        });
        compound.put("list", listNBT);
        return compound;
    }



}
