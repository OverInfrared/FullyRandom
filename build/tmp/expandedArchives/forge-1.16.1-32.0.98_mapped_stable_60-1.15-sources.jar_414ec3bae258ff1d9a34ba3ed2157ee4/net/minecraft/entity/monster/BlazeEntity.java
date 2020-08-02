package net.minecraft.entity.monster;

import java.util.EnumSet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BlazeEntity extends MonsterEntity {
   private float heightOffset = 0.5F;
   private int heightOffsetUpdateTime;
   private static final DataParameter<Byte> ON_FIRE = EntityDataManager.createKey(BlazeEntity.class, DataSerializers.BYTE);

   public BlazeEntity(EntityType<? extends BlazeEntity> type, World p_i50215_2_) {
      super(type, p_i50215_2_);
      this.setPathPriority(PathNodeType.WATER, -1.0F);
      this.setPathPriority(PathNodeType.LAVA, 8.0F);
      this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
      this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
      this.experienceValue = 10;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(4, new BlazeEntity.FireballAttackGoal(this));
      this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
      this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
   }

   public static AttributeModifierMap.MutableAttribute func_234276_m_() {
      return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233823_f_, 6.0D).func_233815_a_(Attributes.field_233821_d_, (double)0.23F).func_233815_a_(Attributes.field_233819_b_, 48.0D);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(ON_FIRE, (byte)0);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_BLAZE_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_BLAZE_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_BLAZE_DEATH;
   }

   public float getBrightness() {
      return 1.0F;
   }

   public void livingTick() {
      if (!this.onGround && this.getMotion().y < 0.0D) {
         this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
      }

      if (this.world.isRemote) {
         if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
            this.world.playSound(this.func_226277_ct_() + 0.5D, this.func_226278_cu_() + 0.5D, this.func_226281_cx_() + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
         }

         for(int i = 0; i < 2; ++i) {
            this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.func_226282_d_(0.5D), this.func_226279_cv_(), this.func_226287_g_(0.5D), 0.0D, 0.0D, 0.0D);
         }
      }

      super.livingTick();
   }

   public boolean func_230270_dK_() {
      return true;
   }

   protected void updateAITasks() {
      --this.heightOffsetUpdateTime;
      if (this.heightOffsetUpdateTime <= 0) {
         this.heightOffsetUpdateTime = 100;
         this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
      }

      LivingEntity livingentity = this.getAttackTarget();
      if (livingentity != null && livingentity.func_226280_cw_() > this.func_226280_cw_() + (double)this.heightOffset && this.canAttack(livingentity)) {
         Vector3d vector3d = this.getMotion();
         this.setMotion(this.getMotion().add(0.0D, ((double)0.3F - vector3d.y) * (double)0.3F, 0.0D));
         this.isAirBorne = true;
      }

      super.updateAITasks();
   }

   public boolean func_225503_b_(float p_225503_1_, float p_225503_2_) {
      return false;
   }

   public boolean isBurning() {
      return this.isCharged();
   }

   private boolean isCharged() {
      return (this.dataManager.get(ON_FIRE) & 1) != 0;
   }

   private void setOnFire(boolean onFire) {
      byte b0 = this.dataManager.get(ON_FIRE);
      if (onFire) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.dataManager.set(ON_FIRE, b0);
   }

   static class FireballAttackGoal extends Goal {
      private final BlazeEntity blaze;
      private int attackStep;
      private int attackTime;
      private int field_223527_d;

      public FireballAttackGoal(BlazeEntity blazeIn) {
         this.blaze = blazeIn;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean shouldExecute() {
         LivingEntity livingentity = this.blaze.getAttackTarget();
         return livingentity != null && livingentity.isAlive() && this.blaze.canAttack(livingentity);
      }

      public void startExecuting() {
         this.attackStep = 0;
      }

      public void resetTask() {
         this.blaze.setOnFire(false);
         this.field_223527_d = 0;
      }

      public void tick() {
         --this.attackTime;
         LivingEntity livingentity = this.blaze.getAttackTarget();
         if (livingentity != null) {
            boolean flag = this.blaze.getEntitySenses().canSee(livingentity);
            if (flag) {
               this.field_223527_d = 0;
            } else {
               ++this.field_223527_d;
            }

            double d0 = this.blaze.getDistanceSq(livingentity);
            if (d0 < 4.0D) {
               if (!flag) {
                  return;
               }

               if (this.attackTime <= 0) {
                  this.attackTime = 20;
                  this.blaze.attackEntityAsMob(livingentity);
               }

               this.blaze.getMoveHelper().setMoveTo(livingentity.func_226277_ct_(), livingentity.func_226278_cu_(), livingentity.func_226281_cx_(), 1.0D);
            } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
               double d1 = livingentity.func_226277_ct_() - this.blaze.func_226277_ct_();
               double d2 = livingentity.func_226283_e_(0.5D) - this.blaze.func_226283_e_(0.5D);
               double d3 = livingentity.func_226281_cx_() - this.blaze.func_226281_cx_();
               if (this.attackTime <= 0) {
                  ++this.attackStep;
                  if (this.attackStep == 1) {
                     this.attackTime = 60;
                     this.blaze.setOnFire(true);
                  } else if (this.attackStep <= 4) {
                     this.attackTime = 6;
                  } else {
                     this.attackTime = 100;
                     this.attackStep = 0;
                     this.blaze.setOnFire(false);
                  }

                  if (this.attackStep > 1) {
                     float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                     if (!this.blaze.isSilent()) {
                        this.blaze.world.playEvent((PlayerEntity)null, 1018, this.blaze.func_233580_cy_(), 0);
                     }

                     for(int i = 0; i < 1; ++i) {
                        SmallFireballEntity smallfireballentity = new SmallFireballEntity(this.blaze.world, this.blaze, d1 + this.blaze.getRNG().nextGaussian() * (double)f, d2, d3 + this.blaze.getRNG().nextGaussian() * (double)f);
                        smallfireballentity.setPosition(smallfireballentity.func_226277_ct_(), this.blaze.func_226283_e_(0.5D) + 0.5D, smallfireballentity.func_226281_cx_());
                        this.blaze.world.addEntity(smallfireballentity);
                     }
                  }
               }

               this.blaze.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);
            } else if (this.field_223527_d < 5) {
               this.blaze.getMoveHelper().setMoveTo(livingentity.func_226277_ct_(), livingentity.func_226278_cu_(), livingentity.func_226281_cx_(), 1.0D);
            }

            super.tick();
         }
      }

      private double getFollowDistance() {
         return this.blaze.func_233637_b_(Attributes.field_233819_b_);
      }
   }
}