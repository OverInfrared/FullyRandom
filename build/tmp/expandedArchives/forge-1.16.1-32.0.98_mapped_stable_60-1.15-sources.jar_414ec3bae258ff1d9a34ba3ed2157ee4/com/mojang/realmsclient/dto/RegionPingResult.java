package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import net.minecraft.realms.IPersistentSerializable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RegionPingResult extends ValueObject implements IPersistentSerializable {
   @SerializedName("regionName")
   private final String field_230632_a_;
   @SerializedName("ping")
   private final int field_230633_b_;

   public RegionPingResult(String p_i51641_1_, int p_i51641_2_) {
      this.field_230632_a_ = p_i51641_1_;
      this.field_230633_b_ = p_i51641_2_;
   }

   public int func_230792_a_() {
      return this.field_230633_b_;
   }

   public String toString() {
      return String.format(Locale.ROOT, "%s --> %.2f ms", this.field_230632_a_, (float)this.field_230633_b_);
   }
}