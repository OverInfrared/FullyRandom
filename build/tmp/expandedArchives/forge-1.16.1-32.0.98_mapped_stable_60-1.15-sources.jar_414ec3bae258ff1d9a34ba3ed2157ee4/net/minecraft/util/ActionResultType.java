package net.minecraft.util;

public enum ActionResultType {
   SUCCESS,
   CONSUME,
   PASS,
   FAIL;

   public boolean func_226246_a_() {
      return this == SUCCESS || this == CONSUME;
   }

   public boolean func_226247_b_() {
      return this == SUCCESS;
   }

   public static ActionResultType func_233537_a_(boolean p_233537_0_) {
      return p_233537_0_ ? SUCCESS : CONSUME;
   }
}