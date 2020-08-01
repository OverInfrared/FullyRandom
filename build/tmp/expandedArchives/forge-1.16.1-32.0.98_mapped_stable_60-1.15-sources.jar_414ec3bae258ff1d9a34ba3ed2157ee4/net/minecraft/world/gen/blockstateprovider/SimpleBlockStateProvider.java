package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SimpleBlockStateProvider extends BlockStateProvider {
   public static final Codec<SimpleBlockStateProvider> field_236809_b_ = BlockState.field_235877_b_.fieldOf("state").xmap(SimpleBlockStateProvider::new, (p_236810_0_) -> {
      return p_236810_0_.field_227405_b_;
   }).codec();
   private final BlockState field_227405_b_;

   public SimpleBlockStateProvider(BlockState p_i225860_1_) {
      this.field_227405_b_ = p_i225860_1_;
   }

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.field_227394_a_;
   }

   public BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_) {
      return this.field_227405_b_;
   }
}