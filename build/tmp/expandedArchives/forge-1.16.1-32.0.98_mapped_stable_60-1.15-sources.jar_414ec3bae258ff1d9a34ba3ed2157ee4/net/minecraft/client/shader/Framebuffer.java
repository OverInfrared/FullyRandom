package net.minecraft.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Framebuffer {
   public int framebufferTextureWidth;
   public int framebufferTextureHeight;
   public int framebufferWidth;
   public int framebufferHeight;
   public final boolean useDepth;
   public int framebufferObject;
   public int framebufferTexture;
   public int depthBuffer;
   public final float[] framebufferColor;
   public int framebufferFilter;

   public Framebuffer(int p_i51175_1_, int p_i51175_2_, boolean p_i51175_3_, boolean p_i51175_4_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.useDepth = p_i51175_3_;
      this.framebufferObject = -1;
      this.framebufferTexture = -1;
      this.depthBuffer = -1;
      this.framebufferColor = new float[4];
      this.framebufferColor[0] = 1.0F;
      this.framebufferColor[1] = 1.0F;
      this.framebufferColor[2] = 1.0F;
      this.framebufferColor[3] = 0.0F;
      this.func_216491_a(p_i51175_1_, p_i51175_2_, p_i51175_4_);
   }

   public void func_216491_a(int p_216491_1_, int p_216491_2_, boolean p_216491_3_) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_227586_d_(p_216491_1_, p_216491_2_, p_216491_3_);
         });
      } else {
         this.func_227586_d_(p_216491_1_, p_216491_2_, p_216491_3_);
      }

   }

   private void func_227586_d_(int p_227586_1_, int p_227586_2_, boolean p_227586_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.func_227734_k_();
      if (this.framebufferObject >= 0) {
         this.deleteFramebuffer();
      }

      this.func_216492_b(p_227586_1_, p_227586_2_, p_227586_3_);
      GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, 0);
   }

   public void deleteFramebuffer() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.unbindFramebufferTexture();
      this.unbindFramebuffer();
      if (this.depthBuffer > -1) {
         TextureUtil.func_225679_a_(this.depthBuffer);
         this.depthBuffer = -1;
      }

      if (this.framebufferTexture > -1) {
         TextureUtil.func_225679_a_(this.framebufferTexture);
         this.framebufferTexture = -1;
      }

      if (this.framebufferObject > -1) {
         GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, 0);
         GlStateManager.func_227738_l_(this.framebufferObject);
         this.framebufferObject = -1;
      }

   }

   public void func_237506_a_(Framebuffer p_237506_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (GlStateManager.func_237508_S_()) {
         GlStateManager.func_227727_h_(36008, p_237506_1_.framebufferObject);
         GlStateManager.func_227727_h_(36009, this.framebufferObject);
         GlStateManager.func_237510_a_(0, 0, p_237506_1_.framebufferTextureWidth, p_237506_1_.framebufferTextureHeight, 0, 0, this.framebufferTextureWidth, this.framebufferTextureHeight, 256, 9728);
      } else {
         GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, this.framebufferObject);
         int i = GlStateManager.func_237514_p_();
         if (i != 0) {
            int j = GlStateManager.func_237515_r_();
            GlStateManager.func_227760_t_(i);
            GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, p_237506_1_.framebufferObject);
            GlStateManager.func_237509_a_(3553, 0, 0, 0, 0, 0, Math.min(this.framebufferTextureWidth, p_237506_1_.framebufferTextureWidth), Math.min(this.framebufferTextureHeight, p_237506_1_.framebufferTextureHeight));
            GlStateManager.func_227760_t_(j);
         }
      }

      GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, 0);
   }

   public void func_216492_b(int p_216492_1_, int p_216492_2_, boolean p_216492_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.framebufferWidth = p_216492_1_;
      this.framebufferHeight = p_216492_2_;
      this.framebufferTextureWidth = p_216492_1_;
      this.framebufferTextureHeight = p_216492_2_;
      this.framebufferObject = GlStateManager.func_227749_p_();
      this.framebufferTexture = TextureUtil.func_225678_a_();
      if (this.useDepth) {
         this.depthBuffer = TextureUtil.func_225678_a_();
         GlStateManager.func_227760_t_(this.depthBuffer);
         GlStateManager.func_227677_b_(3553, 10241, 9728);
         GlStateManager.func_227677_b_(3553, 10240, 9728);
         GlStateManager.func_227677_b_(3553, 10242, 10496);
         GlStateManager.func_227677_b_(3553, 10243, 10496);
         GlStateManager.func_227677_b_(3553, 34892, 0);
         if (!stencilEnabled)
         GlStateManager.func_227647_a_(3553, 0, 6402, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6402, 5126, (IntBuffer)null);
         else
         GlStateManager.func_227647_a_(3553, 0, org.lwjgl.opengl.GL30.GL_DEPTH32F_STENCIL8, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL, org.lwjgl.opengl.GL30.GL_FLOAT_32_UNSIGNED_INT_24_8_REV, null);
      }

      this.setFramebufferFilter(9728);
      GlStateManager.func_227760_t_(this.framebufferTexture);
      GlStateManager.func_227647_a_(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (IntBuffer)null);
      GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, this.framebufferObject);
      GlStateManager.func_227645_a_(FramebufferConstants.field_227592_a_, FramebufferConstants.field_227594_c_, 3553, this.framebufferTexture, 0);
      if (this.useDepth) {
         if(!stencilEnabled)
         GlStateManager.func_227645_a_(FramebufferConstants.field_227592_a_, FramebufferConstants.field_227595_d_, 3553, this.depthBuffer, 0);
         else if(net.minecraftforge.common.ForgeConfig.CLIENT.useCombinedDepthStencilAttachment.get()) {
            GlStateManager.func_227645_a_(FramebufferConstants.field_227592_a_, org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT, 3553, this.depthBuffer, 0);
         } else {
            GlStateManager.func_227645_a_(FramebufferConstants.field_227592_a_, org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT, 3553, this.depthBuffer, 0);
            GlStateManager.func_227645_a_(FramebufferConstants.field_227592_a_, org.lwjgl.opengl.GL30.GL_STENCIL_ATTACHMENT, 3553, this.depthBuffer, 0);
         }
      }

      this.checkFramebufferComplete();
      this.framebufferClear(p_216492_3_);
      this.unbindFramebufferTexture();
   }

   public void setFramebufferFilter(int framebufferFilterIn) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.framebufferFilter = framebufferFilterIn;
      GlStateManager.func_227760_t_(this.framebufferTexture);
      GlStateManager.func_227677_b_(3553, 10241, framebufferFilterIn);
      GlStateManager.func_227677_b_(3553, 10240, framebufferFilterIn);
      GlStateManager.func_227677_b_(3553, 10242, 10496);
      GlStateManager.func_227677_b_(3553, 10243, 10496);
      GlStateManager.func_227760_t_(0);
   }

   public void checkFramebufferComplete() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      int i = GlStateManager.func_227741_m_(FramebufferConstants.field_227592_a_);
      if (i != FramebufferConstants.field_227596_e_) {
         if (i == FramebufferConstants.field_227597_f_) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
         } else if (i == FramebufferConstants.field_227598_g_) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
         } else if (i == FramebufferConstants.field_227599_h_) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
         } else if (i == FramebufferConstants.field_227600_i_) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
         } else {
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
         }
      }
   }

   public void bindFramebufferTexture() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager.func_227760_t_(this.framebufferTexture);
   }

   public void unbindFramebufferTexture() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.func_227760_t_(0);
   }

   public void bindFramebuffer(boolean p_147610_1_) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_227585_c_(p_147610_1_);
         });
      } else {
         this.func_227585_c_(p_147610_1_);
      }

   }

   private void func_227585_c_(boolean p_227585_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, this.framebufferObject);
      if (p_227585_1_) {
         GlStateManager.func_227714_e_(0, 0, this.framebufferWidth, this.framebufferHeight);
      }

   }

   public void unbindFramebuffer() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, 0);
         });
      } else {
         GlStateManager.func_227727_h_(FramebufferConstants.field_227592_a_, 0);
      }

   }

   public void setFramebufferColor(float red, float green, float blue, float alpha) {
      this.framebufferColor[0] = red;
      this.framebufferColor[1] = green;
      this.framebufferColor[2] = blue;
      this.framebufferColor[3] = alpha;
   }

   public void framebufferRender(int width, int height) {
      this.framebufferRenderExt(width, height, true);
   }

   public void framebufferRenderExt(int width, int height, boolean p_178038_3_) {
      RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
      if (!RenderSystem.isInInitPhase()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_227588_e_(width, height, p_178038_3_);
         });
      } else {
         this.func_227588_e_(width, height, p_178038_3_);
      }

   }

   private void func_227588_e_(int p_227588_1_, int p_227588_2_, boolean p_227588_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager.func_227668_a_(true, true, true, false);
      GlStateManager.func_227731_j_();
      GlStateManager.func_227667_a_(false);
      GlStateManager.func_227768_x_(5889);
      GlStateManager.func_227625_M_();
      GlStateManager.func_227633_a_(0.0D, (double)p_227588_1_, (double)p_227588_2_, 0.0D, 1000.0D, 3000.0D);
      GlStateManager.func_227768_x_(5888);
      GlStateManager.func_227625_M_();
      GlStateManager.func_227688_c_(0.0F, 0.0F, -2000.0F);
      GlStateManager.func_227714_e_(0, 0, p_227588_1_, p_227588_2_);
      GlStateManager.func_227619_H_();
      GlStateManager.func_227722_g_();
      GlStateManager.func_227700_d_();
      if (p_227588_3_) {
         GlStateManager.func_227737_l_();
         GlStateManager.func_227725_h_();
      }

      GlStateManager.func_227702_d_(1.0F, 1.0F, 1.0F, 1.0F);
      this.bindFramebufferTexture();
      float f = (float)p_227588_1_;
      float f1 = (float)p_227588_2_;
      float f2 = (float)this.framebufferWidth / (float)this.framebufferTextureWidth;
      float f3 = (float)this.framebufferHeight / (float)this.framebufferTextureHeight;
      Tessellator tessellator = RenderSystem.renderThreadTesselator();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.func_225582_a_(0.0D, (double)f1, 0.0D).func_225583_a_(0.0F, 0.0F).func_225586_a_(255, 255, 255, 255).endVertex();
      bufferbuilder.func_225582_a_((double)f, (double)f1, 0.0D).func_225583_a_(f2, 0.0F).func_225586_a_(255, 255, 255, 255).endVertex();
      bufferbuilder.func_225582_a_((double)f, 0.0D, 0.0D).func_225583_a_(f2, f3).func_225586_a_(255, 255, 255, 255).endVertex();
      bufferbuilder.func_225582_a_(0.0D, 0.0D, 0.0D).func_225583_a_(0.0F, f3).func_225586_a_(255, 255, 255, 255).endVertex();
      tessellator.draw();
      this.unbindFramebufferTexture();
      GlStateManager.func_227667_a_(true);
      GlStateManager.func_227668_a_(true, true, true, true);
   }

   public void framebufferClear(boolean onMac) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.bindFramebuffer(true);
      GlStateManager.func_227673_b_(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
      int i = 16384;
      if (this.useDepth) {
         GlStateManager.func_227631_a_(1.0D);
         i |= 256;
      }

      GlStateManager.func_227658_a_(i, onMac);
      this.unbindFramebuffer();
   }


   /*================================ FORGE START ================================================*/
   private boolean stencilEnabled = false;
   /**
    * Attempts to enable 8 bits of stencil buffer on this FrameBuffer.
    * Modders must call this directly to set things up.
    * This is to prevent the default cause where graphics cards do not support stencil bits.
    * <b>Make sure to call this on the main render thread!</b>
    */
   public void enableStencil()
   {
      if(stencilEnabled) return;
      stencilEnabled = true;
      this.func_216491_a(framebufferWidth, framebufferHeight, net.minecraft.client.Minecraft.IS_RUNNING_ON_MAC);
   }

   /**
    * Returns wither or not this FBO has been successfully initialized with stencil bits.
    * If not, and a modder wishes it to be, they must call enableStencil.
    */
   public boolean isStencilEnabled()
   {
      return this.stencilEnabled;
   }
   /*================================ FORGE END   ================================================*/
}