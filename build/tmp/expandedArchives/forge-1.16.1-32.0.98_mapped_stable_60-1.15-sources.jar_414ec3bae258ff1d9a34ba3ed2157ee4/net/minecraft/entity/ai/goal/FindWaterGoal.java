package net.minecraft.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FindWaterGoal extends Goal {
   private final CreatureEntity field_205152_a;

   public FindWaterGoal(CreatureEntity p_i48936_1_) {
      this.field_205152_a = p_i48936_1_;
   }

   public boolean shouldExecute() {
      return this.field_205152_a.func_233570_aj_() && !this.field_205152_a.world.getFluidState(this.field_205152_a.func_233580_cy_()).isTagged(FluidTags.WATER);
   }

   public void startExecuting() {
      BlockPos blockpos = null;

      for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(MathHelper.floor(this.field_205152_a.func_226277_ct_() - 2.0D), MathHelper.floor(this.field_205152_a.func_226278_cu_() - 2.0D), MathHelper.floor(this.field_205152_a.func_226281_cx_() - 2.0D), MathHelper.floor(this.field_205152_a.func_226277_ct_() + 2.0D), MathHelper.floor(this.field_205152_a.func_226278_cu_()), MathHelper.floor(this.field_205152_a.func_226281_cx_() + 2.0D))) {
         if (this.field_205152_a.world.getFluidState(blockpos1).isTagged(FluidTags.WATER)) {
            blockpos = blockpos1;
            break;
         }
      }

      if (blockpos != null) {
         this.field_205152_a.getMoveHelper().setMoveTo((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), 1.0D);
      }

   }
}