package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OverworldBiomeProvider extends BiomeProvider {
   public static final Codec<OverworldBiomeProvider> field_235297_e_ = RecordCodecBuilder.create((p_235302_0_) -> {
      return p_235302_0_.group(Codec.LONG.fieldOf("seed").stable().forGetter((p_235304_0_) -> {
         return p_235304_0_.field_235298_h_;
      }), Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", Boolean.valueOf(false), Lifecycle.stable()).forGetter((p_235303_0_) -> {
         return p_235303_0_.field_235299_i_;
      }), Codec.BOOL.fieldOf("large_biomes").withDefault(false).stable().forGetter((p_235301_0_) -> {
         return p_235301_0_.field_235300_j_;
      })).apply(p_235302_0_, p_235302_0_.stable(OverworldBiomeProvider::new));
   });
   private final Layer genBiomes;
   private static final List<Biome> field_226847_e_ = ImmutableList.of(Biomes.OCEAN, Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU);
   private final long field_235298_h_;
   private final boolean field_235299_i_;
   private final boolean field_235300_j_;

   public OverworldBiomeProvider(long p_i231643_1_, boolean p_i231643_3_, boolean p_i231643_4_) {
      super(field_226847_e_);
      this.field_235298_h_ = p_i231643_1_;
      this.field_235299_i_ = p_i231643_3_;
      this.field_235300_j_ = p_i231643_4_;
      this.genBiomes = LayerUtil.func_237215_a_(p_i231643_1_, p_i231643_3_, p_i231643_4_ ? 6 : 4, 4);
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235297_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return new OverworldBiomeProvider(p_230320_1_, this.field_235299_i_, this.field_235300_j_);
   }

   public Biome func_225526_b_(int p_225526_1_, int p_225526_2_, int p_225526_3_) {
      return this.genBiomes.func_215738_a(p_225526_1_, p_225526_3_);
   }
}