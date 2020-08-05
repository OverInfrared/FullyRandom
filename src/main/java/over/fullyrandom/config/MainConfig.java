package over.fullyrandom.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MainConfig {

    public static ForgeConfigSpec.LongValue seed;
    public static ForgeConfigSpec.IntValue oreAmount;
    public static ForgeConfigSpec.IntValue[] oreProb = new ForgeConfigSpec.IntValue[4];

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {

        seed = server
                .comment("Seed")
                .defineInRange("main.seed", 100, Long.MIN_VALUE, Long.MAX_VALUE);

        oreAmount = server
                .comment("Amount of random ore.")
                .defineInRange("main.oreamount", 1, 1, Integer.MAX_VALUE / 6);

        oreProb[0] = server
//                .comment("These next values correspond to the 5 material types (stone, sand ...)")
//                .comment("each value is the probability of an ore dropping, so if each number was equal to 1")
//                .comment("then they would have an equal chance, if only one was equal to 5 and every other")
                .comment("was equal to 1 then that ore would have a 5 in 9 (55%) chance of that ore being that material")
                .defineInRange("main.stone", 3, 1, 100);
        oreProb[1] = server
                .defineInRange("main.gravel", 1, 1, 100);
        oreProb[2] = server
                .defineInRange("main.end_stone", 1, 1, 100);
        oreProb[3] = server
                .defineInRange("main.sand", 1, 1, 100);
        oreProb[4] = server
                .defineInRange("main.netherrack", 1, 1, 100);
    }
}
