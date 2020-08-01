package net.minecraft.block;

import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class VineBlock extends Block implements net.minecraftforge.common.IForgeShearable {
   public static final BooleanProperty UP = SixWayBlock.UP;
   public static final BooleanProperty NORTH = SixWayBlock.NORTH;
   public static final BooleanProperty EAST = SixWayBlock.EAST;
   public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
   public static final BooleanProperty WEST = SixWayBlock.WEST;
   public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((p_199782_0_) -> {
      return p_199782_0_.getKey() != Direction.DOWN;
   }).collect(Util.toMapCollector());
   protected static final VoxelShape UP_AABB = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
   protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
   protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

   public VineBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(UP, Boolean.valueOf(false)).with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)));
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      VoxelShape voxelshape = VoxelShapes.empty();
      if (state.get(UP)) {
         voxelshape = VoxelShapes.or(voxelshape, UP_AABB);
      }

      if (state.get(NORTH)) {
         voxelshape = VoxelShapes.or(voxelshape, NORTH_AABB);
      }

      if (state.get(EAST)) {
         voxelshape = VoxelShapes.or(voxelshape, EAST_AABB);
      }

      if (state.get(SOUTH)) {
         voxelshape = VoxelShapes.or(voxelshape, SOUTH_AABB);
      }

      if (state.get(WEST)) {
         voxelshape = VoxelShapes.or(voxelshape, WEST_AABB);
      }

      return voxelshape;
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return this.func_196543_i(this.func_196545_h(state, worldIn, pos));
   }

   private boolean func_196543_i(BlockState p_196543_1_) {
      return this.func_208496_w(p_196543_1_) > 0;
   }

   private int func_208496_w(BlockState p_208496_1_) {
      int i = 0;

      for(BooleanProperty booleanproperty : FACING_TO_PROPERTY_MAP.values()) {
         if (p_208496_1_.get(booleanproperty)) {
            ++i;
         }
      }

      return i;
   }

   private boolean func_196541_a(IBlockReader p_196541_1_, BlockPos p_196541_2_, Direction p_196541_3_) {
      if (p_196541_3_ == Direction.DOWN) {
         return false;
      } else {
         BlockPos blockpos = p_196541_2_.offset(p_196541_3_);
         if (canAttachTo(p_196541_1_, blockpos, p_196541_3_)) {
            return true;
         } else if (p_196541_3_.getAxis() == Direction.Axis.Y) {
            return false;
         } else {
            BooleanProperty booleanproperty = FACING_TO_PROPERTY_MAP.get(p_196541_3_);
            BlockState blockstate = p_196541_1_.getBlockState(p_196541_2_.up());
            return blockstate.isIn(this) && blockstate.get(booleanproperty);
         }
      }
   }

   public static boolean canAttachTo(IBlockReader p_196542_0_, BlockPos worldIn, Direction neighborPos) {
      BlockState blockstate = p_196542_0_.getBlockState(worldIn);
      return Block.doesSideFillSquare(blockstate.getCollisionShape(p_196542_0_, worldIn), neighborPos.getOpposite());
   }

   private BlockState func_196545_h(BlockState p_196545_1_, IBlockReader p_196545_2_, BlockPos p_196545_3_) {
      BlockPos blockpos = p_196545_3_.up();
      if (p_196545_1_.get(UP)) {
         p_196545_1_ = p_196545_1_.with(UP, Boolean.valueOf(canAttachTo(p_196545_2_, blockpos, Direction.DOWN)));
      }

      BlockState blockstate = null;

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BooleanProperty booleanproperty = getPropertyFor(direction);
         if (p_196545_1_.get(booleanproperty)) {
            boolean flag = this.func_196541_a(p_196545_2_, p_196545_3_, direction);
            if (!flag) {
               if (blockstate == null) {
                  blockstate = p_196545_2_.getBlockState(blockpos);
               }

               flag = blockstate.isIn(this) && blockstate.get(booleanproperty);
            }

            p_196545_1_ = p_196545_1_.with(booleanproperty, Boolean.valueOf(flag));
         }
      }

      return p_196545_1_;
   }

   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == Direction.DOWN) {
         return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      } else {
         BlockState blockstate = this.func_196545_h(stateIn, worldIn, currentPos);
         return !this.func_196543_i(blockstate) ? Blocks.AIR.getDefaultState() : blockstate;
      }
   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      if (p_225542_2_.rand.nextInt(4) == 0 && p_225542_2_.isAreaLoaded(p_225542_3_, 4)) { // Forge: check area to prevent loading unloaded chunks
         Direction direction = Direction.func_239631_a_(p_225542_4_);
         BlockPos blockpos = p_225542_3_.up();
         if (direction.getAxis().isHorizontal() && !p_225542_1_.get(getPropertyFor(direction))) {
            if (this.func_196539_a(p_225542_2_, p_225542_3_)) {
               BlockPos blockpos4 = p_225542_3_.offset(direction);
               BlockState blockstate4 = p_225542_2_.getBlockState(blockpos4);
               if (blockstate4.isAir(p_225542_2_, blockpos4)) {
                  Direction direction3 = direction.rotateY();
                  Direction direction4 = direction.rotateYCCW();
                  boolean flag = p_225542_1_.get(getPropertyFor(direction3));
                  boolean flag1 = p_225542_1_.get(getPropertyFor(direction4));
                  BlockPos blockpos2 = blockpos4.offset(direction3);
                  BlockPos blockpos3 = blockpos4.offset(direction4);
                  if (flag && canAttachTo(p_225542_2_, blockpos2, direction3)) {
                     p_225542_2_.setBlockState(blockpos4, this.getDefaultState().with(getPropertyFor(direction3), Boolean.valueOf(true)), 2);
                  } else if (flag1 && canAttachTo(p_225542_2_, blockpos3, direction4)) {
                     p_225542_2_.setBlockState(blockpos4, this.getDefaultState().with(getPropertyFor(direction4), Boolean.valueOf(true)), 2);
                  } else {
                     Direction direction1 = direction.getOpposite();
                     if (flag && p_225542_2_.isAirBlock(blockpos2) && canAttachTo(p_225542_2_, p_225542_3_.offset(direction3), direction1)) {
                        p_225542_2_.setBlockState(blockpos2, this.getDefaultState().with(getPropertyFor(direction1), Boolean.valueOf(true)), 2);
                     } else if (flag1 && p_225542_2_.isAirBlock(blockpos3) && canAttachTo(p_225542_2_, p_225542_3_.offset(direction4), direction1)) {
                        p_225542_2_.setBlockState(blockpos3, this.getDefaultState().with(getPropertyFor(direction1), Boolean.valueOf(true)), 2);
                     } else if ((double)p_225542_2_.rand.nextFloat() < 0.05D && canAttachTo(p_225542_2_, blockpos4.up(), Direction.UP)) {
                        p_225542_2_.setBlockState(blockpos4, this.getDefaultState().with(UP, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (canAttachTo(p_225542_2_, blockpos4, direction)) {
                  p_225542_2_.setBlockState(p_225542_3_, p_225542_1_.with(getPropertyFor(direction), Boolean.valueOf(true)), 2);
               }

            }
         } else {
            if (direction == Direction.UP && p_225542_3_.getY() < 255) {
               if (this.func_196541_a(p_225542_2_, p_225542_3_, direction)) {
                  p_225542_2_.setBlockState(p_225542_3_, p_225542_1_.with(UP, Boolean.valueOf(true)), 2);
                  return;
               }

               if (p_225542_2_.isAirBlock(blockpos)) {
                  if (!this.func_196539_a(p_225542_2_, p_225542_3_)) {
                     return;
                  }

                  BlockState blockstate3 = p_225542_1_;

                  for(Direction direction2 : Direction.Plane.HORIZONTAL) {
                     if (p_225542_4_.nextBoolean() || !canAttachTo(p_225542_2_, blockpos.offset(direction2), Direction.UP)) {
                        blockstate3 = blockstate3.with(getPropertyFor(direction2), Boolean.valueOf(false));
                     }
                  }

                  if (this.func_196540_x(blockstate3)) {
                     p_225542_2_.setBlockState(blockpos, blockstate3, 2);
                  }

                  return;
               }
            }

            if (p_225542_3_.getY() > 0) {
               BlockPos blockpos1 = p_225542_3_.func_177977_b();
               BlockState blockstate = p_225542_2_.getBlockState(blockpos1);
               if (blockstate.isAir(p_225542_2_, blockpos) || blockstate.isIn(this)) {
                  BlockState blockstate1 = blockstate.isAir() ? this.getDefaultState() : blockstate;
                  BlockState blockstate2 = this.func_196544_a(p_225542_1_, blockstate1, p_225542_4_);
                  if (blockstate1 != blockstate2 && this.func_196540_x(blockstate2)) {
                     p_225542_2_.setBlockState(blockpos1, blockstate2, 2);
                  }
               }
            }

         }
      }
   }

   private BlockState func_196544_a(BlockState p_196544_1_, BlockState p_196544_2_, Random p_196544_3_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (p_196544_3_.nextBoolean()) {
            BooleanProperty booleanproperty = getPropertyFor(direction);
            if (p_196544_1_.get(booleanproperty)) {
               p_196544_2_ = p_196544_2_.with(booleanproperty, Boolean.valueOf(true));
            }
         }
      }

      return p_196544_2_;
   }

   private boolean func_196540_x(BlockState p_196540_1_) {
      return p_196540_1_.get(NORTH) || p_196540_1_.get(EAST) || p_196540_1_.get(SOUTH) || p_196540_1_.get(WEST);
   }

   private boolean func_196539_a(IBlockReader p_196539_1_, BlockPos p_196539_2_) {
      int i = 4;
      Iterable<BlockPos> iterable = BlockPos.getAllInBoxMutable(p_196539_2_.getX() - 4, p_196539_2_.getY() - 1, p_196539_2_.getZ() - 4, p_196539_2_.getX() + 4, p_196539_2_.getY() + 1, p_196539_2_.getZ() + 4);
      int j = 5;

      for(BlockPos blockpos : iterable) {
         if (p_196539_1_.getBlockState(blockpos).isIn(this)) {
            --j;
            if (j <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      BlockState blockstate = useContext.getWorld().getBlockState(useContext.getPos());
      if (blockstate.isIn(this)) {
         return this.func_208496_w(blockstate) < FACING_TO_PROPERTY_MAP.size();
      } else {
         return super.isReplaceable(state, useContext);
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      BlockState blockstate = context.getWorld().getBlockState(context.getPos());
      boolean flag = blockstate.isIn(this);
      BlockState blockstate1 = flag ? blockstate : this.getDefaultState();

      for(Direction direction : context.getNearestLookingDirections()) {
         if (direction != Direction.DOWN) {
            BooleanProperty booleanproperty = getPropertyFor(direction);
            boolean flag1 = flag && blockstate.get(booleanproperty);
            if (!flag1 && this.func_196541_a(context.getWorld(), context.getPos(), direction)) {
               return blockstate1.with(booleanproperty, Boolean.valueOf(true));
            }
         }
      }

      return flag ? blockstate1 : null;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(UP, NORTH, EAST, SOUTH, WEST);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      switch(rot) {
      case CLOCKWISE_180:
         return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
      case COUNTERCLOCKWISE_90:
         return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
      case CLOCKWISE_90:
         return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
      default:
         return state;
      }
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      switch(mirrorIn) {
      case LEFT_RIGHT:
         return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
      case FRONT_BACK:
         return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
      default:
         return super.mirror(state, mirrorIn);
      }
   }

   public static BooleanProperty getPropertyFor(Direction side) {
      return FACING_TO_PROPERTY_MAP.get(side);
   }

   @Override
   public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, net.minecraft.entity.LivingEntity entity) { return true; }
}