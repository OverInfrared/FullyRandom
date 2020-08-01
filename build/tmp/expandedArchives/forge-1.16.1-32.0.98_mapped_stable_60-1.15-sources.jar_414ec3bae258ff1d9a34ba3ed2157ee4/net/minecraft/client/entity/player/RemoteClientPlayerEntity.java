package net.minecraft.client.entity.player;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RemoteClientPlayerEntity extends AbstractClientPlayerEntity {
   public RemoteClientPlayerEntity(ClientWorld p_i50989_1_, GameProfile p_i50989_2_) {
      super(p_i50989_1_, p_i50989_2_);
      this.stepHeight = 1.0F;
      this.noClip = true;
   }

   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * getRenderDistanceWeight();
      return distance < d0 * d0;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      net.minecraftforge.common.ForgeHooks.onPlayerAttack(this, source, amount);
      return true;
   }

   public void tick() {
      super.tick();
      this.func_233629_a_(this, false);
   }

   public void livingTick() {
      if (this.newPosRotationIncrements > 0) {
         double d0 = this.func_226277_ct_() + (this.interpTargetX - this.func_226277_ct_()) / (double)this.newPosRotationIncrements;
         double d1 = this.func_226278_cu_() + (this.interpTargetY - this.func_226278_cu_()) / (double)this.newPosRotationIncrements;
         double d2 = this.func_226281_cx_() + (this.interpTargetZ - this.func_226281_cx_()) / (double)this.newPosRotationIncrements;
         this.rotationYaw = (float)((double)this.rotationYaw + MathHelper.wrapDegrees(this.interpTargetYaw - (double)this.rotationYaw) / (double)this.newPosRotationIncrements);
         this.rotationPitch = (float)((double)this.rotationPitch + (this.interpTargetPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
         --this.newPosRotationIncrements;
         this.setPosition(d0, d1, d2);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      }

      if (this.interpTicksHead > 0) {
         this.rotationYawHead = (float)((double)this.rotationYawHead + MathHelper.wrapDegrees(this.interpTargetHeadYaw - (double)this.rotationYawHead) / (double)this.interpTicksHead);
         --this.interpTicksHead;
      }

      this.prevCameraYaw = this.cameraYaw;
      this.updateArmSwingProgress();
      float f1;
      if (this.onGround && !this.func_233643_dh_()) {
         f1 = Math.min(0.1F, MathHelper.sqrt(horizontalMag(this.getMotion())));
      } else {
         f1 = 0.0F;
      }

      if (!this.onGround && !this.func_233643_dh_()) {
         float f2 = (float)Math.atan(-this.getMotion().y * (double)0.2F) * 15.0F;
      } else {
         float f = 0.0F;
      }

      this.cameraYaw += (f1 - this.cameraYaw) * 0.4F;
      this.world.getProfiler().startSection("push");
      this.collideWithNearbyEntities();
      this.world.getProfiler().endSection();
   }

   protected void updatePose() {
   }

   public void sendMessage(ITextComponent component, UUID p_145747_2_) {
      Minecraft minecraft = Minecraft.getInstance();
      if (!minecraft.func_238198_a_(p_145747_2_)) {
         minecraft.ingameGUI.getChatGUI().printChatMessage(component);
      }

   }
}