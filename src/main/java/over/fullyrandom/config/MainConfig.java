package over.fullyrandom.config;

import com.electronwill.nightconfig.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class MainConfig {

    public static ForgeConfigSpec.LongValue seed;
    public static ForgeConfigSpec.IntValue oreAmount;

    public static ForgeConfigSpec.IntValue stone;
    public static ForgeConfigSpec.IntValue gravel;
    public static ForgeConfigSpec.IntValue endstone;
    public static ForgeConfigSpec.IntValue sand;
    public static ForgeConfigSpec.IntValue netherrack;
    public static ForgeConfigSpec.IntValue maxOreHarvestLevel;
    public static ForgeConfigSpec.IntValue ingotDrop;
    public static ForgeConfigSpec.IntValue dropAmount;
    public static ForgeConfigSpec.IntValue getsTools;
    public static ForgeConfigSpec.IntValue getsArmor;
    public static ForgeConfigSpec.IntValue isFuel;
    public static ForgeConfigSpec.IntValue maxFuelTime;
    public static ForgeConfigSpec.IntValue hasPotionEffect;
    public static ForgeConfigSpec.IntValue isFood;
    public static ForgeConfigSpec.IntValue hasFoodEffect;
    public static ForgeConfigSpec.IntValue maxHunger;
    public static ForgeConfigSpec.IntValue maxSaturation;
    public static ForgeConfigSpec.BooleanValue removeDefaultGeneration;
    public static ForgeConfigSpec.BooleanValue generateOre;
    public static ForgeConfigSpec.IntValue stoneHeight;
    public static ForgeConfigSpec.IntValue gravelHeight;
    public static ForgeConfigSpec.IntValue endstoneHeight;
    public static ForgeConfigSpec.IntValue endstoneBottomHeight;
    public static ForgeConfigSpec.IntValue sandHeight;
    public static ForgeConfigSpec.IntValue sandBottomHeight;
    public static ForgeConfigSpec.IntValue netherrackHeight;
    public static ForgeConfigSpec.IntValue bottomOreOffset;
    public static ForgeConfigSpec.IntValue veinSize;
    public static ForgeConfigSpec.IntValue veinSpread;

    public static ForgeConfigSpec.IntValue maxAttackDamage;
    public static ForgeConfigSpec.IntValue maxEfficiency;
    public static ForgeConfigSpec.IntValue maxDurability;
    public static ForgeConfigSpec.IntValue maxEnchantability;
    public static ForgeConfigSpec.IntValue maxDamageReduction;
    public static ForgeConfigSpec.IntValue maxToughness;
    public static ForgeConfigSpec.IntValue knockbackResistenceDivider;
    public static ForgeConfigSpec.IntValue maxHarvestLevel;

    public static ForgeConfigSpec.IntValue biomeAmount;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {

        Config.setInsertionOrderPreserved(true);

        //MAIN
        seed = server
                .comment("Seed")
                .defineInRange("main.seed", 100, Long.MIN_VALUE, Long.MAX_VALUE);

        oreAmount = server
                .comment("Amount of random ore.")
                .defineInRange("main.oreamount", 17, 1, Integer.MAX_VALUE / 6);
        // MAIN

        // ORE
        stone = server
                .comment("Ore probability works on an addition system of values; The game takes all of the probability values and adds them together.",
                        "Once all of the values are added the game picks a random number between 1 and the sum of the values. If the random number is",
                        "less than the first value (30 by default for stone) then the material is set to that corresponding material. If the pick number",
                        "is not less than the first value it takes the next value (gravel probability 10) and adds it to the last probability (30), if",
                        "the value is less then the new sum (40) then the material is the corresponding material (gravel). This goes on until all have been",
                        "checked and by then all materials have been found. So with default settings stone currently has a 42% chance of being a ores",
                        "material because its value is 30, and all other values add up to 40. These values are subject to change for balancing.",
                        "",
                        "Stone Material Probability")
                .defineInRange("ore.stone", 30, 1, 100);

        gravel = server
                .comment("Gravel Material Probability")
                .defineInRange("ore.gravel", 10, 1, 100);

        endstone = server
                .comment("Endstone Material Probability")
                .defineInRange("ore.endstone", 10, 1, 100);

        sand = server
                .comment("Sand Material Probability")
                .defineInRange("ore.sand", 10, 1, 100);

        netherrack = server
                .comment("Netherrack Material Probability")
                .defineInRange("ore.netherrack", 15, 1, 100);

        maxOreHarvestLevel = server
                .comment("Max ore harvest level.")
                .defineInRange("ore.maxharvestlevel", 5, 0, 100);

        ingotDrop = server
                .comment("",
                        "Picks a number between 1 - 100, if less then value drop is an ingot. (smelting)")
                .defineInRange("ore.ingotdropprobability", 40, 0, 100);

        dropAmount = server
                .comment("Picks a number between 1 - 100, if less then value drops one item (diamond), else drops multiple (lapis)")
                .defineInRange("ore.dropamountprobability", 50, 0, 100);

        getsTools = server
                .comment("Picks a number between 1 - 100, if less then value drop gets tools, but all ingot drops get tools")
                .defineInRange("ore.getstoolsprobability", 50, 0, 100);

        getsArmor = server
                .comment("Picks a number between 1 - 100, if less then value drop gets armor, but all ingot drops get armor")
                .defineInRange("ore.getsarmorprobability", 50, 0, 100);

        isFuel = server
                .comment("Picks a number between 1 - 100, if less then value drop is fuel")
                .defineInRange("ore.isfuelprobability", 50, 0, 100);

        maxFuelTime = server
                .comment("Max drop fuel time")
                .defineInRange("ore.maxfueltime", 3600, 1, Integer.MAX_VALUE - 1);

        hasPotionEffect = server
                .comment("Picks a number between 1 - 100, if less then value drop has postion effect.")
                .defineInRange("ore.potioneffectprobability", 30, 0, 100);

        isFood = server
                .comment("Picks a number between 1 - 100, if less then value drop is a food item")
                .defineInRange("ore.isfoodprobability", 10, 0, 100);

        hasFoodEffect = server
                .comment("Picks a number between 1 - 100, if less then value drop has a potion effect")
                .defineInRange("ore.hasfoodeffectprobability", 40, 0, 100);

        maxHunger = server
                .comment("Max drop hunger amount")
                .defineInRange("ore.maxhungeramount", 12, 0, Integer.MAX_VALUE - 1);

        maxSaturation = server
                .comment("Max drop saturation amount")
                .defineInRange("ore.maxsaturationamount", 5, 0, Integer.MAX_VALUE - 1);

        removeDefaultGeneration = server
                .comment("Removes default minecraft ores.")
                .define("ore.removeVanillaOres", true);

        generateOre = server
                .comment("Generate random ores into world.")
                .define("ore.generateRandomOres", true);

        stoneHeight = server
                .comment("Max stone ore height.")
                .defineInRange("ore.maxstoneheight", 120, 1, 255);

        gravelHeight = server
                .comment("Max gravel ore height.")
                .defineInRange("ore.maxgravelheight", 126, 1, 255);

        endstoneHeight = server
                .comment("Max endstone ore height.")
                .defineInRange("ore.maxendstoneheight", 70, 1, 255);

        endstoneBottomHeight = server
                .comment("Elevator for maxendstoneheight, must be a higher number.")
                .defineInRange("ore.endstonebottomheight", 10, 1, 255);

        sandHeight = server
                .comment("Max sand ore height.")
                .defineInRange("ore.maxsandheight", 110, 1, 255);

        sandBottomHeight = server
                .comment("Elevator for maxsandheight, must be a higher number.")
                .defineInRange("ore.sandbottomheight", 60, 1, 255);

        netherrackHeight = server
                .comment("Max netherrack ore height.")
                .defineInRange("ore.maxnetherrackheight", 128, 1, 255);

        bottomOreOffset = server
                .comment("Bottom ore offset, should be smaller than the ore max heights.")
                .defineInRange("ore.bottomoreoffset", 30, 1, 255);

        veinSize = server
                .comment("Max amount of ore in a vein.")
                .defineInRange("ore.veinsize", 15, 1, 100);

        veinSpread = server
                .comment("Max ores per chunk.")
                .defineInRange("ore.veinspread", 15, 1, 100);
        //ORE

        //TOOLS and ARMOR
        maxAttackDamage = server
                .comment("Max tool attack damage")
                .defineInRange("tool&armor.maxattackdamage", 14, 1, Integer.MAX_VALUE - 1);

        maxEfficiency = server
                .comment("Max tool efficiency")
                .defineInRange("tool&armor.maxefficiency", 20, 1, Integer.MAX_VALUE - 1);

        maxDurability = server
                .comment("Max tool/armor durability")
                .defineInRange("tool&armor.maxdurability", 4036, 1, Integer.MAX_VALUE - 1);

        maxEnchantability = server
                .comment("Max tool/armor enchantability")
                .defineInRange("tool&armor.maxenchantability", 30, 1, Integer.MAX_VALUE - 1);

        maxDamageReduction = server
                .comment("Max armor damage reduction (armor bars)")
                .defineInRange("tool&armor.maxdamagereduction", 13, 1, Integer.MAX_VALUE - 1);

        maxToughness = server
                .comment("Max armor toughness (like diamond armor)")
                .defineInRange("tool&armor.maxtoughness", 6, 1, Integer.MAX_VALUE - 1);

        knockbackResistenceDivider = server
                .comment("The game picks a random float number between 0.0 and 1.0 (0.1, 0.2, 0.3 . ..)",
                        "This number controls how high this number can get, I don't recommend changing this value")
                .defineInRange("tool&armor.knockbackresistencedivider", 4, 1, Integer.MAX_VALUE - 1);

        maxHarvestLevel = server
                .comment("Max tool harvest Level")
                .defineInRange("tool&armor.maxtoolharvestlevel", 5, 0, 100);
        //TOOLS and ARMOR

        //BIOMES
//        biomeAmount = server
//                .comment("Amount of random Biomes")
//                .defineInRange("biomes.biomeamount", 60, 1, Integer.MAX_VALUE - 1);
    }
}
