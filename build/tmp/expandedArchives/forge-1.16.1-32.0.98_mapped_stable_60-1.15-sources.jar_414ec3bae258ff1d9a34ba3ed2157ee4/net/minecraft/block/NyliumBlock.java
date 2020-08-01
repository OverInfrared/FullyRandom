package net.minecraft.block;

import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.gen.feature.TwistingVineFeature;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class NyliumBlock extends Block implements IGrowable {
   public NyliumBlock(AbstractBlock.Properties p_i241184_1_) {
      super(p_i241184_1_);
   }

   private static boolean func_235516_b_(BlockState p_235516_0_, IWorldReader p_235516_1_, BlockPos p_235516_2_) {
      BlockPos blockpos = p_235516_2_.up();
      BlockState blockstate = p_235516_1_.getBlockState(blockpos);
      int i = LightEngine.func_215613_a(p_235516_1_, p_235516_0_, p_235516_2_, blockstate, blockpos, Direction.UP, blockstate.getOpacity(p_235516_1_, blockpos));
      return i < p_235516_1_.getMaxLightLevel();
   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      if (!func_235516_b_(p_225542_1_, p_225542_2_, p_225542_3_)) {
         p_225542_2_.setBlockState(p_225542_3_, Blocks.NETHERRACK.getDefaultState());
      }

   }

   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return worldIn.getBlockState(pos.up()).isAir();
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
      BlockState blockstate = p_225535_1_.getBlockState(p_225535_3_);
      BlockPos blockpos = p_225535_3_.up();
      if (blockstate.isIn(Blocks.field_235381_mu_)) {
         NetherVegetationFeature.func_236325_a_(p_225535_1_, p_225535_2_, blockpos, DefaultBiomeFeatures.field_235145_aY_, 3, 1);
      } else if (blockstate.isIn(Blocks.field_235372_ml_)) {
         NetherVegetationFeature.func_236325_a_(p_225535_1_, p_225535_2_, blockpos, DefaultBiomeFeatures.field_235146_aZ_, 3, 1);
         NetherVegetationFeature.func_236325_a_(p_225535_1_, p_225535_2_, blockpos, DefaultBiomeFeatures.field_235151_ba_, 3, 1);
         if (p_225535_2_.nextInt(8) == 0) {
            TwistingVineFeature.func_236423_a_(p_225535_1_, p_225535_2_, blockpos, 3, 1, 2);
         }
      }

   }
}