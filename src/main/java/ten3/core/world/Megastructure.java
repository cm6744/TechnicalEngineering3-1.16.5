package ten3.core.world;

import net.minecraft.entity.player.PlayerEntity;

public class Megastructure {

    public String owner;
    public MegaType type;
    public int code;

    public Megastructure(PlayerEntity owner, MegaType type, int code) {
        this(owner.getName().getString(), type, code);
    }

    public Megastructure(String owner, MegaType type, int code) {
        this.owner = owner;
        this.type = type;
        this.code = code;
    }

}
