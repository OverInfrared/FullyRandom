package net.minecraft.world.storage;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public interface IWorldInfo {
   int getSpawnX();

   int getSpawnY();

   int getSpawnZ();

   long getGameTime();

   long getDayTime();

   boolean isThundering();

   boolean isRaining();

   void setRaining(boolean isRaining);

   boolean isHardcore();

   GameRules getGameRulesInstance();

   Difficulty getDifficulty();

   boolean isDifficultyLocked();

   default void addToCrashReport(CrashReportCategory category) {
      category.func_189529_a("Level spawn location", () -> {
         return CrashReportCategory.getCoordinateInfo(this.getSpawnX(), this.getSpawnY(), this.getSpawnZ());
      });
      category.func_189529_a("Level time", () -> {
         return String.format("%d game time, %d day time", this.getGameTime(), this.getDayTime());
      });
   }
}