package over.fullyrandom.json;

import com.google.gson.JsonObject;
import over.fullyrandom.Randomizer;
import over.fullyrandom.config.MainConfig;

import java.util.Random;

public class Languages {

    private static final int amount = MainConfig.oreAmount.get();

    public static JsonObject createLanguageFile() {

        JsonObject languageFile = new JsonObject();

        languageFile.addProperty("itemGroup.fullyrandom", "FullyRandom Mod");
        languageFile.addProperty("generator.fullyrandom", "FullyRandom");
        OreLanguage.ore(languageFile);

        return languageFile;

    }

    private static class OreLanguage {

        private static void ore(JsonObject languageFile) {
            for (int i = 0; i<amount; i++) {
                String name = getOreName(i);
                oreBlocks(languageFile, name, i);
            }
        }

        private static void oreBlocks(JsonObject languageFile, String name, int index) {
            languageFile.addProperty("block.fullyrandom.r_ore" + index, name + " Ore");
            languageFile.addProperty("item.fullyrandom.r_oredrop" + index, name + Randomizer.blockProperties.getDrop(index).title);
            languageFile.addProperty("item.fullyrandom.r_sword" + index, name + " Sword");
            languageFile.addProperty("item.fullyrandom.r_pickaxe" + index, name + " Pickaxe");
            languageFile.addProperty("item.fullyrandom.r_shovel" + index, name + " Shovel");
            languageFile.addProperty("item.fullyrandom.r_axe" + index, name + " Axe");
            languageFile.addProperty("item.fullyrandom.r_hoe" + index, name + " Hoe");
            languageFile.addProperty("item.fullyrandom.r_nugget" + index, name + " Nugget");
            languageFile.addProperty("item.fullyrandom.r_helmet" + index, name + " Helmet");
            languageFile.addProperty("item.fullyrandom.r_chestplate" + index, name + " Chestplate");
            languageFile.addProperty("item.fullyrandom.r_leggings" + index, name + " Leggings");
            languageFile.addProperty("item.fullyrandom.r_boots" + index, name + " Boots");
        }

        private static String consonants = "bcdfghjklmnpqrstvwxyz";
        private static String vowels = "aeiou";
        private static String[] suffixes = {"mond", "ld", "on", "is", "tz", "stone", "al", "ite", "mite", "tine", "cium", "lium", "ian", "n", "thyst", "lite", "yl", "cite", "pper", "er", "co", "ium", "ma", "balt", "nc", "ad", "ury", "ckel", "ver", "in"};

        private static String getOreName(int index) {

            Random random0 = new Random(Randomizer.getSeed(index, 1));
            Random random1 = new Random(Randomizer.getSeed(index, 2));
            Random random2 = new Random(Randomizer.getSeed(index, 3));

            int letter0 = random0.nextInt(consonants.length());
            int letter1 = random1.nextInt(consonants.length());
            int letter4 = random2.nextInt(consonants.length());
            int letter2 = random0.nextInt(vowels.length());
            int letter3 = random1.nextInt(vowels.length());
            int suffix = random0.nextInt(suffixes.length);

            if (random2.nextBoolean()) {
                if (random0.nextBoolean()) {
                    if (random1.nextBoolean()) {
                        return consonants.substring(letter0, letter0 + 1).toUpperCase() + consonants.charAt(letter1) + vowels.charAt(letter2) + vowels.charAt(letter3) + suffixes[suffix];
                    }
                    return consonants.substring(letter0, letter0 + 1).toUpperCase() + consonants.charAt(letter1) + vowels.charAt(letter2) + suffixes[suffix];
                } else {
                    if (random1.nextBoolean()) {
                        return vowels.substring(letter2, letter2 + 1).toUpperCase() + consonants.charAt(letter0) + vowels.charAt(letter3) + suffixes[suffix];
                    }
                    return consonants.substring(letter0, letter0 + 1).toUpperCase() + vowels.charAt(letter2) + suffixes[suffix];
                }
            } else {
                if (random0.nextBoolean()){
                    if (random1.nextBoolean()) {
                        return consonants.substring(letter0, letter0 + 1).toUpperCase() + vowels.charAt(letter2) + vowels.charAt(letter3) + suffixes[suffix];
                    }
                    return consonants.substring(letter0, letter0 + 1).toUpperCase() + vowels.charAt(letter2) + consonants.charAt(letter1) + vowels.charAt(letter3) + suffixes[suffix];
                } else {
                    if (random1.nextBoolean()) {
                        return vowels.substring(letter2, letter2 + 1).toUpperCase() + consonants.charAt(letter0) + consonants.charAt(letter1) + vowels.charAt(letter3) + suffixes[suffix];
                    }
                    return consonants.substring(letter0, letter0 + 1).toUpperCase() + consonants.charAt(letter1) + consonants.charAt(letter4) + vowels.charAt(letter2) + suffixes[suffix];
                }
            }

        }

    }

}
