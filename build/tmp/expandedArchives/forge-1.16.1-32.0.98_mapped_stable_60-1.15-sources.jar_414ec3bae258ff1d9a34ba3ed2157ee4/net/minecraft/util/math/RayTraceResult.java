package net.minecraft.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

public abstract class RayTraceResult {
   protected final Vector3d hitResult;
   /** Used to determine what sub-segment is hit */
   public int subHit = -1;

   /** Used to add extra hit info */
   public Object hitInfo = null;

   protected RayTraceResult(Vector3d hitVec) {
      this.hitResult = hitVec;
   }

   public double func_237486_a_(Entity p_237486_1_) {
      double d0 = this.hitResult.x - p_237486_1_.func_226277_ct_();
      double d1 = this.hitResult.y - p_237486_1_.func_226278_cu_();
      double d2 = this.hitResult.z - p_237486_1_.func_226281_cx_();
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public abstract RayTraceResult.Type getType();

   public Vector3d getHitVec() {
      return this.hitResult;
   }

   public static enum Type {
      MISS,
      BLOCK,
      ENTITY;
   }
}