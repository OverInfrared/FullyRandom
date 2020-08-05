package over.fullyrandom.world;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import over.fullyrandom.Randomizer;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;

import java.util.Iterator;
import java.util.Objects;

public class OreGeneration {

    public static void removeVanillaOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            Iterator<ConfiguredFeature<?, ?>> it = biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).iterator();
            Iterator<ConfiguredFeature<?, ?>> itn = biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).iterator();
            while (it.hasNext()) {
                ConfiguredFeature<?, ?> configuredFeature = it.next();
                if (configuredFeature.config instanceof DecoratedFeatureConfig) {
                    configuredFeature = ((DecoratedFeatureConfig)configuredFeature.config).feature;
                }
                Feature<?> feature = configuredFeature.feature;
                if (feature instanceof OreFeature) {
                    final OreFeatureConfig config = (OreFeatureConfig)configuredFeature.config;
                    final Block ore = config.state.getBlock();
                    String[] names = {"minecraft:coal_ore", "minecraft:iron_ore", "minecraft:gold_ore", "minecraft:redstone_ore", "minecraft:diamond_ore", "minecraft:lapis_ore", "minecraft:nether_quartz_ore", "minecraft:nether_gold_ore", "minecraft:ancient_debris"};
                    for (String i : names) {
                        if (Objects.requireNonNull(ore.getRegistryName()).toString().equals(i)) {
                            it.remove();
                        }
                    }
                }
                if (feature instanceof ReplaceBlockFeature) {
                    it.remove();
                }
            }
            while (itn.hasNext()) {
                ConfiguredFeature<?, ?> configuredFeature = itn.next();
                if (configuredFeature.config instanceof DecoratedFeatureConfig) {
                    configuredFeature = ((DecoratedFeatureConfig)configuredFeature.config).feature;
                }
                Feature<?> feature = configuredFeature.feature;
                if (feature instanceof OreFeature || feature instanceof NoExposedOreFeature) {
                    final OreFeatureConfig config = (OreFeatureConfig)configuredFeature.config;
                    final Block ore = config.state.getBlock();
                    String[] names = {"minecraft:nether_quartz_ore", "minecraft:nether_gold_ore", "minecraft:ancient_debris"};
                    for (String i : names) {
                        if (Objects.requireNonNull(ore.getRegistryName()).toString().equals(i)) {
                            itn.remove();
                        }
                    }
                }
                if (feature instanceof ReplaceBlockFeature) {
                    itn.remove();
                }
            }
        }
    }

    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            for (int i = 0; i< MainConfig.oreAmount.get(); i++) {
                switch (Randomizer.blockProperties.material.get(i).name().toLowerCase()) {
                    case "stone":
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ((Block) ModBlocks.oreBlocks.get(i)).getDefaultState(), Randomizer.blockProperties.getOreSpawn(i))).withPlacement(Placement.COUNT_RANGE.configure(Randomizer.blockProperties.getSpawnPos(i))));
                        break;
                    case "gravel":
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.create("GRAVEL", "gravel", new BlockMatcher(Blocks.GRAVEL)), ((Block)ModBlocks.oreBlocks.get(i)).getDefaultState(), Randomizer.blockProperties.getOreSpawn(i))).withPlacement(Placement.COUNT_RANGE.configure(Randomizer.blockProperties.getSpawnPos(i))));
                        break;
                    case "sand":
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.create("SAND", "sand", new BlockMatcher(Blocks.SAND)), ((Block)ModBlocks.oreBlocks.get(i)).getDefaultState(), Randomizer.blockProperties.getOreSpawn(i))).withPlacement(Placement.COUNT_RANGE.configure(Randomizer.blockProperties.getSpawnPos(i))));
                        break;
                    case "netherrack":
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ((Block)ModBlocks.oreBlocks.get(i)).getDefaultState(), Randomizer.blockProperties.getOreSpawn(i))).withPlacement(Placement.COUNT_RANGE.configure(Randomizer.blockProperties.getSpawnPos(i))));
                        break;
                    case "end_stone":
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.create("ENDSTONE", "end_stone", new BlockMatcher(Blocks.END_STONE)), ((Block)ModBlocks.oreBlocks.get(i)).getDefaultState(), Randomizer.blockProperties.getOreSpawn(i))).withPlacement(Placement.COUNT_RANGE.configure(Randomizer.blockProperties.getSpawnPos(i))));
                        break;
                }
            }
        }
    }

}
