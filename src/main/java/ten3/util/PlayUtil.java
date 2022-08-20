package ten3.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;

public class PlayUtil {

    public static void giveAdvancement(String name, PlayerEntity player) {
        if(!(player instanceof ServerPlayerEntity)) return;
        Advancement adv = ((ServerPlayerEntity) player).server.getAdvancementManager()
                .getAdvancement(new ResourceLocation("ten3", name));
        AdvancementProgress ap = ((ServerPlayerEntity) player).getAdvancements().getProgress(adv);
        if (!ap.isDone()) {
            for(String criterion : ap.getRemaningCriteria()) {
                ((ServerPlayerEntity) player).getAdvancements().grantCriterion(adv, criterion);
            }
        }
    }

}
