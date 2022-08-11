package ten3.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import ten3.TER;
import ten3.TechnicalEngineering;

import java.util.HashMap;
import java.util.Map;

public class SoundInit {

    static Map<String, RegistryObject<SoundEvent>> regs = new HashMap<>();
    public static Map<String, SoundEvent> sounds = new HashMap<>();

    public static void regAll() {


    }

    public static void regSound(String id) {

        ResourceLocation rl = new ResourceLocation(TER.modid, id);
        regSound(id, new SoundEvent(rl));

    }

    public static void regSound(String id, SoundEvent im) {

        RegistryObject<SoundEvent> reg = TechnicalEngineering.SOUNDS.register(id, () -> im);
        regs.put(id, reg);
        sounds.put(id, im);

    }

    public static SoundEvent getSound(String id) {

        return sounds.get(id);

    }

}
