package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BasaltDeltasBiome extends Biome {
   protected BasaltDeltasBiome() {
      super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.field_237191_af_, SurfaceBuilder.field_237187_R_).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(4341314).func_235239_a_(6840176).func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239820_at_, 0.118093334F)).func_235241_a_(SoundEvents.field_232698_c_).func_235243_a_(new MoodSoundAmbience(SoundEvents.field_232700_d_, 6000, 8, 2.0D)).func_235242_a_(new SoundAdditionsAmbience(SoundEvents.field_232692_b_, 0.0111D)).func_235240_a_(BackgroundMusicTracks.func_232677_a_(SoundEvents.field_232759_io_)).func_235238_a_()).parent((String)null).func_235098_a_(ImmutableList.of(new Biome.Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F))));
      this.func_235063_a_(DefaultBiomeFeatures.field_235133_E_);
      this.addCarver(GenerationStage.Carving.AIR, createCarver(WorldCarver.field_236240_b_, new ProbabilityConfig(0.2F)));
      this.func_235063_a_(DefaultBiomeFeatures.field_235177_o_);
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236286_Q_.func_225566_b_(DefaultBiomeFeatures.field_235144_aV_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(40))));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(DefaultBiomeFeatures.field_226737_Y_).func_227228_a_(Placement.COUNT_VERY_BIASED_RANGE.func_227446_a_(new CountRangeConfig(40, 8, 16, 256))));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236285_P_.func_225566_b_(DefaultBiomeFeatures.field_235140_aR_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(4))));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236285_P_.func_225566_b_(DefaultBiomeFeatures.field_235141_aS_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(2))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.func_225566_b_(DefaultBiomeFeatures.field_235142_aT_).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(75, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.func_225566_b_(DefaultBiomeFeatures.field_235143_aU_).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(25, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(DefaultBiomeFeatures.field_235137_aN_).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(16, 4, 8, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_235147_ap_).func_227228_a_(Placement.field_236960_A_.func_227446_a_(new FrequencyConfig(10))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_235148_aq_).func_227228_a_(Placement.field_236960_A_.func_227446_a_(new FrequencyConfig(10))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.GLOWSTONE_BLOB.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.LIGHT_GEM_CHANCE.func_227446_a_(new FrequencyConfig(10))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.GLOWSTONE_BLOB.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226722_J_).func_227228_a_(Placement.CHANCE_RANGE.func_227446_a_(new ChanceRangeConfig(0.5F, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226721_I_).func_227228_a_(Placement.CHANCE_RANGE.func_227446_a_(new ChanceRangeConfig(0.5F, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.MAGMA_BLOCK.getDefaultState(), 33)).func_227228_a_(Placement.MAGMA.func_227446_a_(new FrequencyConfig(4))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(DefaultBiomeFeatures.field_226766_aa_).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(32, 10, 20, 128))));
      DefaultBiomeFeatures.func_235190_a_(this, 20, 32);
      DefaultBiomeFeatures.func_235193_at_(this);
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.GHAST, 40, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 100, 2, 5));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.field_233589_aE_, 60, 1, 2));
   }
}