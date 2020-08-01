package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoarModel<T extends MobEntity & IFlinging> extends AgeableModel<T> {
   private final ModelRenderer field_239106_a_;
   private final ModelRenderer field_239107_b_;
   private final ModelRenderer field_239108_f_;
   private final ModelRenderer field_239109_g_;
   private final ModelRenderer field_239110_h_;
   private final ModelRenderer field_239111_i_;
   private final ModelRenderer field_239112_j_;
   private final ModelRenderer field_239113_k_;
   private final ModelRenderer field_239114_l_;

   public BoarModel() {
      super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.field_239109_g_ = new ModelRenderer(this);
      this.field_239109_g_.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.field_239109_g_.func_78784_a(1, 1).func_228300_a_(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F);
      this.field_239114_l_ = new ModelRenderer(this);
      this.field_239114_l_.setRotationPoint(0.0F, -14.0F, -5.0F);
      this.field_239114_l_.func_78784_a(90, 33).func_228301_a_(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, 0.001F);
      this.field_239109_g_.func_78792_a(this.field_239114_l_);
      this.field_239106_a_ = new ModelRenderer(this);
      this.field_239106_a_.setRotationPoint(0.0F, 2.0F, -12.0F);
      this.field_239106_a_.func_78784_a(61, 1).func_228300_a_(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F);
      this.field_239107_b_ = new ModelRenderer(this);
      this.field_239107_b_.setRotationPoint(-6.0F, -2.0F, -3.0F);
      this.field_239107_b_.func_78784_a(1, 1).func_228300_a_(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
      this.field_239107_b_.rotateAngleZ = -0.6981317F;
      this.field_239106_a_.func_78792_a(this.field_239107_b_);
      this.field_239108_f_ = new ModelRenderer(this);
      this.field_239108_f_.setRotationPoint(6.0F, -2.0F, -3.0F);
      this.field_239108_f_.func_78784_a(1, 6).func_228300_a_(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
      this.field_239108_f_.rotateAngleZ = 0.6981317F;
      this.field_239106_a_.func_78792_a(this.field_239108_f_);
      ModelRenderer modelrenderer = new ModelRenderer(this);
      modelrenderer.setRotationPoint(-7.0F, 2.0F, -12.0F);
      modelrenderer.func_78784_a(10, 13).func_228300_a_(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
      this.field_239106_a_.func_78792_a(modelrenderer);
      ModelRenderer modelrenderer1 = new ModelRenderer(this);
      modelrenderer1.setRotationPoint(7.0F, 2.0F, -12.0F);
      modelrenderer1.func_78784_a(1, 13).func_228300_a_(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
      this.field_239106_a_.func_78792_a(modelrenderer1);
      this.field_239106_a_.rotateAngleX = 0.87266463F;
      int i = 14;
      int j = 11;
      this.field_239110_h_ = new ModelRenderer(this);
      this.field_239110_h_.setRotationPoint(-4.0F, 10.0F, -8.5F);
      this.field_239110_h_.func_78784_a(66, 42).func_228300_a_(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
      this.field_239111_i_ = new ModelRenderer(this);
      this.field_239111_i_.setRotationPoint(4.0F, 10.0F, -8.5F);
      this.field_239111_i_.func_78784_a(41, 42).func_228300_a_(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
      this.field_239112_j_ = new ModelRenderer(this);
      this.field_239112_j_.setRotationPoint(-5.0F, 13.0F, 10.0F);
      this.field_239112_j_.func_78784_a(21, 45).func_228300_a_(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
      this.field_239113_k_ = new ModelRenderer(this);
      this.field_239113_k_.setRotationPoint(5.0F, 13.0F, 10.0F);
      this.field_239113_k_.func_78784_a(0, 45).func_228300_a_(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
   }

   protected Iterable<ModelRenderer> func_225602_a_() {
      return ImmutableList.of(this.field_239106_a_);
   }

   protected Iterable<ModelRenderer> func_225600_b_() {
      return ImmutableList.of(this.field_239109_g_, this.field_239110_h_, this.field_239111_i_, this.field_239112_j_, this.field_239113_k_);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.field_239107_b_.rotateAngleZ = -0.6981317F - p_225597_3_ * MathHelper.sin(p_225597_2_);
      this.field_239108_f_.rotateAngleZ = 0.6981317F + p_225597_3_ * MathHelper.sin(p_225597_2_);
      this.field_239106_a_.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      int i = p_225597_1_.func_230290_eL_();
      float f = 1.0F - (float)MathHelper.abs(10 - 2 * i) / 10.0F;
      this.field_239106_a_.rotateAngleX = MathHelper.lerp(f, 0.87266463F, -0.34906584F);
      if (p_225597_1_.isChild()) {
         this.field_239106_a_.rotationPointY = MathHelper.lerp(f, 2.0F, 5.0F);
         this.field_239114_l_.rotationPointZ = -3.0F;
      } else {
         this.field_239106_a_.rotationPointY = 2.0F;
         this.field_239114_l_.rotationPointZ = -7.0F;
      }

      float f1 = 1.2F;
      this.field_239110_h_.rotateAngleX = MathHelper.cos(p_225597_2_) * 1.2F * p_225597_3_;
      this.field_239111_i_.rotateAngleX = MathHelper.cos(p_225597_2_ + (float)Math.PI) * 1.2F * p_225597_3_;
      this.field_239112_j_.rotateAngleX = this.field_239111_i_.rotateAngleX;
      this.field_239113_k_.rotateAngleX = this.field_239110_h_.rotateAngleX;
   }
}