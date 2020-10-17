package over.fullyrandom.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.util.unsafe.ReflectionTools;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings( "deprecation" )
public class OreGeneration {

    public static void setupOreFeatures() {
        enableBiomeMods();
        if (MainConfig.removeDefaultGeneration.get()) {
            disableOverworldGenerators();
            disableNetherGenerators();
        }
        if (MainConfig.generateOre.get())
            registerGenerators();
    }

    private static void enableBiomeMods() {
        final Field features = ReflectionTools.getField(BiomeGenerationSettings.class, "field_242484_f", 6);
        ReflectionTools.setMutable(features);
        synchronized (WorldGenRegistries.BIOME) {
            updateAllFeatures(features);
        }
        Fullyrandom.LOGGER.info("Successfully enabled biome feature mods.");
    }

    private static void updateAllFeatures(Field features) {
        for (Biome b : WorldGenRegistries.BIOME) {
            final BiomeGenerationSettings settings = b.getGenerationSettings();
            final List<List<Supplier<ConfiguredFeature<?, ?>>>> current = ReflectionTools.getValue(features, settings);
            final List<List<Supplier<ConfiguredFeature<?, ?>>>> values = synchronizedLinkedList();
            current.forEach(list -> values.add(synchronizedLinkedList(list)));
            ReflectionTools.setValue(features, settings, values);
        }
    }

    private static <T> List<T> synchronizedLinkedList() {
        return Collections.synchronizedList(new LinkedList<>());
    }

    private static <T> List<T> synchronizedLinkedList(Collection<T> copyOf) {
        return Collections.synchronizedList(new LinkedList<>(copyOf));
    }

    private static final int UNDERGROUND_ORES = GenerationStage.Decoration.UNDERGROUND_ORES.ordinal();
    private static void disableOverworldGenerators() {
        for (Biome b : WorldGenRegistries.BIOME) {
            final List<List<Supplier<ConfiguredFeature<?, ?>>>> features = b.getGenerationSettings().getFeatures();
            if (features.size() < UNDERGROUND_ORES) {
                continue;
            }
            final List<Supplier<ConfiguredFeature<?, ?>>> ores = features.get(UNDERGROUND_ORES);
            final List<Supplier<ConfiguredFeature<?, ?>>> drain = new ArrayList<>();
            ores.forEach(ore ->
                    findOreConfig(ore.get()).ifPresent(config -> {
                        if (shouldDisable(config.state)) {
                            Fullyrandom.LOGGER.info("Removing {} from generation in {}.", config.state, b);
                            drain.add(ore);
                        }
                    })
            );
            ores.removeAll(drain);
        }
    }

    private static final int DECORATED_FEATURES = GenerationStage.Decoration.UNDERGROUND_DECORATION.ordinal();
    private static void disableNetherGenerators() {
        for (Biome b : WorldGenRegistries.BIOME) {
            final List<List<Supplier<ConfiguredFeature<?, ?>>>> features = b.getGenerationSettings().getFeatures();
            if (features.size() < DECORATED_FEATURES) {
                continue;
            }
            final List<Supplier<ConfiguredFeature<?, ?>>> netherores = features.get(DECORATED_FEATURES);
            final List<Supplier<ConfiguredFeature<?, ?>>> drain = new ArrayList<>();
            netherores.forEach(ore ->
                    findOreConfig(ore.get()).ifPresent(config -> {
                        if (shouldDisable(config.state)) {
                            Fullyrandom.LOGGER.info("Removing {} from generation in {}.", config.state, b);
                            drain.add(ore);
                        }
                    })
            );
            netherores.removeAll(drain);
        }
    }

    private static Optional<OreFeatureConfig> findOreConfig(ConfiguredFeature<?, ?> feature) {
        return feature.config.func_241856_an_()
                .filter(nested -> nested.config instanceof OreFeatureConfig)
                .findFirst()
                .map(nested -> (OreFeatureConfig) nested.config);
    }

    private static boolean shouldDisable(BlockState state) {
        return !isStoneGen(state.getBlock());
    }

    private static boolean isStoneGen(Block block) {
        return block == Blocks.STONE
                || block == Blocks.ANDESITE
                || block == Blocks.DIORITE
                || block == Blocks.GRANITE
                || block == Blocks.DIRT
                || block == Blocks.GRAVEL
                || block == Blocks.MAGMA_BLOCK
                || block == Blocks.INFESTED_STONE
                || block == Blocks.BLACKSTONE;
    }

    private static void registerGenerators() {
        for (int i = 0; i < MainConfig.oreAmount.get(); i++) {
            OreFeatureConfig featureConfig = new OreFeatureConfig(Randomizer.blockProperties.getFillerBlock(i), ((Block)ModBlocks.oreBlocks.get(i)).getDefaultState(), Randomizer.blockProperties.getOreSpawn(i));
            final ConfiguredFeature<?, ?> configured = createFeature(featureConfig, Objects.requireNonNull(((Block) ModBlocks.oreBlocks.get(i)).getRegistryName()).toString(), i);
            for(Biome b : WorldGenRegistries.BIOME) {
                Fullyrandom.LOGGER.info(configured);
                getOreFeatures(b).add(() -> configured);
            }
        }
    }

    private static List<Supplier<ConfiguredFeature<?, ?>>> getOreFeatures(Biome b) {
        final List<List<Supplier<ConfiguredFeature<?, ?>>>> features = b.getGenerationSettings().getFeatures();
        while (features.size() <= UNDERGROUND_ORES) {
            features.add(new LinkedList<>());
        }
        return features.get(UNDERGROUND_ORES);
    }

    private static ConfiguredFeature<?,?> createFeature(OreFeatureConfig featureConfig, String name, int id) {
        final ConfiguredFeature<?, ?> feature = Feature.ORE.withConfiguration(featureConfig).withPlacement(Placement.field_242907_l.configure(Randomizer.blockProperties.getSpawnPos(id)).func_242728_a().func_242731_b(new Random(Randomizer.getSeed(id, 1)).nextInt(MainConfig.veinSpread.get()) + 1));
        return registerRandomly(feature, name);
    }

    private static ConfiguredFeature<?, ?> registerRandomly(ConfiguredFeature<?, ?> feature, String name) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, feature);
    }


}
