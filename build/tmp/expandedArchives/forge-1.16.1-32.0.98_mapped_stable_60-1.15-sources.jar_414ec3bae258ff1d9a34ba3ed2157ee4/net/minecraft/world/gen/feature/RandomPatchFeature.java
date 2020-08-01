package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class RandomPatchFeature extends Feature<BlockClusterFeatureConfig> {
   public RandomPatchFeature(Codec<BlockClusterFeatureConfig> p_i231979_1_) {
      super(p_i231979_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, BlockClusterFeatureConfig p_230362_6_) {
      BlockState blockstate = p_230362_6_.field_227289_a_.func_225574_a_(p_230362_4_, p_230362_5_);
      BlockPos blockpos;
      if (p_230362_6_.field_227298_k_) {
         blockpos = p_230362_1_.getHeight(Heightmap.Type.WORLD_SURFACE_WG, p_230362_5_);
      } else {
         blockpos = p_230362_5_;
      }

      int i = 0;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int j = 0; j < p_230362_6_.field_227293_f_; ++j) {
         blockpos$mutable.func_239621_a_(blockpos, p_230362_4_.nextInt(p_230362_6_.field_227294_g_ + 1) - p_230362_4_.nextInt(p_230362_6_.field_227294_g_ + 1), p_230362_4_.nextInt(p_230362_6_.field_227295_h_ + 1) - p_230362_4_.nextInt(p_230362_6_.field_227295_h_ + 1), p_230362_4_.nextInt(p_230362_6_.field_227296_i_ + 1) - p_230362_4_.nextInt(p_230362_6_.field_227296_i_ + 1));
         BlockPos blockpos1 = blockpos$mutable.func_177977_b();
         BlockState blockstate1 = p_230362_1_.getBlockState(blockpos1);
         if ((p_230362_1_.isAirBlock(blockpos$mutable) || p_230362_6_.field_227297_j_ && p_230362_1_.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && blockstate.isValidPosition(p_230362_1_, blockpos$mutable) && (p_230362_6_.field_227291_c_.isEmpty() || p_230362_6_.field_227291_c_.contains(blockstate1.getBlock())) && !p_230362_6_.field_227292_d_.contains(blockstate1) && (!p_230362_6_.field_227299_l_ || p_230362_1_.getFluidState(blockpos1.west()).isTagged(FluidTags.WATER) || p_230362_1_.getFluidState(blockpos1.east()).isTagged(FluidTags.WATER) || p_230362_1_.getFluidState(blockpos1.north()).isTagged(FluidTags.WATER) || p_230362_1_.getFluidState(blockpos1.south()).isTagged(FluidTags.WATER))) {
            p_230362_6_.field_227290_b_.func_225567_a_(p_230362_1_, blockpos$mutable, blockstate, p_230362_4_);
            ++i;
         }
      }

      return i > 0;
   }
}