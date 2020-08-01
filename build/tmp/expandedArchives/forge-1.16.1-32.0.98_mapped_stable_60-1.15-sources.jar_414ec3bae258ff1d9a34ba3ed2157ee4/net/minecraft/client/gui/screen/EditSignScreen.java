package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Arrays;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EditSignScreen extends Screen {
   private final SignTileEntityRenderer.SignModel field_228191_a_ = new SignTileEntityRenderer.SignModel();
   private final SignTileEntity tileSign;
   private int updateCounter;
   private int editLine;
   private TextInputUtil textInputUtil;
   private final String[] field_238846_r_ = Util.make(new String[4], (p_238849_0_) -> {
      Arrays.fill(p_238849_0_, "");
   });

   public EditSignScreen(SignTileEntity teSign) {
      super(new TranslationTextComponent("sign.edit"));
      this.tileSign = teSign;
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 4 + 120, 200, 20, DialogTexts.field_240632_c_, (p_238847_1_) -> {
         this.close();
      }));
      this.tileSign.setEditable(false);
      this.textInputUtil = new TextInputUtil(() -> {
         return this.field_238846_r_[this.editLine];
      }, (p_238850_1_) -> {
         this.field_238846_r_[this.editLine] = p_238850_1_;
         this.tileSign.setText(this.editLine, new StringTextComponent(p_238850_1_));
      }, TextInputUtil.func_238570_a_(this.field_230706_i_), TextInputUtil.func_238582_c_(this.field_230706_i_), (p_238848_1_) -> {
         return this.field_230706_i_.fontRenderer.getStringWidth(p_238848_1_) <= 90;
      });
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
      ClientPlayNetHandler clientplaynethandler = this.field_230706_i_.getConnection();
      if (clientplaynethandler != null) {
         clientplaynethandler.sendPacket(new CUpdateSignPacket(this.tileSign.getPos(), this.field_238846_r_[0], this.field_238846_r_[1], this.field_238846_r_[2], this.field_238846_r_[3]));
      }

      this.tileSign.setEditable(true);
   }

   public void func_231023_e_() {
      ++this.updateCounter;
      if (!this.tileSign.getType().isValidBlock(this.tileSign.getBlockState().getBlock())) {
         this.close();
      }

   }

   private void close() {
      this.tileSign.markDirty();
      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      this.textInputUtil.func_216894_a(p_231042_1_);
      return true;
   }

   public void func_231175_as__() {
      this.close();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 265) {
         this.editLine = this.editLine - 1 & 3;
         this.textInputUtil.func_238588_f_();
         return true;
      } else if (p_231046_1_ != 264 && p_231046_1_ != 257 && p_231046_1_ != 335) {
         return this.textInputUtil.func_216897_a(p_231046_1_) ? true : super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      } else {
         this.editLine = this.editLine + 1 & 3;
         this.textInputUtil.func_238588_f_();
         return true;
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      RenderHelper.func_227783_c_();
      this.func_230446_a_(p_230430_1_);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 40, 16777215);
      p_230430_1_.func_227860_a_();
      p_230430_1_.func_227861_a_((double)(this.field_230708_k_ / 2), 0.0D, 50.0D);
      float f = 93.75F;
      p_230430_1_.func_227862_a_(93.75F, -93.75F, 93.75F);
      p_230430_1_.func_227861_a_(0.0D, -1.3125D, 0.0D);
      BlockState blockstate = this.tileSign.getBlockState();
      boolean flag = blockstate.getBlock() instanceof StandingSignBlock;
      if (!flag) {
         p_230430_1_.func_227861_a_(0.0D, -0.3125D, 0.0D);
      }

      boolean flag1 = this.updateCounter / 6 % 2 == 0;
      float f1 = 0.6666667F;
      p_230430_1_.func_227860_a_();
      p_230430_1_.func_227862_a_(0.6666667F, -0.6666667F, -0.6666667F);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = this.field_230706_i_.func_228019_au_().func_228487_b_();
      RenderMaterial rendermaterial = SignTileEntityRenderer.func_228877_a_(blockstate.getBlock());
      IVertexBuilder ivertexbuilder = rendermaterial.func_229311_a_(irendertypebuffer$impl, this.field_228191_a_::func_228282_a_);
      this.field_228191_a_.field_78166_a.func_228308_a_(p_230430_1_, ivertexbuilder, 15728880, OverlayTexture.field_229196_a_);
      if (flag) {
         this.field_228191_a_.field_78165_b.func_228308_a_(p_230430_1_, ivertexbuilder, 15728880, OverlayTexture.field_229196_a_);
      }

      p_230430_1_.func_227865_b_();
      float f2 = 0.010416667F;
      p_230430_1_.func_227861_a_(0.0D, (double)0.33333334F, (double)0.046666667F);
      p_230430_1_.func_227862_a_(0.010416667F, -0.010416667F, 0.010416667F);
      int i = this.tileSign.getTextColor().getTextColor();
      int j = this.textInputUtil.func_216896_c();
      int k = this.textInputUtil.func_216898_d();
      int l = this.editLine * 10 - this.field_238846_r_.length * 5;
      Matrix4f matrix4f = p_230430_1_.func_227866_c_().func_227870_a_();

      for(int i1 = 0; i1 < this.field_238846_r_.length; ++i1) {
         String s = this.field_238846_r_[i1];
         if (s != null) {
            if (this.field_230712_o_.getBidiFlag()) {
               s = this.field_230712_o_.bidiReorder(s);
            }

            float f3 = (float)(-this.field_230706_i_.fontRenderer.getStringWidth(s) / 2);
            this.field_230706_i_.fontRenderer.func_238411_a_(s, f3, (float)(i1 * 10 - this.field_238846_r_.length * 5), i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
            if (i1 == this.editLine && j >= 0 && flag1) {
               int j1 = this.field_230706_i_.fontRenderer.getStringWidth(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
               int k1 = j1 - this.field_230706_i_.fontRenderer.getStringWidth(s) / 2;
               if (j >= s.length()) {
                  this.field_230706_i_.fontRenderer.func_238411_a_("_", (float)k1, (float)l, i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
               }
            }
         }
      }

      irendertypebuffer$impl.func_228461_a_();

      for(int i3 = 0; i3 < this.field_238846_r_.length; ++i3) {
         String s1 = this.field_238846_r_[i3];
         if (s1 != null && i3 == this.editLine && j >= 0) {
            int j3 = this.field_230706_i_.fontRenderer.getStringWidth(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
            int k3 = j3 - this.field_230706_i_.fontRenderer.getStringWidth(s1) / 2;
            if (flag1 && j < s1.length()) {
               func_238467_a_(p_230430_1_, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
            }

            if (k != j) {
               int l3 = Math.min(j, k);
               int l1 = Math.max(j, k);
               int i2 = this.field_230706_i_.fontRenderer.getStringWidth(s1.substring(0, l3)) - this.field_230706_i_.fontRenderer.getStringWidth(s1) / 2;
               int j2 = this.field_230706_i_.fontRenderer.getStringWidth(s1.substring(0, l1)) - this.field_230706_i_.fontRenderer.getStringWidth(s1) / 2;
               int k2 = Math.min(i2, j2);
               int l2 = Math.max(i2, j2);
               Tessellator tessellator = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tessellator.getBuffer();
               RenderSystem.disableTexture();
               RenderSystem.enableColorLogicOp();
               RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
               bufferbuilder.func_227888_a_(matrix4f, (float)k2, (float)(l + 9), 0.0F).func_225586_a_(0, 0, 255, 255).endVertex();
               bufferbuilder.func_227888_a_(matrix4f, (float)l2, (float)(l + 9), 0.0F).func_225586_a_(0, 0, 255, 255).endVertex();
               bufferbuilder.func_227888_a_(matrix4f, (float)l2, (float)l, 0.0F).func_225586_a_(0, 0, 255, 255).endVertex();
               bufferbuilder.func_227888_a_(matrix4f, (float)k2, (float)l, 0.0F).func_225586_a_(0, 0, 255, 255).endVertex();
               bufferbuilder.finishDrawing();
               WorldVertexBufferUploader.draw(bufferbuilder);
               RenderSystem.disableColorLogicOp();
               RenderSystem.enableTexture();
            }
         }
      }

      p_230430_1_.func_227865_b_();
      RenderHelper.func_227784_d_();
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}