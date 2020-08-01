package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PiglinModel<T extends MobEntity> extends PlayerModel<T> {
   public final ModelRenderer field_239115_a_;
   public final ModelRenderer field_239116_b_;
   private final ModelRenderer field_241660_y_;
   private final ModelRenderer field_241661_z_;
   private final ModelRenderer field_241658_A_;
   private final ModelRenderer field_241659_B_;

   public PiglinModel(float p_i232336_1_, int p_i232336_2_, int p_i232336_3_) {
      super(p_i232336_1_, false);
      this.textureWidth = p_i232336_2_;
      this.textureHeight = p_i232336_3_;
      this.bipedBody = new ModelRenderer(this, 16, 16);
      this.bipedBody.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i232336_1_);
      this.bipedHead = new ModelRenderer(this);
      this.bipedHead.func_78784_a(0, 0).func_228301_a_(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, p_i232336_1_);
      this.bipedHead.func_78784_a(31, 1).func_228301_a_(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, p_i232336_1_);
      this.bipedHead.func_78784_a(2, 4).func_228301_a_(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, p_i232336_1_);
      this.bipedHead.func_78784_a(2, 0).func_228301_a_(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, p_i232336_1_);
      this.field_239115_a_ = new ModelRenderer(this);
      this.field_239115_a_.setRotationPoint(4.5F, -6.0F, 0.0F);
      this.field_239115_a_.func_78784_a(51, 6).func_228301_a_(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, p_i232336_1_);
      this.bipedHead.func_78792_a(this.field_239115_a_);
      this.field_239116_b_ = new ModelRenderer(this);
      this.field_239116_b_.setRotationPoint(-4.5F, -6.0F, 0.0F);
      this.field_239116_b_.func_78784_a(39, 6).func_228301_a_(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, p_i232336_1_);
      this.bipedHead.func_78792_a(this.field_239116_b_);
      this.bipedHeadwear = new ModelRenderer(this);
      this.field_241660_y_ = this.bipedBody.func_241662_a_();
      this.field_241661_z_ = this.bipedHead.func_241662_a_();
      this.field_241658_A_ = this.bipedLeftArm.func_241662_a_();
      this.field_241659_B_ = this.bipedLeftArm.func_241662_a_();
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.bipedBody.func_217177_a(this.field_241660_y_);
      this.bipedHead.func_217177_a(this.field_241661_z_);
      this.bipedLeftArm.func_217177_a(this.field_241658_A_);
      this.bipedRightArm.func_217177_a(this.field_241659_B_);
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      float f = ((float)Math.PI / 6F);
      float f1 = p_225597_4_ * 0.1F + p_225597_2_ * 0.5F;
      float f2 = 0.08F + p_225597_3_ * 0.4F;
      this.field_239115_a_.rotateAngleZ = (-(float)Math.PI / 6F) - MathHelper.cos(f1 * 1.2F) * f2;
      this.field_239116_b_.rotateAngleZ = ((float)Math.PI / 6F) + MathHelper.cos(f1) * f2;
      if (p_225597_1_.getType() == EntityType.field_233591_ai_) {
         PiglinEntity piglinentity = (PiglinEntity)p_225597_1_;
         PiglinEntity.Action piglinentity$action = piglinentity.func_234424_eM_();
         if (piglinentity$action == PiglinEntity.Action.DANCING) {
            float f3 = p_225597_4_ / 60.0F;
            this.field_239116_b_.rotateAngleZ = ((float)Math.PI / 6F) + ((float)Math.PI / 180F) * MathHelper.sin(f3 * 30.0F) * 10.0F;
            this.field_239115_a_.rotateAngleZ = (-(float)Math.PI / 6F) - ((float)Math.PI / 180F) * MathHelper.cos(f3 * 30.0F) * 10.0F;
            this.bipedHead.rotationPointX = MathHelper.sin(f3 * 10.0F);
            this.bipedHead.rotationPointY = MathHelper.sin(f3 * 40.0F) + 0.4F;
            this.bipedRightArm.rotateAngleZ = ((float)Math.PI / 180F) * (70.0F + MathHelper.cos(f3 * 40.0F) * 10.0F);
            this.bipedLeftArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ * -1.0F;
            this.bipedRightArm.rotationPointY = MathHelper.sin(f3 * 40.0F) * 0.5F + 1.5F;
            this.bipedLeftArm.rotationPointY = MathHelper.sin(f3 * 40.0F) * 0.5F + 1.5F;
            this.bipedBody.rotationPointY = MathHelper.sin(f3 * 40.0F) * 0.35F;
         } else if (piglinentity$action == PiglinEntity.Action.ATTACKING_WITH_MELEE_WEAPON && this.swingProgress == 0.0F) {
            this.func_239117_a_(p_225597_1_);
         } else if (piglinentity$action == PiglinEntity.Action.CROSSBOW_HOLD) {
            ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, !p_225597_1_.isLeftHanded());
         } else if (piglinentity$action == PiglinEntity.Action.CROSSBOW_CHARGE) {
            ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, p_225597_1_, !p_225597_1_.isLeftHanded());
         } else if (piglinentity$action == PiglinEntity.Action.ADMIRING_ITEM) {
            this.bipedHead.rotateAngleX = 0.5F;
            this.bipedHead.rotateAngleY = 0.0F;
            if (p_225597_1_.isLeftHanded()) {
               this.bipedRightArm.rotateAngleY = -0.5F;
               this.bipedRightArm.rotateAngleX = -0.9F;
            } else {
               this.bipedLeftArm.rotateAngleY = 0.5F;
               this.bipedLeftArm.rotateAngleX = -0.9F;
            }
         }
      } else if (p_225597_1_.getType() == EntityType.field_233592_ba_) {
         ModelHelper.func_239105_a_(this.bipedLeftArm, this.bipedRightArm, p_225597_1_.isAggressive(), this.swingProgress, p_225597_4_);
      }

      this.bipedLeftLegwear.func_217177_a(this.bipedLeftLeg);
      this.bipedRightLegwear.func_217177_a(this.bipedRightLeg);
      this.bipedLeftArmwear.func_217177_a(this.bipedLeftArm);
      this.bipedRightArmwear.func_217177_a(this.bipedRightArm);
      this.bipedBodyWear.func_217177_a(this.bipedBody);
   }

   protected void func_230486_a_(T p_230486_1_, float p_230486_2_) {
      if (this.swingProgress > 0.0F && p_230486_1_ instanceof PiglinEntity && ((PiglinEntity)p_230486_1_).func_234424_eM_() == PiglinEntity.Action.ATTACKING_WITH_MELEE_WEAPON) {
         ModelHelper.func_239103_a_(this.bipedRightArm, this.bipedLeftArm, p_230486_1_, this.swingProgress, p_230486_2_);
      } else {
         super.func_230486_a_(p_230486_1_, p_230486_2_);
      }
   }

   private void func_239117_a_(T p_239117_1_) {
      if (p_239117_1_.isLeftHanded()) {
         this.bipedLeftArm.rotateAngleX = -1.8F;
      } else {
         this.bipedRightArm.rotateAngleX = -1.8F;
      }

   }
}