package net.minecraft.block;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerWorld;

public class MushroomBlock extends BushBlock implements IGrowable {
   protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

   public MushroomBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      if (p_225542_4_.nextInt(25) == 0) {
         int i = 5;
         int j = 4;

         for(BlockPos blockpos : BlockPos.getAllInBoxMutable(p_225542_3_.add(-4, -1, -4), p_225542_3_.add(4, 1, 4))) {
            if (p_225542_2_.getBlockState(blockpos).isIn(this)) {
               --i;
               if (i <= 0) {
                  return;
               }
            }
         }

         BlockPos blockpos1 = p_225542_3_.add(p_225542_4_.nextInt(3) - 1, p_225542_4_.nextInt(2) - p_225542_4_.nextInt(2), p_225542_4_.nextInt(3) - 1);

         for(int k = 0; k < 4; ++k) {
            if (p_225542_2_.isAirBlock(blockpos1) && p_225542_1_.isValidPosition(p_225542_2_, blockpos1)) {
               p_225542_3_ = blockpos1;
            }

            blockpos1 = p_225542_3_.add(p_225542_4_.nextInt(3) - 1, p_225542_4_.nextInt(2) - p_225542_4_.nextInt(2), p_225542_4_.nextInt(3) - 1);
         }

         if (p_225542_2_.isAirBlock(blockpos1) && p_225542_1_.isValidPosition(p_225542_2_, blockpos1)) {
            p_225542_2_.setBlockState(blockpos1, p_225542_1_, 2);
         }
      }

   }

   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.isOpaqueCube(worldIn, pos);
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.func_177977_b();
      BlockState blockstate = worldIn.getBlockState(blockpos);
      if (!blockstate.isIn(Blocks.MYCELIUM) && !blockstate.isIn(Blocks.PODZOL)) {
         return worldIn.func_226659_b_(pos, 0) < 13 && blockstate.canSustainPlant(worldIn, blockpos, net.minecraft.util.Direction.UP, this);
      } else {
         return true;
      }
   }

   public boolean func_226940_a_(ServerWorld p_226940_1_, BlockPos p_226940_2_, BlockState p_226940_3_, Random p_226940_4_) {
      p_226940_1_.removeBlock(p_226940_2_, false);
      ConfiguredFeature<BigMushroomFeatureConfig, ?> configuredfeature;
      if (this == Blocks.BROWN_MUSHROOM) {
         configuredfeature = Feature.HUGE_BROWN_MUSHROOM.func_225566_b_(DefaultBiomeFeatures.field_226768_ac_);
      } else {
         if (this != Blocks.RED_MUSHROOM) {
            p_226940_1_.setBlockState(p_226940_2_, p_226940_3_, 3);
            return false;
         }

         configuredfeature = Feature.HUGE_RED_MUSHROOM.func_225566_b_(DefaultBiomeFeatures.field_226767_ab_);
      }

      if (configuredfeature.func_236265_a_(p_226940_1_, p_226940_1_.func_241112_a_(), p_226940_1_.getChunkProvider().getChunkGenerator(), p_226940_4_, p_226940_2_)) {
         return true;
      } else {
         p_226940_1_.setBlockState(p_226940_2_, p_226940_3_, 3);
         return false;
      }
   }

   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return (double)rand.nextFloat() < 0.4D;
   }

   public void func_225535_a_(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
      this.func_226940_a_(p_225535_1_, p_225535_3_, p_225535_4_, p_225535_2_);
   }
}