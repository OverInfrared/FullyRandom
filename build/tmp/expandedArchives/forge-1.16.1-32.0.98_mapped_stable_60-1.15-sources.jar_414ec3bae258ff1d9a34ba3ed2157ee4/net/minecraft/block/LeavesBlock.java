package net.minecraft.block;

import java.util.Random;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LeavesBlock extends Block implements net.minecraftforge.common.IForgeShearable {
   public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE_1_7;
   public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

   public LeavesBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, Integer.valueOf(7)).with(PERSISTENT, Boolean.valueOf(false)));
   }

   public VoxelShape func_230335_e_(BlockState p_230335_1_, IBlockReader p_230335_2_, BlockPos p_230335_3_) {
      return VoxelShapes.empty();
   }

   public boolean ticksRandomly(BlockState state) {
      return state.get(DISTANCE) == 7 && !state.get(PERSISTENT);
   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      if (!p_225542_1_.get(PERSISTENT) && p_225542_1_.get(DISTANCE) == 7) {
         spawnDrops(p_225542_1_, p_225542_2_, p_225542_3_);
         p_225542_2_.removeBlock(p_225542_3_, false);
      }

   }

   public void func_225534_a_(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      p_225534_2_.setBlockState(p_225534_3_, updateDistance(p_225534_1_, p_225534_2_, p_225534_3_), 3);
   }

   public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return 1;
   }

   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      int i = getDistance(facingState) + 1;
      if (i != 1 || stateIn.get(DISTANCE) != i) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      return stateIn;
   }

   private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
      int i = 7;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Direction direction : Direction.values()) {
         blockpos$mutable.func_239622_a_(pos, direction);
         i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
         if (i == 1) {
            break;
         }
      }

      return state.with(DISTANCE, Integer.valueOf(i));
   }

   private static int getDistance(BlockState neighbor) {
      if (BlockTags.LOGS.func_230235_a_(neighbor.getBlock())) {
         return 0;
      } else {
         return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get(DISTANCE) : 7;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (worldIn.isRainingAt(pos.up())) {
         if (rand.nextInt(15) == 1) {
            BlockPos blockpos = pos.func_177977_b();
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (!blockstate.isSolid() || !blockstate.func_224755_d(worldIn, blockpos, Direction.UP)) {
               double d0 = (double)pos.getX() + rand.nextDouble();
               double d1 = (double)pos.getY() - 0.05D;
               double d2 = (double)pos.getZ() + rand.nextDouble();
               worldIn.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
         }
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(DISTANCE, PERSISTENT);
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return updateDistance(this.getDefaultState().with(PERSISTENT, Boolean.valueOf(true)), context.getWorld(), context.getPos());
   }
}