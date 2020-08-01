package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface IWorldWriter {
   boolean func_241211_a_(BlockPos p_241211_1_, BlockState p_241211_2_, int p_241211_3_, int p_241211_4_);

   default boolean setBlockState(BlockPos pos, BlockState newState, int flags) {
      return this.func_241211_a_(pos, newState, flags, 512);
   }

   boolean removeBlock(BlockPos pos, boolean isMoving);

   default boolean destroyBlock(BlockPos pos, boolean dropBlock) {
      return this.func_225521_a_(pos, dropBlock, (Entity)null);
   }

   default boolean func_225521_a_(BlockPos p_225521_1_, boolean p_225521_2_, @Nullable Entity p_225521_3_) {
      return this.func_241212_a_(p_225521_1_, p_225521_2_, p_225521_3_, 512);
   }

   boolean func_241212_a_(BlockPos p_241212_1_, boolean p_241212_2_, @Nullable Entity p_241212_3_, int p_241212_4_);

   default boolean addEntity(Entity entityIn) {
      return false;
   }
}