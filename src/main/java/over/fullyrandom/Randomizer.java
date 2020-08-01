package over.fullyrandom;

import over.fullyrandom.config.MainConfig;

import java.util.Random;

public class Randomizer {

    public static int getSeed(int id) {
        return new Random(new Random(MainConfig.seed.get()).nextInt(Math.abs(new Random(id).nextInt())) * new Random(id).nextInt()).nextInt();
    }

}
