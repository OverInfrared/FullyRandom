package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class SitGoal extends Goal {
   private final TameableEntity tameable;

   public SitGoal(TameableEntity entityIn) {
      this.tameable = entityIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   public boolean shouldContinueExecuting() {
      return this.tameable.func_233685_eM_();
   }

   public boolean shouldExecute() {
      if (!this.tameable.isTamed()) {
         return false;
      } else if (this.tameable.isInWaterOrBubbleColumn()) {
         return false;
      } else if (!this.tameable.func_233570_aj_()) {
         return false;
      } else {
         LivingEntity livingentity = this.tameable.getOwner();
         if (livingentity == null) {
            return true;
         } else {
            return this.tameable.getDistanceSq(livingentity) < 144.0D && livingentity.getRevengeTarget() != null ? false : this.tameable.func_233685_eM_();
         }
      }
   }

   public void startExecuting() {
      this.tameable.getNavigator().clearPath();
      this.tameable.func_233686_v_(true);
   }

   public void resetTask() {
      this.tameable.func_233686_v_(false);
   }
}