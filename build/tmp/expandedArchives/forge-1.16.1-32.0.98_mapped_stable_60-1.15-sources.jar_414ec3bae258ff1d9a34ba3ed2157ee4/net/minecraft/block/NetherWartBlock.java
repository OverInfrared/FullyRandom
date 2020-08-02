package net.minecraft.block;

import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class NetherWartBlock extends BushBlock {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
   private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)};

   public NetherWartBlock(AbstractBlock.Properties builder) {
      super(builder);
      this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return SHAPES[state.get(AGE)];
   }

   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.isIn(Blocks.SOUL_SAND);
   }

   public boolean ticksRandomly(BlockState state) {
      return state.get(AGE) < 3;
   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      int i = p_225542_1_.get(AGE);
      if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_225542_2_, p_225542_3_, p_225542_1_, p_225542_4_.nextInt(10) == 0)) {
         p_225542_1_ = p_225542_1_.with(AGE, Integer.valueOf(i + 1));
         p_225542_2_.setBlockState(p_225542_3_, p_225542_1_, 2);
         net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_225542_2_, p_225542_3_, p_225542_1_);
      }

   }

   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(Items.NETHER_WART);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }
}