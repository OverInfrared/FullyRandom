package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;

public class BlockStateFeatureConfig implements IFeatureConfig {
   public static final Codec<BlockStateFeatureConfig> field_236455_a_ = BlockState.field_235877_b_.fieldOf("state").xmap(BlockStateFeatureConfig::new, (p_236456_0_) -> {
      return p_236456_0_.field_227270_a_;
   }).codec();
   public final BlockState field_227270_a_;

   public BlockStateFeatureConfig(BlockState p_i225831_1_) {
      this.field_227270_a_ = p_i225831_1_;
   }
}