package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.StriderModel;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StriderRenderer extends MobRenderer<StriderEntity, StriderModel<StriderEntity>> {
   private static final ResourceLocation field_239397_a_ = new ResourceLocation("textures/entity/strider/strider.png");
   private static final ResourceLocation field_239398_g_ = new ResourceLocation("textures/entity/strider/strider_cold.png");

   public StriderRenderer(EntityRendererManager p_i232473_1_) {
      super(p_i232473_1_, new StriderModel<>(), 0.5F);
      this.addLayer(new SaddleLayer<>(this, new StriderModel<>(), new ResourceLocation("textures/entity/strider/strider_saddle.png")));
   }

   public ResourceLocation getEntityTexture(StriderEntity entity) {
      return entity.func_234315_eI_() ? field_239398_g_ : field_239397_a_;
   }

   protected void func_225620_a_(StriderEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      float f = 0.9375F;
      if (p_225620_1_.isChild()) {
         f *= 0.5F;
         this.shadowSize = 0.25F;
      } else {
         this.shadowSize = 0.5F;
      }

      p_225620_2_.func_227862_a_(f, f, f);
   }

   protected boolean func_230495_a_(StriderEntity p_230495_1_) {
      return p_230495_1_.func_234315_eI_();
   }
}