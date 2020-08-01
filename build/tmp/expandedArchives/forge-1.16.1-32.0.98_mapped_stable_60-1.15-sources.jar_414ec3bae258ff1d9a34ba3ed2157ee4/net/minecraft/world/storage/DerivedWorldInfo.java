package net.minecraft.world.storage;

import java.util.UUID;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public class DerivedWorldInfo implements IServerWorldInfo {
   private final IServerConfiguration field_237244_a_;
   private final IServerWorldInfo delegate;

   public DerivedWorldInfo(IServerConfiguration p_i232150_1_, IServerWorldInfo p_i232150_2_) {
      this.field_237244_a_ = p_i232150_1_;
      this.delegate = p_i232150_2_;
   }

   public int getSpawnX() {
      return this.delegate.getSpawnX();
   }

   public int getSpawnY() {
      return this.delegate.getSpawnY();
   }

   public int getSpawnZ() {
      return this.delegate.getSpawnZ();
   }

   public long getGameTime() {
      return this.delegate.getGameTime();
   }

   public long getDayTime() {
      return this.delegate.getDayTime();
   }

   public String getWorldName() {
      return this.field_237244_a_.getWorldName();
   }

   public int func_230395_g_() {
      return this.delegate.func_230395_g_();
   }

   public void func_230391_a_(int p_230391_1_) {
   }

   public boolean isThundering() {
      return this.delegate.isThundering();
   }

   public int getThunderTime() {
      return this.delegate.getThunderTime();
   }

   public boolean isRaining() {
      return this.delegate.isRaining();
   }

   public int getRainTime() {
      return this.delegate.getRainTime();
   }

   public GameType getGameType() {
      return this.field_237244_a_.getGameType();
   }

   public void setSpawnX(int x) {
   }

   public void setSpawnY(int y) {
   }

   public void setSpawnZ(int z) {
   }

   public void setGameTime(long time) {
   }

   public void setDayTime(long time) {
   }

   public void setSpawn(BlockPos spawnPoint) {
   }

   public void setThundering(boolean thunderingIn) {
   }

   public void setThunderTime(int time) {
   }

   public void setRaining(boolean isRaining) {
   }

   public void setRainTime(int time) {
   }

   public void func_230392_a_(GameType p_230392_1_) {
   }

   public boolean isHardcore() {
      return this.field_237244_a_.isHardcore();
   }

   public boolean areCommandsAllowed() {
      return this.field_237244_a_.areCommandsAllowed();
   }

   public boolean isInitialized() {
      return this.delegate.isInitialized();
   }

   public void setInitialized(boolean initializedIn) {
   }

   public GameRules getGameRulesInstance() {
      return this.field_237244_a_.getGameRulesInstance();
   }

   public WorldBorder.Serializer func_230398_q_() {
      return this.delegate.func_230398_q_();
   }

   public void func_230393_a_(WorldBorder.Serializer p_230393_1_) {
   }

   public Difficulty getDifficulty() {
      return this.field_237244_a_.getDifficulty();
   }

   public boolean isDifficultyLocked() {
      return this.field_237244_a_.isDifficultyLocked();
   }

   public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
      return this.delegate.getScheduledEvents();
   }

   public int func_230399_u_() {
      return 0;
   }

   public void func_230396_g_(int p_230396_1_) {
   }

   public int func_230400_v_() {
      return 0;
   }

   public void func_230397_h_(int p_230397_1_) {
   }

   public void func_230394_a_(UUID p_230394_1_) {
   }

   public void addToCrashReport(CrashReportCategory category) {
      category.func_71507_a("Derived", true);
      this.delegate.addToCrashReport(category);
   }
}