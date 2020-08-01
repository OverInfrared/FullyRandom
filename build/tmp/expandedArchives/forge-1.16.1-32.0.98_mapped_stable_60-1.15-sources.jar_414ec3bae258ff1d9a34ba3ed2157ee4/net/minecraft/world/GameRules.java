package net.minecraft.world;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRules {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<GameRules.RuleKey<?>, GameRules.RuleType<?>> GAME_RULES = Maps.newTreeMap(Comparator.comparing((p_223597_0_) -> {
      return p_223597_0_.gameRuleName;
   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_FIRE_TICK = func_234903_a_("doFireTick", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> MOB_GRIEFING = func_234903_a_("mobGriefing", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> KEEP_INVENTORY = func_234903_a_("keepInventory", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_MOB_SPAWNING = func_234903_a_("doMobSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_MOB_LOOT = func_234903_a_("doMobLoot", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_TILE_DROPS = func_234903_a_("doTileDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_ENTITY_DROPS = func_234903_a_("doEntityDrops", GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> COMMAND_BLOCK_OUTPUT = func_234903_a_("commandBlockOutput", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> NATURAL_REGENERATION = func_234903_a_("naturalRegeneration", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_DAYLIGHT_CYCLE = func_234903_a_("doDaylightCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> LOG_ADMIN_COMMANDS = func_234903_a_("logAdminCommands", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SHOW_DEATH_MESSAGES = func_234903_a_("showDeathMessages", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.IntegerValue> RANDOM_TICK_SPEED = func_234903_a_("randomTickSpeed", GameRules.Category.UPDATES, GameRules.IntegerValue.create(3));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SEND_COMMAND_FEEDBACK = func_234903_a_("sendCommandFeedback", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> REDUCED_DEBUG_INFO = func_234903_a_("reducedDebugInfo", GameRules.Category.MISC, GameRules.BooleanValue.create(false, (p_223589_0_, p_223589_1_) -> {
      byte b0 = (byte)(p_223589_1_.get() ? 22 : 23);

      for(ServerPlayerEntity serverplayerentity : p_223589_0_.getPlayerList().getPlayers()) {
         serverplayerentity.connection.sendPacket(new SEntityStatusPacket(serverplayerentity, b0));
      }

   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SPECTATORS_GENERATE_CHUNKS = func_234903_a_("spectatorsGenerateChunks", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.IntegerValue> SPAWN_RADIUS = func_234903_a_("spawnRadius", GameRules.Category.PLAYER, GameRules.IntegerValue.create(10));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DISABLE_ELYTRA_MOVEMENT_CHECK = func_234903_a_("disableElytraMovementCheck", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.IntegerValue> MAX_ENTITY_CRAMMING = func_234903_a_("maxEntityCramming", GameRules.Category.MOBS, GameRules.IntegerValue.create(24));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_WEATHER_CYCLE = func_234903_a_("doWeatherCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_LIMITED_CRAFTING = func_234903_a_("doLimitedCrafting", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.IntegerValue> MAX_COMMAND_CHAIN_LENGTH = func_234903_a_("maxCommandChainLength", GameRules.Category.MISC, GameRules.IntegerValue.create(65536));
   public static final GameRules.RuleKey<GameRules.BooleanValue> ANNOUNCE_ADVANCEMENTS = func_234903_a_("announceAdvancements", GameRules.Category.CHAT, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DISABLE_RAIDS = func_234903_a_("disableRaids", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226682_y_ = func_234903_a_("doInsomnia", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226683_z_ = func_234903_a_("doImmediateRespawn", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false, (p_226686_0_, p_226686_1_) -> {
      for(ServerPlayerEntity serverplayerentity : p_226686_0_.getPlayerList().getPlayers()) {
         serverplayerentity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241775_l_, p_226686_1_.get() ? 1.0F : 0.0F));
      }

   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226679_A_ = func_234903_a_("drowningDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226680_B_ = func_234903_a_("fallDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226681_C_ = func_234903_a_("fireDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_230127_D_ = func_234903_a_("doPatrolSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_230128_E_ = func_234903_a_("doTraderSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_234895_F_ = func_234903_a_("forgiveDeadPlayers", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_234896_G_ = func_234903_a_("universalAnger", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
   private final Map<GameRules.RuleKey<?>, GameRules.RuleValue<?>> rules;

   public static <T extends GameRules.RuleValue<T>> GameRules.RuleKey<T> func_234903_a_(String p_234903_0_, GameRules.Category p_234903_1_, GameRules.RuleType<T> p_234903_2_) {
      GameRules.RuleKey<T> rulekey = new GameRules.RuleKey<>(p_234903_0_, p_234903_1_);
      GameRules.RuleType<?> ruletype = GAME_RULES.put(rulekey, p_234903_2_);
      if (ruletype != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + p_234903_0_);
      } else {
         return rulekey;
      }
   }

   public GameRules(DynamicLike<?> p_i231611_1_) {
      this();
      this.func_234901_a_(p_i231611_1_);
   }

   public GameRules() {
      this.rules = GAME_RULES.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_226684_0_) -> {
         return p_226684_0_.getValue().func_223579_a();
      }));
   }

   private GameRules(Map<GameRules.RuleKey<?>, GameRules.RuleValue<?>> p_i231612_1_) {
      this.rules = p_i231612_1_;
   }

   public <T extends GameRules.RuleValue<T>> T get(GameRules.RuleKey<T> key) {
      return (T)(this.rules.get(key));
   }

   public CompoundNBT write() {
      CompoundNBT compoundnbt = new CompoundNBT();
      this.rules.forEach((p_226688_1_, p_226688_2_) -> {
         compoundnbt.putString(p_226688_1_.gameRuleName, p_226688_2_.func_223552_b());
      });
      return compoundnbt;
   }

   private void func_234901_a_(DynamicLike<?> p_234901_1_) {
      this.rules.forEach((p_234902_1_, p_234902_2_) -> {
         p_234901_1_.get(p_234902_1_.gameRuleName).asString().result().ifPresent(p_234902_2_::func_223553_a);
      });
   }

   public GameRules func_234905_b_() {
      return new GameRules(this.rules.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_234904_0_) -> {
         return p_234904_0_.getValue().func_230314_f_();
      })));
   }

   public static void func_223590_a(GameRules.IRuleEntryVisitor p_223590_0_) {
      GAME_RULES.forEach((p_234906_1_, p_234906_2_) -> {
         func_234897_a_(p_223590_0_, p_234906_1_, p_234906_2_);
      });
   }

   private static <T extends GameRules.RuleValue<T>> void func_234897_a_(GameRules.IRuleEntryVisitor p_234897_0_, GameRules.RuleKey<?> p_234897_1_, GameRules.RuleType<?> p_234897_2_) {
      p_234897_0_.func_223481_a((GameRules.RuleKey)p_234897_1_, p_234897_2_);
      p_234897_2_.func_234914_a_(p_234897_0_,(GameRules.RuleKey) p_234897_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_234899_a_(GameRules p_234899_1_, @Nullable MinecraftServer p_234899_2_) {
      p_234899_1_.rules.keySet().forEach((p_234900_3_) -> {
         this.func_234898_a_(p_234900_3_, p_234899_1_, p_234899_2_);
      });
   }

   @OnlyIn(Dist.CLIENT)
   private <T extends GameRules.RuleValue<T>> void func_234898_a_(GameRules.RuleKey<T> p_234898_1_, GameRules p_234898_2_, @Nullable MinecraftServer p_234898_3_) {
      T t = p_234898_2_.get(p_234898_1_);
      this.<T>get(p_234898_1_).func_230313_a_(t, p_234898_3_);
   }

   public boolean getBoolean(GameRules.RuleKey<GameRules.BooleanValue> key) {
      return this.get(key).get();
   }

   public int getInt(GameRules.RuleKey<GameRules.IntegerValue> key) {
      return this.get(key).get();
   }

   public static class BooleanValue extends GameRules.RuleValue<GameRules.BooleanValue> {
      private boolean value;

      private static GameRules.RuleType<GameRules.BooleanValue> create(boolean p_223567_0_, BiConsumer<MinecraftServer, GameRules.BooleanValue> p_223567_1_) {
         return new GameRules.RuleType<>(BoolArgumentType::bool, (p_223574_1_) -> {
            return new GameRules.BooleanValue(p_223574_1_, p_223567_0_);
         }, p_223567_1_, GameRules.IRuleEntryVisitor::func_230482_b_);
      }

      private static GameRules.RuleType<GameRules.BooleanValue> create(boolean defaultValue) {
         return create(defaultValue, (p_223569_0_, p_223569_1_) -> {
         });
      }

      public BooleanValue(GameRules.RuleType<GameRules.BooleanValue> p_i51535_1_, boolean p_i51535_2_) {
         super(p_i51535_1_);
         this.value = p_i51535_2_;
      }

      protected void func_223555_a(CommandContext<CommandSource> p_223555_1_, String p_223555_2_) {
         this.value = BoolArgumentType.getBool(p_223555_1_, p_223555_2_);
      }

      public boolean get() {
         return this.value;
      }

      public void set(boolean p_223570_1_, @Nullable MinecraftServer p_223570_2_) {
         this.value = p_223570_1_;
         this.func_223556_a(p_223570_2_);
      }

      public String func_223552_b() {
         return Boolean.toString(this.value);
      }

      protected void func_223553_a(String p_223553_1_) {
         this.value = Boolean.parseBoolean(p_223553_1_);
      }

      public int func_223557_c() {
         return this.value ? 1 : 0;
      }

      protected GameRules.BooleanValue func_223213_e_() {
         return this;
      }

      protected GameRules.BooleanValue func_230314_f_() {
         return new GameRules.BooleanValue(this.type, this.value);
      }

      @OnlyIn(Dist.CLIENT)
      public void func_230313_a_(GameRules.BooleanValue p_230313_1_, @Nullable MinecraftServer p_230313_2_) {
         this.value = p_230313_1_.value;
         this.func_223556_a(p_230313_2_);
      }
   }

   public static enum Category {
      PLAYER("gamerule.category.player"),
      MOBS("gamerule.category.mobs"),
      SPAWNING("gamerule.category.spawning"),
      DROPS("gamerule.category.drops"),
      UPDATES("gamerule.category.updates"),
      CHAT("gamerule.category.chat"),
      MISC("gamerule.category.misc");

      private final String field_234907_h_;

      private Category(String p_i231613_3_) {
         this.field_234907_h_ = p_i231613_3_;
      }

      @OnlyIn(Dist.CLIENT)
      public String func_234908_a_() {
         return this.field_234907_h_;
      }
   }

   interface IRule<T extends GameRules.RuleValue<T>> {
      void call(GameRules.IRuleEntryVisitor p_call_1_, GameRules.RuleKey<T> p_call_2_, GameRules.RuleType<T> p_call_3_);
   }

   public interface IRuleEntryVisitor {
      default <T extends GameRules.RuleValue<T>> void func_223481_a(GameRules.RuleKey<T> p_223481_1_, GameRules.RuleType<T> p_223481_2_) {
      }

      default void func_230482_b_(GameRules.RuleKey<GameRules.BooleanValue> p_230482_1_, GameRules.RuleType<GameRules.BooleanValue> p_230482_2_) {
      }

      default void func_230483_c_(GameRules.RuleKey<GameRules.IntegerValue> p_230483_1_, GameRules.RuleType<GameRules.IntegerValue> p_230483_2_) {
      }
   }

   public static class IntegerValue extends GameRules.RuleValue<GameRules.IntegerValue> {
      private int value;

      private static GameRules.RuleType<GameRules.IntegerValue> func_223564_a(int p_223564_0_, BiConsumer<MinecraftServer, GameRules.IntegerValue> p_223564_1_) {
         return new GameRules.RuleType<>(IntegerArgumentType::integer, (p_223565_1_) -> {
            return new GameRules.IntegerValue(p_223565_1_, p_223564_0_);
         }, p_223564_1_, GameRules.IRuleEntryVisitor::func_230483_c_);
      }

      private static GameRules.RuleType<GameRules.IntegerValue> create(int p_223559_0_) {
         return func_223564_a(p_223559_0_, (p_223561_0_, p_223561_1_) -> {
         });
      }

      public IntegerValue(GameRules.RuleType<GameRules.IntegerValue> p_i51534_1_, int p_i51534_2_) {
         super(p_i51534_1_);
         this.value = p_i51534_2_;
      }

      protected void func_223555_a(CommandContext<CommandSource> p_223555_1_, String p_223555_2_) {
         this.value = IntegerArgumentType.getInteger(p_223555_1_, p_223555_2_);
      }

      public int get() {
         return this.value;
      }

      public String func_223552_b() {
         return Integer.toString(this.value);
      }

      protected void func_223553_a(String p_223553_1_) {
         this.value = func_223563_b(p_223553_1_);
      }

      @OnlyIn(Dist.CLIENT)
      public boolean func_234909_b_(String p_234909_1_) {
         try {
            this.value = Integer.parseInt(p_234909_1_);
            return true;
         } catch (NumberFormatException numberformatexception) {
            return false;
         }
      }

      private static int func_223563_b(String p_223563_0_) {
         if (!p_223563_0_.isEmpty()) {
            try {
               return Integer.parseInt(p_223563_0_);
            } catch (NumberFormatException numberformatexception) {
               GameRules.LOGGER.warn("Failed to parse integer {}", (Object)p_223563_0_);
            }
         }

         return 0;
      }

      public int func_223557_c() {
         return this.value;
      }

      protected GameRules.IntegerValue func_223213_e_() {
         return this;
      }

      protected GameRules.IntegerValue func_230314_f_() {
         return new GameRules.IntegerValue(this.type, this.value);
      }

      @OnlyIn(Dist.CLIENT)
      public void func_230313_a_(GameRules.IntegerValue p_230313_1_, @Nullable MinecraftServer p_230313_2_) {
         this.value = p_230313_1_.value;
         this.func_223556_a(p_230313_2_);
      }
   }

   public static final class RuleKey<T extends GameRules.RuleValue<T>> {
      private final String gameRuleName;
      private final GameRules.Category field_234910_b_;

      public RuleKey(String p_i231614_1_, GameRules.Category p_i231614_2_) {
         this.gameRuleName = p_i231614_1_;
         this.field_234910_b_ = p_i231614_2_;
      }

      public String toString() {
         return this.gameRuleName;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else {
            return p_equals_1_ instanceof GameRules.RuleKey && ((GameRules.RuleKey)p_equals_1_).gameRuleName.equals(this.gameRuleName);
         }
      }

      public int hashCode() {
         return this.gameRuleName.hashCode();
      }

      public String func_223576_a() {
         return this.gameRuleName;
      }

      public String func_234911_b_() {
         return "gamerule." + this.gameRuleName;
      }

      @OnlyIn(Dist.CLIENT)
      public GameRules.Category func_234912_c_() {
         return this.field_234910_b_;
      }
   }

   public static class RuleType<T extends GameRules.RuleValue<T>> {
      private final Supplier<ArgumentType<?>> field_223582_a;
      private final Function<GameRules.RuleType<T>, T> field_223583_b;
      private final BiConsumer<MinecraftServer, T> field_223584_c;
      private final GameRules.IRule<T> field_234913_d_;

      private RuleType(Supplier<ArgumentType<?>> p_i231615_1_, Function<GameRules.RuleType<T>, T> p_i231615_2_, BiConsumer<MinecraftServer, T> p_i231615_3_, GameRules.IRule<T> p_i231615_4_) {
         this.field_223582_a = p_i231615_1_;
         this.field_223583_b = p_i231615_2_;
         this.field_223584_c = p_i231615_3_;
         this.field_234913_d_ = p_i231615_4_;
      }

      public RequiredArgumentBuilder<CommandSource, ?> func_223581_a(String p_223581_1_) {
         return Commands.argument(p_223581_1_, this.field_223582_a.get());
      }

      public T func_223579_a() {
         return this.field_223583_b.apply(this);
      }

      public void func_234914_a_(GameRules.IRuleEntryVisitor p_234914_1_, GameRules.RuleKey<T> p_234914_2_) {
         this.field_234913_d_.call(p_234914_1_, p_234914_2_, this);
      }
   }

   public abstract static class RuleValue<T extends GameRules.RuleValue<T>> {
      protected final GameRules.RuleType<T> type;

      public RuleValue(GameRules.RuleType<T> type) {
         this.type = type;
      }

      protected abstract void func_223555_a(CommandContext<CommandSource> p_223555_1_, String p_223555_2_);

      public void func_223554_b(CommandContext<CommandSource> p_223554_1_, String p_223554_2_) {
         this.func_223555_a(p_223554_1_, p_223554_2_);
         this.func_223556_a(p_223554_1_.getSource().getServer());
      }

      protected void func_223556_a(@Nullable MinecraftServer p_223556_1_) {
         if (p_223556_1_ != null) {
            this.type.field_223584_c.accept(p_223556_1_, this.func_223213_e_());
         }

      }

      protected abstract void func_223553_a(String p_223553_1_);

      public abstract String func_223552_b();

      public String toString() {
         return this.func_223552_b();
      }

      public abstract int func_223557_c();

      protected abstract T func_223213_e_();

      protected abstract T func_230314_f_();

      @OnlyIn(Dist.CLIENT)
      public abstract void func_230313_a_(T p_230313_1_, @Nullable MinecraftServer p_230313_2_);
   }
}