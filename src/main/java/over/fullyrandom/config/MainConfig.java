package over.fullyrandom.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MainConfig {

    public static ForgeConfigSpec.LongValue seed;
    public static ForgeConfigSpec.IntValue oreAmount;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {

        server.comment("--- Main Config File ---");

        seed = server
                .comment("Seed for game, change this you change your random")
                .defineInRange("main.seed", 100, Long.MIN_VALUE, Long.MAX_VALUE);

        oreAmount = server
                .comment("Ore amount for game, change this for amount of random ore you want")
                .defineInRange("main.oreamount", 1, 1, Integer.MAX_VALUE / 2);
    }
}
