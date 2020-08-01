package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TurtleEggBlock extends Block {
   private static final VoxelShape ONE_EGG_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
   private static final VoxelShape MULTI_EGG_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
   public static final IntegerProperty HATCH = BlockStateProperties.HATCH_0_2;
   public static final IntegerProperty EGGS = BlockStateProperties.EGGS_1_4;

   public TurtleEggBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(HATCH, Integer.valueOf(0)).with(EGGS, Integer.valueOf(1)));
   }

   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
      this.tryTrample(worldIn, pos, entityIn, 100);
      super.onEntityWalk(worldIn, pos, entityIn);
   }

   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
      if (!(entityIn instanceof ZombieEntity)) {
         this.tryTrample(worldIn, pos, entityIn, 3);
      }

      super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
   }

   private void tryTrample(World p_203167_1_, BlockPos p_203167_2_, Entity p_203167_3_, int p_203167_4_) {
      if (this.canTrample(p_203167_1_, p_203167_3_)) {
         if (!p_203167_1_.isRemote && p_203167_1_.rand.nextInt(p_203167_4_) == 0) {
            BlockState blockstate = p_203167_1_.getBlockState(p_203167_2_);
            if (blockstate.isIn(Blocks.TURTLE_EGG)) {
               this.removeOneEgg(p_203167_1_, p_203167_2_, blockstate);
            }
         }

      }
   }

   private void removeOneEgg(World p_203166_1_, BlockPos p_203166_2_, BlockState p_203166_3_) {
      p_203166_1_.playSound((PlayerEntity)null, p_203166_2_, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + p_203166_1_.rand.nextFloat() * 0.2F);
      int i = p_203166_3_.get(EGGS);
      if (i <= 1) {
         p_203166_1_.destroyBlock(p_203166_2_, false);
      } else {
         p_203166_1_.setBlockState(p_203166_2_, p_203166_3_.with(EGGS, Integer.valueOf(i - 1)), 2);
         p_203166_1_.playEvent(2001, p_203166_2_, Block.getStateId(p_203166_3_));
      }

   }

   public void func_225542_b_(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
      if (this.canGrow(p_225542_2_) && hasProperHabitat(p_225542_2_, p_225542_3_)) {
         int i = p_225542_1_.get(HATCH);
         if (i < 2) {
            p_225542_2_.playSound((PlayerEntity)null, p_225542_3_, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + p_225542_4_.nextFloat() * 0.2F);
            p_225542_2_.setBlockState(p_225542_3_, p_225542_1_.with(HATCH, Integer.valueOf(i + 1)), 2);
         } else {
            p_225542_2_.playSound((PlayerEntity)null, p_225542_3_, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + p_225542_4_.nextFloat() * 0.2F);
            p_225542_2_.removeBlock(p_225542_3_, false);

            for(int j = 0; j < p_225542_1_.get(EGGS); ++j) {
               p_225542_2_.playEvent(2001, p_225542_3_, Block.getStateId(p_225542_1_));
               TurtleEntity turtleentity = EntityType.TURTLE.create(p_225542_2_);
               turtleentity.setGrowingAge(-24000);
               turtleentity.setHome(p_225542_3_);
               turtleentity.setLocationAndAngles((double)p_225542_3_.getX() + 0.3D + (double)j * 0.2D, (double)p_225542_3_.getY(), (double)p_225542_3_.getZ() + 0.3D, 0.0F, 0.0F);
               p_225542_2_.addEntity(turtleentity);
            }
         }
      }

   }

   public static boolean hasProperHabitat(IBlockReader p_203168_0_, BlockPos p_203168_1_) {
      return func_241473_b_(p_203168_0_, p_203168_1_.func_177977_b());
   }

   public static boolean func_241473_b_(IBlockReader p_241473_0_, BlockPos p_241473_1_) {
      return p_241473_0_.getBlockState(p_241473_1_).func_235714_a_(BlockTags.SAND);
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (hasProperHabitat(worldIn, pos) && !worldIn.isRemote) {
         worldIn.playEvent(2005, pos, 0);
      }

   }

   private boolean canGrow(World p_203169_1_) {
      float f = p_203169_1_.getCelestialAngle(1.0F);
      if ((double)f < 0.69D && (double)f > 0.65D) {
         return true;
      } else {
         return p_203169_1_.rand.nextInt(500) == 0;
      }
   }

   public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
      super.harvestBlock(worldIn, player, pos, state, te, stack);
      this.removeOneEgg(worldIn, pos, state);
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return useContext.getItem().getItem() == this.asItem() && state.get(EGGS) < 4 ? true : super.isReplaceable(state, useContext);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      BlockState blockstate = context.getWorld().getBlockState(context.getPos());
      return blockstate.isIn(this) ? blockstate.with(EGGS, Integer.valueOf(Math.min(4, blockstate.get(EGGS) + 1))) : super.getStateForPlacement(context);
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return state.get(EGGS) > 1 ? MULTI_EGG_SHAPE : ONE_EGG_SHAPE;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(HATCH, EGGS);
   }

   private boolean canTrample(World worldIn, Entity trampler) {
      if (!(trampler instanceof TurtleEntity) && !(trampler instanceof BatEntity)) {
         if (!(trampler instanceof LivingEntity)) {
            return false;
         } else {
            return trampler instanceof LivingEntity && !(trampler instanceof PlayerEntity) ? net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler) : true;
         }
      } else {
         return false;
      }
   }
}