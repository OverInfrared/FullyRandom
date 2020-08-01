package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public interface IServerWorldInfo extends ISpawnWorldInfo {
   String getWorldName();

   void setThundering(boolean thunderingIn);

   int getRainTime();

   void setRainTime(int time);

   void setThunderTime(int time);

   int getThunderTime();

   default void addToCrashReport(CrashReportCategory category) {
      ISpawnWorldInfo.super.addToCrashReport(category);
      category.func_189529_a("Level name", this::getWorldName);
      category.func_189529_a("Level game mode", () -> {
         return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.getGameType().getName(), this.getGameType().getID(), this.isHardcore(), this.areCommandsAllowed());
      });
      category.func_189529_a("Level weather", () -> {
         return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.getRainTime(), this.isRaining(), this.getThunderTime(), this.isThundering());
      });
   }

   int func_230395_g_();

   void func_230391_a_(int p_230391_1_);

   int func_230399_u_();

   void func_230396_g_(int p_230396_1_);

   int func_230400_v_();

   void func_230397_h_(int p_230397_1_);

   void func_230394_a_(UUID p_230394_1_);

   GameType getGameType();

   void func_230393_a_(WorldBorder.Serializer p_230393_1_);

   WorldBorder.Serializer func_230398_q_();

   boolean isInitialized();

   void setInitialized(boolean initializedIn);

   boolean areCommandsAllowed();

   void func_230392_a_(GameType p_230392_1_);

   TimerCallbackManager<MinecraftServer> getScheduledEvents();

   void setGameTime(long time);

   void setDayTime(long time);
}