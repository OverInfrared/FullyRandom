package net.minecraft.world.biome;

import com.google.common.hash.Hashing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeManager {
   private final BiomeManager.IBiomeReader field_226832_a_;
   private final long field_226833_b_;
   private final IBiomeMagnifier field_226834_c_;

   public BiomeManager(BiomeManager.IBiomeReader p_i225744_1_, long p_i225744_2_, IBiomeMagnifier p_i225744_4_) {
      this.field_226832_a_ = p_i225744_1_;
      this.field_226833_b_ = p_i225744_2_;
      this.field_226834_c_ = p_i225744_4_;
   }

   public static long func_235200_a_(long p_235200_0_) {
      return Hashing.sha256().hashLong(p_235200_0_).asLong();
   }

   public BiomeManager func_226835_a_(BiomeProvider p_226835_1_) {
      return new BiomeManager(p_226835_1_, this.field_226833_b_, this.field_226834_c_);
   }

   public Biome func_226836_a_(BlockPos p_226836_1_) {
      return this.field_226834_c_.func_225532_a_(this.field_226833_b_, p_226836_1_.getX(), p_226836_1_.getY(), p_226836_1_.getZ(), this.field_226832_a_);
   }

   @OnlyIn(Dist.CLIENT)
   public Biome func_235198_a_(double p_235198_1_, double p_235198_3_, double p_235198_5_) {
      int i = MathHelper.floor(p_235198_1_) >> 2;
      int j = MathHelper.floor(p_235198_3_) >> 2;
      int k = MathHelper.floor(p_235198_5_) >> 2;
      return this.func_235199_a_(i, j, k);
   }

   @OnlyIn(Dist.CLIENT)
   public Biome func_235201_b_(BlockPos p_235201_1_) {
      int i = p_235201_1_.getX() >> 2;
      int j = p_235201_1_.getY() >> 2;
      int k = p_235201_1_.getZ() >> 2;
      return this.func_235199_a_(i, j, k);
   }

   @OnlyIn(Dist.CLIENT)
   public Biome func_235199_a_(int p_235199_1_, int p_235199_2_, int p_235199_3_) {
      return this.field_226832_a_.func_225526_b_(p_235199_1_, p_235199_2_, p_235199_3_);
   }

   public interface IBiomeReader {
      Biome func_225526_b_(int p_225526_1_, int p_225526_2_, int p_225526_3_);
   }
}