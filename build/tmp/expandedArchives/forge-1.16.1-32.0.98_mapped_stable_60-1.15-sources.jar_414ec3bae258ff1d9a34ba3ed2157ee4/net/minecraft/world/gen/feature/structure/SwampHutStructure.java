package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SwampHutStructure extends Structure<NoFeatureConfig> {
   private static final List<Biome.SpawnListEntry> field_202384_d = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.WITCH, 1, 1, 1));
   private static final List<Biome.SpawnListEntry> field_214559_aS = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.CAT, 1, 1, 1));

   public SwampHutStructure(Codec<NoFeatureConfig> p_i231998_1_) {
      super(p_i231998_1_);
   }

   public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
      return SwampHutStructure.Start::new;
   }

   public List<Biome.SpawnListEntry> getSpawnList() {
      return field_202384_d;
   }

   public List<Biome.SpawnListEntry> getCreatureSpawnList() {
      return field_214559_aS;
   }

   public static class Start extends StructureStart<NoFeatureConfig> {
      public Start(Structure<NoFeatureConfig> p_i225819_1_, int p_i225819_2_, int p_i225819_3_, MutableBoundingBox p_i225819_4_, int p_i225819_5_, long p_i225819_6_) {
         super(p_i225819_1_, p_i225819_2_, p_i225819_3_, p_i225819_4_, p_i225819_5_, p_i225819_6_);
      }

      public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, NoFeatureConfig p_230364_6_) {
         SwampHutPiece swamphutpiece = new SwampHutPiece(this.rand, p_230364_3_ * 16, p_230364_4_ * 16);
         this.components.add(swamphutpiece);
         this.recalculateStructureSize();
      }
   }
}