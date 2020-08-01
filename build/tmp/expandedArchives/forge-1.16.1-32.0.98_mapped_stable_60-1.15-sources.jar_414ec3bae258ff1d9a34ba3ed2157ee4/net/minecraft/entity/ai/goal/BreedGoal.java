package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class BreedGoal extends Goal {
   private static final EntityPredicate field_220689_d = (new EntityPredicate()).setDistance(8.0D).allowInvulnerable().allowFriendlyFire().setLineOfSiteRequired();
   protected final AnimalEntity animal;
   private final Class<? extends AnimalEntity> mateClass;
   protected final World world;
   protected AnimalEntity field_75391_e;
   private int spawnBabyDelay;
   private final double moveSpeed;

   public BreedGoal(AnimalEntity animal, double speedIn) {
      this(animal, speedIn, animal.getClass());
   }

   public BreedGoal(AnimalEntity p_i47306_1_, double p_i47306_2_, Class<? extends AnimalEntity> p_i47306_4_) {
      this.animal = p_i47306_1_;
      this.world = p_i47306_1_.world;
      this.mateClass = p_i47306_4_;
      this.moveSpeed = p_i47306_2_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean shouldExecute() {
      if (!this.animal.isInLove()) {
         return false;
      } else {
         this.field_75391_e = this.getNearbyMate();
         return this.field_75391_e != null;
      }
   }

   public boolean shouldContinueExecuting() {
      return this.field_75391_e.isAlive() && this.field_75391_e.isInLove() && this.spawnBabyDelay < 60;
   }

   public void resetTask() {
      this.field_75391_e = null;
      this.spawnBabyDelay = 0;
   }

   public void tick() {
      this.animal.getLookController().setLookPositionWithEntity(this.field_75391_e, 10.0F, (float)this.animal.getVerticalFaceSpeed());
      this.animal.getNavigator().tryMoveToEntityLiving(this.field_75391_e, this.moveSpeed);
      ++this.spawnBabyDelay;
      if (this.spawnBabyDelay >= 60 && this.animal.getDistanceSq(this.field_75391_e) < 9.0D) {
         this.spawnBaby();
      }

   }

   @Nullable
   private AnimalEntity getNearbyMate() {
      List<AnimalEntity> list = this.world.getTargettableEntitiesWithinAABB(this.mateClass, field_220689_d, this.animal, this.animal.getBoundingBox().grow(8.0D));
      double d0 = Double.MAX_VALUE;
      AnimalEntity animalentity = null;

      for(AnimalEntity animalentity1 : list) {
         if (this.animal.canMateWith(animalentity1) && this.animal.getDistanceSq(animalentity1) < d0) {
            animalentity = animalentity1;
            d0 = this.animal.getDistanceSq(animalentity1);
         }
      }

      return animalentity;
   }

   protected void spawnBaby() {
      this.animal.func_234177_a_(this.world, this.field_75391_e);
   }
}