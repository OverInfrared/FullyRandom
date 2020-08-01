package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.BlockPos;

public class WeightedBlockStateProvider extends BlockStateProvider {
   public static final Codec<WeightedBlockStateProvider> field_236811_b_ = WeightedList.func_234002_a_(BlockState.field_235877_b_).comapFlatMap(WeightedBlockStateProvider::func_236812_a_, (p_236813_0_) -> {
      return p_236813_0_.field_227406_b_;
   }).fieldOf("entries").codec();
   private final WeightedList<BlockState> field_227406_b_;

   private static DataResult<WeightedBlockStateProvider> func_236812_a_(WeightedList<BlockState> p_236812_0_) {
      return p_236812_0_.func_234005_b_() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new WeightedBlockStateProvider(p_236812_0_));
   }

   private WeightedBlockStateProvider(WeightedList<BlockState> p_i225862_1_) {
      this.field_227406_b_ = p_i225862_1_;
   }

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.field_227395_b_;
   }

   public WeightedBlockStateProvider() {
      this(new WeightedList<>());
   }

   public WeightedBlockStateProvider func_227407_a_(BlockState p_227407_1_, int p_227407_2_) {
      this.field_227406_b_.func_226313_a_(p_227407_1_, p_227407_2_);
      return this;
   }

   public BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_) {
      return this.field_227406_b_.func_226318_b_(p_225574_1_);
   }
}