package over.fullyrandom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.common.ToolType;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;
import over.fullyrandom.items.RandomToolTier;

import java.util.ArrayList;
import java.util.Random;

public class Randomizer {

    public static long getSeed(long id) {
        return new Random(new Random(MainConfig.seed.get()).nextInt(Math.abs(new Random(id).nextInt())) * new Random(id).nextLong()).nextLong();
    }

    public static class blockProperties {

        static int getColor(int id) {
            return new Random(getSeed(id)).nextInt(0xffffff + 1);
        }

        // Overlays
        public static String getOverlay(int id) {
            Overlay[] overlays = Overlay.values();
            return overlays[new Random(getSeed(getSeed(id))).nextInt(overlays.length)].toString();
        }

        private enum Overlay {
            REGULAR,
            EMERALD,
            LAPIS,
            QUARTZ,
            GOLD
        }

        public static ArrayList<AppearsIn> material = new ArrayList<>();
        public static void getMaterial(int id) {
            Randomizer.blockProperties.AppearsIn[] mat = Randomizer.blockProperties.AppearsIn.values();
            material.add(mat[new Random(getSeed(id)).nextInt(mat.length)]);
        }

        public static AbstractBlock.Properties getProperties(int id) {
            return AbstractBlock.Properties.create(material.get(id).mat).sound(material.get(id).sound).hardnessAndResistance(material.get(id).hardness).harvestTool(material.get(id).tool).harvestLevel(new Random(getSeed(id)).nextInt(3 + 1)).setRequiresTool();
        }

        public enum AppearsIn {
            STONE(Material.ROCK, SoundType.STONE, 2.0f, ToolType.PICKAXE),
            GRAVEL(Material.EARTH, SoundType.GROUND, 0.5f, ToolType.SHOVEL),
            ENDSTONE(Material.ROCK, SoundType.STONE, 3.0f, ToolType.PICKAXE),
            SAND(Material.SAND, SoundType.SAND, 0.5f, ToolType.SHOVEL),
            NETHERRACK(Material.ROCK, SoundType.NETHER_ORE, 0.4f, ToolType.PICKAXE);

            public final Material mat;
            public final SoundType sound;
            public final float hardness;
            public final ToolType tool;

            AppearsIn(Material mat, SoundType sound, float hardness, ToolType tool) {
                this.mat = mat;
                this.sound = sound;
                this.hardness = hardness;
                this.tool = tool;
            }
        }

        public static boolean getOreType(int id) {
            return new Random(getSeed(id)).nextBoolean();
        }
        public static boolean getOreResource(int id) { return new Random(getSeed(getSeed(id))).nextBoolean(); }

        public static DropAppearance getDrop(int id) {
            DropAppearance drop[] = DropAppearance.values();
            if (getOreType(id)) {
                return DropAppearance.INGOT;
            } else {
                return drop[new Random(getSeed(id)).nextInt(drop.length - 1)];
            }
        }

        public enum DropAppearance {
            COAL( ""),
            DIAMOND( ""),
            EMERALD( ""),
            FLINT( ""),
            GHAST_TEAR( ""),
            LAPIS( ""),
            PHANTOM( " Membrane"),
            PRISMARINE_CRYSTAL( " Crystal"),
            QUARTZ( ""),
            REDSTONE( " Dust"),
            RUBY( ""),
            SLIME_BALL( ""),
            STRING( " Fiber"),
            INGOT(" Ingot");

            public final String title;

            DropAppearance(String title) { this.title = title; }
        }

        public static int getOreSpawn(int id) {
            return new Random(getSeed(id)).nextInt(15 + 1) + 1;
        }

        public static CountRangeConfig getSpawnPos(int index) {
            int max = 0;
            if (material.get(index).name().equals("STONE")) {
                max = new Random(getSeed(index)).nextInt(120 + 1) + 1;
            } else if (material.get(index).name().equals("GRAVEL")) {
                max = new Random(getSeed(index)).nextInt(126 + 1) + 1;
            } else if (material.get(index).name().equals("ENDSTONE")) {
                max = new Random(getSeed(index)).nextInt((70 - 10) + 1) + 10;
            } else if (material.get(index).name().equals("SAND")) {
                max = new Random(getSeed(index)).nextInt((110 - 60) + 1) + 60;
            } else if (material.get(index).name().equals("NETHERRACK")) {
                max = new Random(getSeed(index)).nextInt(128 + 1) + 1;
            }
            return new CountRangeConfig(new Random(getSeed(index)).nextInt(13 - 1) + 1, new Random(getSeed(index)).nextInt(30), 0, max);
        }

        public static boolean getTools(int index) {
            if (getOreType(index)) {
                return true;
            } else {
                return new Random(getSeed(getSeed(getSeed(index)))).nextBoolean();
            }
        }

        public static RandomToolTier getToolTier(int index, float mult) {
            float attackDamage;
            if (mult == 0) {
                attackDamage = 1;
            } else {
                attackDamage = (new Random(getSeed(index)).nextInt(9 + 1) + 1) * mult;
            }
            float efficiency = new Random(getSeed(index)).nextInt(14 + 1) + 1;
            int durability = new Random(getSeed(index)).nextInt(2018*2 + 1) + 1;
            int harvestLevel = new Random(getSeed(index)).nextInt(2 + 1) + 1;
            int enchantability = new Random(getSeed(index)).nextInt(25 + 1) + 1;
            return new RandomToolTier(attackDamage, efficiency, durability, harvestLevel, enchantability, ModItems.oreDrops[index]);
        }

    }

}
