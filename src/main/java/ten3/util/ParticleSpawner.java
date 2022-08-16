package ten3.util;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ParticleSpawner {

    public static IParticleData ASHORE = new RedstoneParticleData(0.5f, 0.5f, 0.5f, 0.5f);
    public static IParticleData RANGE = new RedstoneParticleData(1f, 0.05f, 0.05f, 1f);

    public static void spawnClt(IParticleData data, double x, double y, double z, double spread) {

        World world = Minecraft.getInstance().world;
        if(world != null)
        world.addParticle(data, x, y, z, spread, spread, spread);

    }

    public static void spawnInRangeClt(IParticleData data, BlockPos center, int radius, double thick) {

        WorkUtil.runIn(radius, center, (pin) -> {
            double dst = Math.sqrt(pin.distanceSq(center));
            if(dst <= radius && dst >= radius - thick) {
                double xp = 0.75;
                double yp = 0.75;
                double zp = 0.75;
                spawnClt(data, pin.getX() + xp, pin.getY() + yp, pin.getZ() + zp, 0);
            }
            return false;
        });

    }

}
