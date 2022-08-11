package ten3.core.network;

import net.minecraftforge.common.ForgeConfigSpec;

public class ZConfigs {

    public static ForgeConfigSpec config;
    public static ForgeConfigSpec.IntValue starlight_speed;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Spread Speed");
        starlight_speed = builder.comment("Spread Speed of Starlight").defineInRange("speed", 2, 0, 100);
        builder.pop();
        config = builder.build();
    }

}
