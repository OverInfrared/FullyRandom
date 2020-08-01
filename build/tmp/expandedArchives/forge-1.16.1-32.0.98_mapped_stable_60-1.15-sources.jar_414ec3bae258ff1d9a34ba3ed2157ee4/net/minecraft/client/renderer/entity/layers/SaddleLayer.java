package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEquipable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SaddleLayer<T extends Entity & IEquipable, M extends EntityModel<T>> extends LayerRenderer<T, M> {
   private final ResourceLocation field_239408_a_;
   private final M field_239409_b_;

   public SaddleLayer(IEntityRenderer<T, M> p_i232478_1_, M p_i232478_2_, ResourceLocation p_i232478_3_) {
      super(p_i232478_1_);
      this.field_239409_b_ = p_i232478_2_;
      this.field_239408_a_ = p_i232478_3_;
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if (p_225628_4_.isHorseSaddled()) {
         this.getEntityModel().setModelAttributes(this.field_239409_b_);
         this.field_239409_b_.setLivingAnimations(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_);
         this.field_239409_b_.func_225597_a_(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
         IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228640_c_(this.field_239408_a_));
         this.field_239409_b_.func_225598_a_(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}