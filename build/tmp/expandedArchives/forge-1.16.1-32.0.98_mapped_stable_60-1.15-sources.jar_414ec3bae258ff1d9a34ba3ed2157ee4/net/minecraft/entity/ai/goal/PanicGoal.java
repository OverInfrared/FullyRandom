package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class PanicGoal extends Goal {
   protected final CreatureEntity creature;
   protected final double speed;
   protected double randPosX;
   protected double randPosY;
   protected double randPosZ;
   protected boolean field_234043_f_;

   public PanicGoal(CreatureEntity creature, double speedIn) {
      this.creature = creature;
      this.speed = speedIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean shouldExecute() {
      if (this.creature.getRevengeTarget() == null && !this.creature.isBurning()) {
         return false;
      } else {
         if (this.creature.isBurning()) {
            BlockPos blockpos = this.getRandPos(this.creature.world, this.creature, 5, 4);
            if (blockpos != null) {
               this.randPosX = (double)blockpos.getX();
               this.randPosY = (double)blockpos.getY();
               this.randPosZ = (double)blockpos.getZ();
               return true;
            }
         }

         return this.findRandomPosition();
      }
   }

   protected boolean findRandomPosition() {
      Vector3d vector3d = RandomPositionGenerator.findRandomTarget(this.creature, 5, 4);
      if (vector3d == null) {
         return false;
      } else {
         this.randPosX = vector3d.x;
         this.randPosY = vector3d.y;
         this.randPosZ = vector3d.z;
         return true;
      }
   }

   public boolean func_234044_h_() {
      return this.field_234043_f_;
   }

   public void startExecuting() {
      this.creature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
      this.field_234043_f_ = true;
   }

   public void resetTask() {
      this.field_234043_f_ = false;
   }

   public boolean shouldContinueExecuting() {
      return !this.creature.getNavigator().noPath();
   }

   @Nullable
   protected BlockPos getRandPos(IBlockReader worldIn, Entity entityIn, int horizontalRange, int verticalRange) {
      BlockPos blockpos = entityIn.func_233580_cy_();
      int i = blockpos.getX();
      int j = blockpos.getY();
      int k = blockpos.getZ();
      float f = (float)(horizontalRange * horizontalRange * verticalRange * 2);
      BlockPos blockpos1 = null;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int l = i - horizontalRange; l <= i + horizontalRange; ++l) {
         for(int i1 = j - verticalRange; i1 <= j + verticalRange; ++i1) {
            for(int j1 = k - horizontalRange; j1 <= k + horizontalRange; ++j1) {
               blockpos$mutable.func_181079_c(l, i1, j1);
               if (worldIn.getFluidState(blockpos$mutable).isTagged(FluidTags.WATER)) {
                  float f1 = (float)((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));
                  if (f1 < f) {
                     f = f1;
                     blockpos1 = new BlockPos(blockpos$mutable);
                  }
               }
            }
         }
      }

      return blockpos1;
   }
}