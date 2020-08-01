package net.minecraft.world.biome;

public enum DefaultBiomeMagnifier implements IBiomeMagnifier {
   INSTANCE;

   public Biome func_225532_a_(long p_225532_1_, int p_225532_3_, int p_225532_4_, int p_225532_5_, BiomeManager.IBiomeReader p_225532_6_) {
      return p_225532_6_.func_225526_b_(p_225532_3_ >> 2, p_225532_4_ >> 2, p_225532_5_ >> 2);
   }
}