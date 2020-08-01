package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FogRenderer {
   private static float red;
   private static float green;
   private static float blue;
   private static int lastWaterFogColor = -1;
   private static int waterFogColor = -1;
   private static long waterFogUpdateTime = -1L;

   public static void func_228371_a_(ActiveRenderInfo p_228371_0_, float p_228371_1_, ClientWorld p_228371_2_, int p_228371_3_, float p_228371_4_) {
      FluidState fluidstate = p_228371_0_.getFluidState();
      if (fluidstate.isTagged(FluidTags.WATER)) {
         long i = Util.milliTime();
         int j = p_228371_2_.func_226691_t_(new BlockPos(p_228371_0_.getProjectedView())).getWaterFogColor();
         if (waterFogUpdateTime < 0L) {
            lastWaterFogColor = j;
            waterFogColor = j;
            waterFogUpdateTime = i;
         }

         int k = lastWaterFogColor >> 16 & 255;
         int l = lastWaterFogColor >> 8 & 255;
         int i1 = lastWaterFogColor & 255;
         int j1 = waterFogColor >> 16 & 255;
         int k1 = waterFogColor >> 8 & 255;
         int l1 = waterFogColor & 255;
         float f = MathHelper.clamp((float)(i - waterFogUpdateTime) / 5000.0F, 0.0F, 1.0F);
         float f1 = MathHelper.lerp(f, (float)j1, (float)k);
         float f2 = MathHelper.lerp(f, (float)k1, (float)l);
         float f3 = MathHelper.lerp(f, (float)l1, (float)i1);
         red = f1 / 255.0F;
         green = f2 / 255.0F;
         blue = f3 / 255.0F;
         if (lastWaterFogColor != j) {
            lastWaterFogColor = j;
            waterFogColor = MathHelper.floor(f1) << 16 | MathHelper.floor(f2) << 8 | MathHelper.floor(f3);
            waterFogUpdateTime = i;
         }
      } else if (fluidstate.isTagged(FluidTags.LAVA)) {
         red = 0.6F;
         green = 0.1F;
         blue = 0.0F;
         waterFogUpdateTime = -1L;
      } else {
         float f4 = 0.25F + 0.75F * (float)p_228371_3_ / 32.0F;
         f4 = 1.0F - (float)Math.pow((double)f4, 0.25D);
         Vector3d vector3d = p_228371_2_.func_228318_a_(p_228371_0_.getBlockPos(), p_228371_1_);
         float f5 = (float)vector3d.x;
         float f8 = (float)vector3d.y;
         float f11 = (float)vector3d.z;
         float f12 = MathHelper.clamp(MathHelper.cos(p_228371_2_.getCelestialAngle(p_228371_1_) * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
         BiomeManager biomemanager = p_228371_2_.func_225523_d_();
         Vector3d vector3d1 = p_228371_0_.getProjectedView().subtract(2.0D, 2.0D, 2.0D).scale(0.25D);
         Vector3d vector3d2 = CubicSampler.func_240807_a_(vector3d1, (p_239218_3_, p_239218_4_, p_239218_5_) -> {
            return p_228371_2_.func_239132_a_().func_230494_a_(Vector3d.func_237487_a_(biomemanager.func_235199_a_(p_239218_3_, p_239218_4_, p_239218_5_).func_235080_i_()), f12);
         });
         red = (float)vector3d2.getX();
         green = (float)vector3d2.getY();
         blue = (float)vector3d2.getZ();
         if (p_228371_3_ >= 4) {
            float f13 = MathHelper.sin(p_228371_2_.getCelestialAngleRadians(p_228371_1_)) > 0.0F ? -1.0F : 1.0F;
            Vector3f vector3f = new Vector3f(f13, 0.0F, 0.0F);
            float f17 = p_228371_0_.func_227996_l_().dot(vector3f);
            if (f17 < 0.0F) {
               f17 = 0.0F;
            }

            if (f17 > 0.0F) {
               float[] afloat = p_228371_2_.func_239132_a_().func_230492_a_(p_228371_2_.getCelestialAngle(p_228371_1_), p_228371_1_);
               if (afloat != null) {
                  f17 = f17 * afloat[3];
                  red = red * (1.0F - f17) + afloat[0] * f17;
                  green = green * (1.0F - f17) + afloat[1] * f17;
                  blue = blue * (1.0F - f17) + afloat[2] * f17;
               }
            }
         }

         red += (f5 - red) * f4;
         green += (f8 - green) * f4;
         blue += (f11 - blue) * f4;
         float f14 = p_228371_2_.getRainStrength(p_228371_1_);
         if (f14 > 0.0F) {
            float f15 = 1.0F - f14 * 0.5F;
            float f18 = 1.0F - f14 * 0.4F;
            red *= f15;
            green *= f15;
            blue *= f18;
         }

         float f16 = p_228371_2_.getThunderStrength(p_228371_1_);
         if (f16 > 0.0F) {
            float f19 = 1.0F - f16 * 0.5F;
            red *= f19;
            green *= f19;
            blue *= f19;
         }

         waterFogUpdateTime = -1L;
      }

      double d0 = p_228371_0_.getProjectedView().y * p_228371_2_.getWorldInfo().func_239160_g_();
      if (p_228371_0_.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)p_228371_0_.getRenderViewEntity()).isPotionActive(Effects.BLINDNESS)) {
         int i2 = ((LivingEntity)p_228371_0_.getRenderViewEntity()).getActivePotionEffect(Effects.BLINDNESS).getDuration();
         if (i2 < 20) {
            d0 *= (double)(1.0F - (float)i2 / 20.0F);
         } else {
            d0 = 0.0D;
         }
      }

      if (d0 < 1.0D && !fluidstate.isTagged(FluidTags.LAVA)) {
         if (d0 < 0.0D) {
            d0 = 0.0D;
         }

         d0 = d0 * d0;
         red = (float)((double)red * d0);
         green = (float)((double)green * d0);
         blue = (float)((double)blue * d0);
      }

      if (p_228371_4_ > 0.0F) {
         red = red * (1.0F - p_228371_4_) + red * 0.7F * p_228371_4_;
         green = green * (1.0F - p_228371_4_) + green * 0.6F * p_228371_4_;
         blue = blue * (1.0F - p_228371_4_) + blue * 0.6F * p_228371_4_;
      }

      if (fluidstate.isTagged(FluidTags.WATER)) {
         float f6 = 0.0F;
         if (p_228371_0_.getRenderViewEntity() instanceof ClientPlayerEntity) {
            ClientPlayerEntity clientplayerentity = (ClientPlayerEntity)p_228371_0_.getRenderViewEntity();
            f6 = clientplayerentity.getWaterBrightness();
         }

         float f9 = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
         // Forge: fix MC-4647 and MC-10480
         if (Float.isInfinite(f9)) f9 = Math.nextAfter(f9, 0.0);
         red = red * (1.0F - f6) + red * f9 * f6;
         green = green * (1.0F - f6) + green * f9 * f6;
         blue = blue * (1.0F - f6) + blue * f9 * f6;
      } else if (p_228371_0_.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)p_228371_0_.getRenderViewEntity()).isPotionActive(Effects.NIGHT_VISION)) {
         float f7 = GameRenderer.getNightVisionBrightness((LivingEntity)p_228371_0_.getRenderViewEntity(), p_228371_1_);
         float f10 = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
         // Forge: fix MC-4647 and MC-10480
         if (Float.isInfinite(f10)) f10 = Math.nextAfter(f10, 0.0);
         red = red * (1.0F - f7) + red * f10 * f7;
         green = green * (1.0F - f7) + green * f10 * f7;
         blue = blue * (1.0F - f7) + blue * f10 * f7;
      }

      net.minecraftforge.client.event.EntityViewRenderEvent.FogColors event = new net.minecraftforge.client.event.EntityViewRenderEvent.FogColors(p_228371_0_, p_228371_1_, red, green, blue);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);

      red = event.getRed();
      green = event.getGreen();
      blue = event.getBlue();

      RenderSystem.clearColor(red, green, blue, 0.0F);
   }

   public static void func_228370_a_() {
      RenderSystem.fogDensity(0.0F);
      RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
   }

    @Deprecated // FORGE: Pass in partialTicks
    public static void func_228372_a_(ActiveRenderInfo p_228372_0_, FogRenderer.FogType p_228372_1_, float p_228372_2_, boolean p_228372_3_) {
       setupFog(p_228372_0_, p_228372_1_, p_228372_2_, p_228372_3_, 0);
    }

    public static void setupFog(ActiveRenderInfo p_228372_0_, FogRenderer.FogType p_228372_1_, float p_228372_2_, boolean p_228372_3_, float partialTicks) {
      FluidState fluidstate = p_228372_0_.getFluidState();
      Entity entity = p_228372_0_.getRenderViewEntity();
      boolean flag = fluidstate.getFluid() != Fluids.EMPTY;
      float hook = net.minecraftforge.client.ForgeHooksClient.getFogDensity(p_228372_1_, p_228372_0_, partialTicks, 0.1F);
      if (hook >= 0) RenderSystem.fogDensity(hook);
      else
      if (fluidstate.isTagged(FluidTags.WATER)) {
         float f = 1.0F;
         f = 0.05F;
         if (entity instanceof ClientPlayerEntity) {
            ClientPlayerEntity clientplayerentity = (ClientPlayerEntity)entity;
            f -= clientplayerentity.getWaterBrightness() * clientplayerentity.getWaterBrightness() * 0.03F;
            Biome biome = clientplayerentity.world.func_226691_t_(clientplayerentity.func_233580_cy_());
            if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) {
               f += 0.005F;
            }
         }

         RenderSystem.fogDensity(f);
         RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
      } else {
         float f2;
         float f3;
         if (fluidstate.isTagged(FluidTags.LAVA)) {
            if (entity instanceof LivingEntity && ((LivingEntity)entity).isPotionActive(Effects.FIRE_RESISTANCE)) {
               f2 = 0.0F;
               f3 = 3.0F;
            } else {
               f2 = 0.25F;
               f3 = 1.0F;
            }
         } else if (entity instanceof LivingEntity && ((LivingEntity)entity).isPotionActive(Effects.BLINDNESS)) {
            int i = ((LivingEntity)entity).getActivePotionEffect(Effects.BLINDNESS).getDuration();
            float f1 = MathHelper.lerp(Math.min(1.0F, (float)i / 20.0F), p_228372_2_, 5.0F);
            if (p_228372_1_ == FogRenderer.FogType.FOG_SKY) {
               f2 = 0.0F;
               f3 = f1 * 0.8F;
            } else {
               f2 = f1 * 0.25F;
               f3 = f1;
            }
         } else if (p_228372_3_) {
            f2 = p_228372_2_ * 0.05F;
            f3 = Math.min(p_228372_2_, 192.0F) * 0.5F;
         } else if (p_228372_1_ == FogRenderer.FogType.FOG_SKY) {
            f2 = 0.0F;
            f3 = p_228372_2_;
         } else {
            f2 = p_228372_2_ * 0.75F;
            f3 = p_228372_2_;
         }

         RenderSystem.fogStart(f2);
         RenderSystem.fogEnd(f3);
         RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
         RenderSystem.setupNvFogDistance();
         net.minecraftforge.client.ForgeHooksClient.onFogRender(p_228372_1_, p_228372_0_, partialTicks, f3);
      }

   }

   public static void func_228373_b_() {
      RenderSystem.fog(2918, red, green, blue, 1.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public static enum FogType {
      FOG_SKY,
      FOG_TERRAIN;
   }
}