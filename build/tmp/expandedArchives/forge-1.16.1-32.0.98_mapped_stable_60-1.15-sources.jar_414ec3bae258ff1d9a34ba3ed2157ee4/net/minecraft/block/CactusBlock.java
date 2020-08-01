package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CactusBlock extends Block implements net.minecraftforge.common.IPlantable {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
   protected static final VoxelShape field_196400_b = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
   protected static final VoxelShape field_196401_c = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public CactusBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
   }

   public void func_225534_a_(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      if (!p_225534_2_.isAreaLoaded(p_225534_3_, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
      if (!p_225534_1_.isValidPosition(p_225534_2_, p_225534_3_)) {
         p_225534_2_.destroyBlock(p_225534_3_, true);
      }

   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      BlockPos blockpos = p_225542_3_.up();
      if (p_225542_2_.isAirBlock(blockpos)) {
         int i;
         for(i = 1; p_225542_2_.getBlockState(p_225542_3_.func_177979_c(i)).isIn(this); ++i) {
         }

         if (i < 3) {
            int j = p_225542_1_.get(AGE);
            if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_225542_2_, blockpos, p_225542_1_, true)) {
            if (j == 15) {
               p_225542_2_.setBlockState(blockpos, this.getDefaultState());
               BlockState blockstate = p_225542_1_.with(AGE, Integer.valueOf(0));
               p_225542_2_.setBlockState(p_225542_3_, blockstate, 4);
               blockstate.neighborChanged(p_225542_2_, blockpos, this, p_225542_3_, false);
            } else {
               p_225542_2_.setBlockState(p_225542_3_, p_225542_1_.with(AGE, Integer.valueOf(j + 1)), 4);
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_225542_2_, p_225542_3_, p_225542_1_);
            }
         }
      }
   }

   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_196400_b;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_196401_c;
   }

   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (!stateIn.isValidPosition(worldIn, currentPos)) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
         Material material = blockstate.getMaterial();
         if (material.isSolid() || worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA)) {
            return false;
         }
      }

      BlockState blockstate1 = worldIn.getBlockState(pos.func_177977_b());
      return blockstate1.canSustainPlant(worldIn, pos, Direction.UP, this) && !worldIn.getBlockState(pos.up()).getMaterial().isLiquid();
   }

   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
      entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   @Override
   public net.minecraftforge.common.PlantType getPlantType(IBlockReader world, BlockPos pos) {
      return net.minecraftforge.common.PlantType.DESERT;
   }

   @Override
   public BlockState getPlant(IBlockReader world, BlockPos pos) {
      return getDefaultState();
   }
}