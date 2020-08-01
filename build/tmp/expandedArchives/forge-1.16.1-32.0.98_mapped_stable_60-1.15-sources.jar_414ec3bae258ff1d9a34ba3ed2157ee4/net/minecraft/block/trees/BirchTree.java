package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class BirchTree extends Tree {
   @Nullable
   protected ConfiguredFeature<BaseTreeFeatureConfig, ?> func_225546_b_(Random p_225546_1_, boolean p_225546_2_) {
      return Feature.field_236291_c_.func_225566_b_(p_225546_2_ ? DefaultBiomeFeatures.field_230136_s_ : DefaultBiomeFeatures.field_226812_g_);
   }
}