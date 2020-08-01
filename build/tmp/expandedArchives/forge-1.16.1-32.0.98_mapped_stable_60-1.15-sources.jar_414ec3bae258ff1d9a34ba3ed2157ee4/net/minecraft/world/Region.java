package net.minecraft.world;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunk;

public class Region implements IBlockReader, ICollisionReader {
   protected final int chunkX;
   protected final int chunkZ;
   protected final IChunk[][] chunks;
   protected boolean empty;
   protected final World world;

   public Region(World worldIn, BlockPos p_i50004_2_, BlockPos p_i50004_3_) {
      this.world = worldIn;
      this.chunkX = p_i50004_2_.getX() >> 4;
      this.chunkZ = p_i50004_2_.getZ() >> 4;
      int i = p_i50004_3_.getX() >> 4;
      int j = p_i50004_3_.getZ() >> 4;
      this.chunks = new IChunk[i - this.chunkX + 1][j - this.chunkZ + 1];
      AbstractChunkProvider abstractchunkprovider = worldIn.getChunkProvider();
      this.empty = true;

      for(int k = this.chunkX; k <= i; ++k) {
         for(int l = this.chunkZ; l <= j; ++l) {
            this.chunks[k - this.chunkX][l - this.chunkZ] = abstractchunkprovider.func_225313_a(k, l);
         }
      }

      for(int i1 = p_i50004_2_.getX() >> 4; i1 <= p_i50004_3_.getX() >> 4; ++i1) {
         for(int j1 = p_i50004_2_.getZ() >> 4; j1 <= p_i50004_3_.getZ() >> 4; ++j1) {
            IChunk ichunk = this.chunks[i1 - this.chunkX][j1 - this.chunkZ];
            if (ichunk != null && !ichunk.isEmptyBetween(p_i50004_2_.getY(), p_i50004_3_.getY())) {
               this.empty = false;
               return;
            }
         }
      }

   }

   private IChunk func_226703_d_(BlockPos p_226703_1_) {
      return this.func_226702_a_(p_226703_1_.getX() >> 4, p_226703_1_.getZ() >> 4);
   }

   private IChunk func_226702_a_(int p_226702_1_, int p_226702_2_) {
      int i = p_226702_1_ - this.chunkX;
      int j = p_226702_2_ - this.chunkZ;
      if (i >= 0 && i < this.chunks.length && j >= 0 && j < this.chunks[i].length) {
         IChunk ichunk = this.chunks[i][j];
         return (IChunk)(ichunk != null ? ichunk : new EmptyChunk(this.world, new ChunkPos(p_226702_1_, p_226702_2_)));
      } else {
         return new EmptyChunk(this.world, new ChunkPos(p_226702_1_, p_226702_2_));
      }
   }

   public WorldBorder getWorldBorder() {
      return this.world.getWorldBorder();
   }

   public IBlockReader func_225522_c_(int p_225522_1_, int p_225522_2_) {
      return this.func_226702_a_(p_225522_1_, p_225522_2_);
   }

   @Nullable
   public TileEntity getTileEntity(BlockPos pos) {
      IChunk ichunk = this.func_226703_d_(pos);
      return ichunk.getTileEntity(pos);
   }

   public BlockState getBlockState(BlockPos pos) {
      if (World.isOutsideBuildHeight(pos)) {
         return Blocks.AIR.getDefaultState();
      } else {
         IChunk ichunk = this.func_226703_d_(pos);
         return ichunk.getBlockState(pos);
      }
   }

   public Stream<VoxelShape> func_230318_c_(@Nullable Entity p_230318_1_, AxisAlignedBB p_230318_2_, Predicate<Entity> p_230318_3_) {
      return Stream.empty();
   }

   public Stream<VoxelShape> func_234867_d_(@Nullable Entity p_234867_1_, AxisAlignedBB p_234867_2_, Predicate<Entity> p_234867_3_) {
      return this.func_226666_b_(p_234867_1_, p_234867_2_);
   }

   public FluidState getFluidState(BlockPos pos) {
      if (World.isOutsideBuildHeight(pos)) {
         return Fluids.EMPTY.getDefaultState();
      } else {
         IChunk ichunk = this.func_226703_d_(pos);
         return ichunk.getFluidState(pos);
      }
   }
}