package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfiguredFeature<FC extends IFeatureConfig, F extends Feature<FC>> {
   public static final ConfiguredFeature<?, ?> field_236263_a_ = new ConfiguredFeature<>(Feature.field_227245_q_, NoFeatureConfig.NO_FEATURE_CONFIG);
   public static final Codec<ConfiguredFeature<?, ?>> field_236264_b_ = Registry.FEATURE.<ConfiguredFeature<?, ?>>dispatch("name", (p_236266_0_) -> {
      return p_236266_0_.feature;
   }, Feature::func_236292_a_).withDefault(field_236263_a_);
   public static final Logger field_227226_a_ = LogManager.getLogger();
   public final F feature;
   public final FC config;

   public ConfiguredFeature(F featureIn, FC configIn) {
      this.feature = featureIn;
      this.config = configIn;
   }

   public ConfiguredFeature<?, ?> func_227228_a_(ConfiguredPlacement<?> p_227228_1_) {
      Feature<DecoratedFeatureConfig> feature = this.feature instanceof FlowersFeature ? Feature.DECORATED_FLOWER : Feature.DECORATED;
      return feature.func_225566_b_(new DecoratedFeatureConfig(this, p_227228_1_));
   }

   public ConfiguredRandomFeatureList<FC> func_227227_a_(float p_227227_1_) {
      return new ConfiguredRandomFeatureList<>(this, p_227227_1_);
   }

   public boolean func_236265_a_(ISeedReader p_236265_1_, StructureManager p_236265_2_, ChunkGenerator p_236265_3_, Random p_236265_4_, BlockPos p_236265_5_) {
      return this.feature.func_230362_a_(p_236265_1_, p_236265_2_, p_236265_3_, p_236265_4_, p_236265_5_, this.config);
   }
}