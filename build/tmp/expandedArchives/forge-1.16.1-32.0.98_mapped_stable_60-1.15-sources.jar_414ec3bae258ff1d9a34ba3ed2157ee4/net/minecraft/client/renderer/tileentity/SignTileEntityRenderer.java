package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SignTileEntityRenderer extends TileEntityRenderer<SignTileEntity> {
   private final SignTileEntityRenderer.SignModel model = new SignTileEntityRenderer.SignModel();

   public SignTileEntityRenderer(TileEntityRendererDispatcher p_i226014_1_) {
      super(p_i226014_1_);
   }

   public void func_225616_a_(SignTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      BlockState blockstate = p_225616_1_.getBlockState();
      p_225616_3_.func_227860_a_();
      float f = 0.6666667F;
      if (blockstate.getBlock() instanceof StandingSignBlock) {
         p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
         float f1 = -((float)(blockstate.get(StandingSignBlock.ROTATION) * 360) / 16.0F);
         p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1));
         this.model.field_78165_b.showModel = true;
      } else {
         p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
         float f4 = -blockstate.get(WallSignBlock.FACING).getHorizontalAngle();
         p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f4));
         p_225616_3_.func_227861_a_(0.0D, -0.3125D, -0.4375D);
         this.model.field_78165_b.showModel = false;
      }

      p_225616_3_.func_227860_a_();
      p_225616_3_.func_227862_a_(0.6666667F, -0.6666667F, -0.6666667F);
      RenderMaterial rendermaterial = func_228877_a_(blockstate.getBlock());
      IVertexBuilder ivertexbuilder = rendermaterial.func_229311_a_(p_225616_4_, this.model::func_228282_a_);
      this.model.field_78166_a.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
      this.model.field_78165_b.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
      p_225616_3_.func_227865_b_();
      FontRenderer fontrenderer = this.field_228858_b_.getFontRenderer();
      float f2 = 0.010416667F;
      p_225616_3_.func_227861_a_(0.0D, (double)0.33333334F, (double)0.046666667F);
      p_225616_3_.func_227862_a_(0.010416667F, -0.010416667F, 0.010416667F);
      int i = p_225616_1_.getTextColor().getTextColor();
      double d0 = 0.4D;
      int j = (int)((double)NativeImage.func_227791_b_(i) * 0.4D);
      int k = (int)((double)NativeImage.func_227793_c_(i) * 0.4D);
      int l = (int)((double)NativeImage.func_227795_d_(i) * 0.4D);
      int i1 = NativeImage.func_227787_a_(0, l, k, j);
      int j1 = 20;

      for(int k1 = 0; k1 < 4; ++k1) {
         ITextProperties itextproperties = p_225616_1_.func_235677_a_(k1, (p_239309_1_) -> {
            List<ITextProperties> list = fontrenderer.func_238420_b_().func_238362_b_(p_239309_1_, 90, Style.field_240709_b_);
            return list.isEmpty() ? ITextProperties.field_240651_c_ : list.get(0);
         });
         if (itextproperties != null) {
            float f3 = (float)(-fontrenderer.func_238414_a_(itextproperties) / 2);
            fontrenderer.func_238416_a_(itextproperties, f3, (float)(k1 * 10 - 20), i1, false, p_225616_3_.func_227866_c_().func_227870_a_(), p_225616_4_, false, 0, p_225616_5_);
         }
      }

      p_225616_3_.func_227865_b_();
   }

   public static RenderMaterial func_228877_a_(Block p_228877_0_) {
      WoodType woodtype;
      if (p_228877_0_ instanceof AbstractSignBlock) {
         woodtype = ((AbstractSignBlock)p_228877_0_).func_226944_c_();
      } else {
         woodtype = WoodType.field_227038_a_;
      }

      return Atlases.func_228773_a_(woodtype);
   }

   @OnlyIn(Dist.CLIENT)
   public static final class SignModel extends Model {
      public final ModelRenderer field_78166_a = new ModelRenderer(64, 32, 0, 0);
      public final ModelRenderer field_78165_b;

      public SignModel() {
         super(RenderType::func_228640_c_);
         this.field_78166_a.func_228301_a_(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
         this.field_78165_b = new ModelRenderer(64, 32, 0, 14);
         this.field_78165_b.func_228301_a_(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
      }

      public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
         this.field_78166_a.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
         this.field_78165_b.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
      }
   }
}