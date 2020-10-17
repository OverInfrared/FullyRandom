package over.fullyrandom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.ToolType;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;
import over.fullyrandom.items.RandomArmorTier;
import over.fullyrandom.items.RandomToolTier;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Randomizer {

    public static long getSeed(long id, int repeat) {
        for (int i = 0; i<repeat; i++) {
            id = new Random(new Random(MainConfig.seed.get()).nextInt(Math.abs(new Random(id).nextInt())) * new Random(id).nextLong()).nextLong();
        }
        return id;
    }

    public static class blockProperties {

         public static int getColor(int id) {
            return new Random(getSeed(id, 1)).nextInt(0xffffff + 1);
        }

        // Overlays
        public static String getOverlay(int id) {
            Overlay[] overlays = Overlay.values();
            return overlays[new Random(getSeed(id, 2)).nextInt(overlays.length)].toString();
        }

        private enum Overlay {
            REGULAR,
            REGULARDIAMOND,
            EMERALD,
            LAPIS,
            QUARTZ,
            GOLD
        }

        private static final int num0 = MainConfig.stone.get() + MainConfig.gravel.get();
        private static final int num1 = num0 + MainConfig.endstone.get();
        private static final int num2 = num1 + MainConfig.sand.get();
        private static final int num3 = num2 + MainConfig.netherrack.get();

        public static ArrayList<AppearsIn> material = new ArrayList<>();
        public static void getMaterial(int id) {
            Randomizer.blockProperties.AppearsIn[] mat = Randomizer.blockProperties.AppearsIn.values();
            int pick = new Random(getSeed(id, 1)).nextInt(num3) + 1;
            if (pick <= MainConfig.stone.get()) {
                material.add(mat[0]);
            } else if (pick <= num0) {
                material.add(mat[1]);
            } else if (pick <= num1) {
                material.add(mat[2]);
            } else if (pick <= num2) {
                material.add(mat[3]);
            } else if (pick <= num3) {
                material.add(mat[4]);
            }
        }

        public static AbstractBlock.Properties getProperties(int id) {
            return AbstractBlock.Properties.create(material.get(id).mat).sound(material.get(id).sound).hardnessAndResistance(material.get(id).hardness).harvestTool(material.get(id).tool).harvestLevel(new Random(getSeed(id, 1)).nextInt(MainConfig.maxOreHarvestLevel.get())).setRequiresTool();
        }

        public enum AppearsIn {
            STONE(Material.ROCK, SoundType.STONE, 2.0f, ToolType.PICKAXE),
            GRAVEL(Material.EARTH, SoundType.GROUND, 0.5f, ToolType.SHOVEL),
            ENDSTONE(Material.ROCK, SoundType.STONE, 3.0f, ToolType.PICKAXE),
            SAND(Material.SAND, SoundType.SAND, 0.5f, ToolType.SHOVEL),
            NETHERRACK(Material.ROCK, SoundType.NETHER_GOLD, 0.4f, ToolType.PICKAXE);

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
            return new Random(getSeed(id, 1)).nextInt(100) + 1 <= MainConfig.ingotDrop.get();
        }
        public static boolean getOreResource(int id) {
            return new Random(getSeed(id, 2)).nextInt(100) + 1 <= MainConfig.dropAmount.get();
        }

        public static DropAppearance getDrop(int id) {
            DropAppearance[] drop = DropAppearance.values();
            return getOreType(id) ? DropAppearance.INGOT : drop[new Random(getSeed(id, 1)).nextInt(drop.length - 1)];
        }

        public static Item.Properties getItemProperties(int id) {
            return new Random(getSeed(id, 7)).nextInt(100) + 1 <= MainConfig.isFood.get() ?
                    new Random(getSeed(id, 8)).nextInt(100) + 1 <= MainConfig.hasFoodEffect.get() ?
                     new Item.Properties().group(Fullyrandom.setup.itemGroup).food(new Food.Builder().hunger(new Random(getSeed(id, 9)).nextInt(MainConfig.maxHunger.get())).saturation(new Random(getSeed(id, 10)).nextInt(MainConfig.maxSaturation.get())).build())
                    : new Item.Properties().group(Fullyrandom.setup.itemGroup).food(new Food.Builder().hunger(new Random(getSeed(id, 9)).nextInt(MainConfig.maxHunger.get())).saturation(new Random(getSeed(id, 10)).nextInt(MainConfig.maxSaturation.get())).effect(() -> getEffect(id), 1f).build())
                     : new Item.Properties().group(Fullyrandom.setup.itemGroup);
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
            return new Random(getSeed(id, 1)).nextInt(MainConfig.veinSize.get() + 1) + 1;
        }

        public static RuleTest getFillerBlock(int id) {
            switch (material.get(id).name()) {
                case "STONE":
                    return OreFeatureConfig.FillerBlockType.field_241882_a;
                case "GRAVEL":
                    return new BlockMatchRuleTest(Blocks.GRAVEL);
                case "ENDSTONE":
                    return new BlockMatchRuleTest(Blocks.END_STONE);
                case "SAND":
                    return new BlockMatchRuleTest(Blocks.SAND);
                case "NETHERRACK":
                    return OreFeatureConfig.FillerBlockType.field_241883_b;
            }
            return OreFeatureConfig.FillerBlockType.field_241882_a;
        }

        public static TopSolidRangeConfig getSpawnPos(int index) {
            int max = 0;
            switch (material.get(index).name()) {
                case "STONE":
                    max = new Random(getSeed(index, 1)).nextInt(MainConfig.stoneHeight.get() + 1) + 1; break;
                case "GRAVEL":
                    max = new Random(getSeed(index, 1)).nextInt(MainConfig.gravelHeight.get() + 1) + 1; break;
                case "ENDSTONE":
                    max = new Random(getSeed(index, 1)).nextInt((MainConfig.endstoneHeight.get() - MainConfig.endstoneBottomHeight.get()) + 1) + MainConfig.endstoneBottomHeight.get(); break;
                case "SAND":
                    max = new Random(getSeed(index, 1)).nextInt((MainConfig.sandHeight.get() - MainConfig.sandBottomHeight.get()) + 1) + MainConfig.sandBottomHeight.get(); break;
                case "NETHERRACK":
                    max = new Random(getSeed(index, 1)).nextInt(MainConfig.netherrack.get() + 1) + 1; break;
            }
            return new TopSolidRangeConfig(new Random(getSeed(index, 1)).nextInt(MainConfig.bottomOreOffset.get()), 0, max);
        }

        public static boolean getTools(int index) {
            return getOreType(index) || new Random(getSeed(index, 3)).nextInt(100) + 1 <= MainConfig.getsTools.get();
        }

        public static RandomToolTier getToolTier(int index, float mult) {
            float attackDamage = mult == 0 ? 1 : (new Random(getSeed(index, 1)).nextInt(MainConfig.maxAttackDamage.get()) + 1) * mult;
            float efficiency = new Random(getSeed(index, 2)).nextInt(MainConfig.maxEfficiency.get()) + 1;
            int durability = new Random(getSeed(index, 3)).nextInt(MainConfig.maxDurability.get()) + 1;
            int harvestLevel = new Random(getSeed(index, 4)).nextInt(MainConfig.maxHarvestLevel.get());
            int enchantability = new Random(getSeed(index, 5)).nextInt(MainConfig.maxEnchantability.get()) + 1;
            return new RandomToolTier(attackDamage, efficiency, durability, harvestLevel, enchantability, ModItems.oreDrops.get(index));
        }

        public static boolean getArmor(int index) {
            return getOreType(index) || new Random(getSeed(index, 4)).nextInt(100) + 1 <= MainConfig.getsArmor.get();
        }

        public static RandomArmorTier getArmorTier(int index, float durMult, float damMult) {
            String name = "armor" + index;
            int durability = (int) (((new Random(getSeed(index, 1)).nextInt(MainConfig.maxDurability.get()) + 1)/3d) * durMult);
            int damageReductionAmount = (int) ((new Random(getSeed(index, 2)).nextInt(MainConfig.maxDamageReduction.get())) * damMult);
            float toughness = new Random(getSeed(index, 3)).nextInt(MainConfig.maxToughness.get());
            int enchantability = new Random(getSeed(index, 4)).nextInt(MainConfig.maxEnchantability.get()) + 1;
            float knockbackResistence = (float) (Math.floor((new Random(getSeed(index, 5)).nextFloat()/(float)MainConfig.knockbackResistenceDivider.get()) * 10f) / 10f);
            return new RandomArmorTier(name, durability, damageReductionAmount, toughness, enchantability, "item.armor.equip_iron", ModItems.oreDrops.get(index), knockbackResistence);
        }

        public static boolean getIsFuel(int index) { return new Random(getSeed(index, 5)).nextInt(100) + 1 <= MainConfig.isFuel.get(); }
        public static int getFuelTime(int index) { return new Random(getSeed(index, 1)).nextInt(MainConfig.maxFuelTime.get()) + 1; }

        public static boolean getIsEffected(int index) { return new Random(getSeed(index, 6)).nextInt(100) + 1 <= MainConfig.hasPotionEffect.get(); }
        public static EffectInstance getEffect(int index) { return new EffectInstance(Objects.requireNonNull(Effect.get(new Random(getSeed(index, 1)).nextInt(30) + 1)), new Random(getSeed(index, 1)).nextInt(160) + 1, new Random(getSeed(index, 1)).nextInt(2) + 1); }

    }

}
