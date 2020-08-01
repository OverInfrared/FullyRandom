package net.minecraft.client.renderer.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillageSectionsDebugRender implements DebugRenderer.IDebugRenderer {
   private final Set<SectionPos> field_239375_a_ = Sets.newHashSet();

   VillageSectionsDebugRender() {
   }

   public void func_217675_a() {
      this.field_239375_a_.clear();
   }

   public void func_239378_a_(SectionPos p_239378_1_) {
      this.field_239375_a_.add(p_239378_1_);
   }

   public void func_239379_b_(SectionPos p_239379_1_) {
      this.field_239375_a_.remove(p_239379_1_);
   }

   public void func_225619_a_(MatrixStack p_225619_1_, IRenderTypeBuffer p_225619_2_, double p_225619_3_, double p_225619_5_, double p_225619_7_) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.func_239376_a_(p_225619_3_, p_225619_5_, p_225619_7_);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   private void func_239376_a_(double p_239376_1_, double p_239376_3_, double p_239376_5_) {
      BlockPos blockpos = new BlockPos(p_239376_1_, p_239376_3_, p_239376_5_);
      this.field_239375_a_.forEach((p_239377_1_) -> {
         if (blockpos.withinDistance(p_239377_1_.getCenter(), 60.0D)) {
            func_239380_c_(p_239377_1_);
         }

      });
   }

   private static void func_239380_c_(SectionPos p_239380_0_) {
      float f = 1.0F;
      BlockPos blockpos = p_239380_0_.getCenter();
      BlockPos blockpos1 = blockpos.add(-1.0D, -1.0D, -1.0D);
      BlockPos blockpos2 = blockpos.add(1.0D, 1.0D, 1.0D);
      DebugRenderer.func_217735_a(blockpos1, blockpos2, 0.2F, 1.0F, 0.2F, 0.15F);
   }
}