package net.minecraft.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.server.ServerWorld;

public class FungusBlock extends BushBlock implements IGrowable {
   protected static final VoxelShape field_235496_a_ = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
   private final Supplier<ConfiguredFeature<HugeFungusConfig, ?>> field_235497_b_;

   public FungusBlock(AbstractBlock.Properties p_i241177_1_, Supplier<ConfiguredFeature<HugeFungusConfig, ?>> p_i241177_2_) {
      super(p_i241177_1_);
      this.field_235497_b_ = p_i241177_2_;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_235496_a_;
   }

   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.func_235714_a_(BlockTags.field_232873_an_) || state.isIn(Blocks.field_235336_cN_) || super.isValidGround(state, worldIn, pos);
   }

   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      Block block = ((HugeFungusConfig)(this.field_235497_b_.get()).config).field_236303_f_.getBlock();
      Block block1 = worldIn.getBlockState(pos.func_177977_b()).getBlock();
      return block1 == block;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return (double)rand.nextFloat() < 0.4D;
   }

   public void func_225535_a_(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
      this.field_235497_b_.get().func_236265_a_(p_225535_1_, p_225535_1_.func_241112_a_(), p_225535_1_.getChunkProvider().getChunkGenerator(), p_225535_2_, p_225535_3_);
   }
}