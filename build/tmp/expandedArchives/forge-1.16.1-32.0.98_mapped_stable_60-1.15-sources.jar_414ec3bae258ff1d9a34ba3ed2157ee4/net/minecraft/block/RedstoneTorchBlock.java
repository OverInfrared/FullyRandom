package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RedstoneTorchBlock extends TorchBlock {
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   private static final Map<IBlockReader, List<RedstoneTorchBlock.Toggle>> BURNED_TORCHES = new WeakHashMap<>();

   public RedstoneTorchBlock(AbstractBlock.Properties properties) {
      super(properties, RedstoneParticleData.REDSTONE_DUST);
      this.setDefaultState(this.stateContainer.getBaseState().with(LIT, Boolean.valueOf(true)));
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      for(Direction direction : Direction.values()) {
         worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
      }

   }

   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!isMoving) {
         for(Direction direction : Direction.values()) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
         }

      }
   }

   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return blockState.get(LIT) && Direction.UP != side ? 15 : 0;
   }

   protected boolean shouldBeOff(World worldIn, BlockPos pos, BlockState state) {
      return worldIn.isSidePowered(pos.func_177977_b(), Direction.DOWN);
   }

   public void func_225534_a_(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      boolean flag = this.shouldBeOff(p_225534_2_, p_225534_3_, p_225534_1_);
      List<RedstoneTorchBlock.Toggle> list = BURNED_TORCHES.get(p_225534_2_);

      while(list != null && !list.isEmpty() && p_225534_2_.getGameTime() - (list.get(0)).time > 60L) {
         list.remove(0);
      }

      if (p_225534_1_.get(LIT)) {
         if (flag) {
            p_225534_2_.setBlockState(p_225534_3_, p_225534_1_.with(LIT, Boolean.valueOf(false)), 3);
            if (isBurnedOut(p_225534_2_, p_225534_3_, true)) {
               p_225534_2_.playEvent(1502, p_225534_3_, 0);
               p_225534_2_.getPendingBlockTicks().scheduleTick(p_225534_3_, p_225534_2_.getBlockState(p_225534_3_).getBlock(), 160);
            }
         }
      } else if (!flag && !isBurnedOut(p_225534_2_, p_225534_3_, false)) {
         p_225534_2_.setBlockState(p_225534_3_, p_225534_1_.with(LIT, Boolean.valueOf(true)), 3);
      }

   }

   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (state.get(LIT) == this.shouldBeOff(worldIn, pos, state) && !worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
         worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
      }

   }

   public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return side == Direction.DOWN ? blockState.getWeakPower(blockAccess, pos, side) : 0;
   }

   public boolean canProvidePower(BlockState state) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (stateIn.get(LIT)) {
         double d0 = (double)pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
         double d1 = (double)pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
         double d2 = (double)pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
         worldIn.addParticle(this.field_235607_e_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(LIT);
   }

   private static boolean isBurnedOut(World p_176598_0_, BlockPos worldIn, boolean pos) {
      List<RedstoneTorchBlock.Toggle> list = BURNED_TORCHES.computeIfAbsent(p_176598_0_, (p_220288_0_) -> {
         return Lists.newArrayList();
      });
      if (pos) {
         list.add(new RedstoneTorchBlock.Toggle(worldIn.toImmutable(), p_176598_0_.getGameTime()));
      }

      int i = 0;

      for(int j = 0; j < list.size(); ++j) {
         RedstoneTorchBlock.Toggle redstonetorchblock$toggle = list.get(j);
         if (redstonetorchblock$toggle.pos.equals(worldIn)) {
            ++i;
            if (i >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class Toggle {
      private final BlockPos pos;
      private final long time;

      public Toggle(BlockPos pos, long time) {
         this.pos = pos;
         this.time = time;
      }
   }
}