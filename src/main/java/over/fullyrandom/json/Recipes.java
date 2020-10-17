package over.fullyrandom.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;
import over.fullyrandom.util.unsafe.ReflectionTools;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static over.fullyrandom.util.CommonMethods.f;
import static over.fullyrandom.util.CommonMethods.getMax;

public class Recipes {

    private static Map<IRecipeType<?>, ImmutableMap.Builder<ResourceLocation, IRecipe<?>>> map;

    public Recipes(Map<IRecipeType<?>, ImmutableMap.Builder<ResourceLocation, IRecipe<?>>> map) {
        Recipes.map = map;
        OreRecipes.createOreRecipes();
    }

    private static class OreRecipes {

        private static void createOreRecipes() {
            for (int i = 0; i < MainConfig.oreAmount.get(); i++) {
                createFurnaceRecipes(i);
                createToolRecipes(i);
                createArmorRecipes(i);
                createOreRecipes(i);
                createShapelessRecipes(i);
            }
        }
        // START OF FURNACE
        private static void createFurnaceRecipes(int index) {
            try {
                createRecipes(index, "r_ore", "r_oredrop", 0.7);
                if (Randomizer.blockProperties.getOreType(index)) {
                    createRecipes(index, "r_sword", "r_nugget", 0.5);
                    createRecipes(index, "r_pickaxe", "r_nugget", 0.5);
                    createRecipes(index, "r_shovel", "r_nugget", 0.5);
                    createRecipes(index, "r_axe", "r_nugget", 0.5);
                    createRecipes(index, "r_hoe", "r_nugget", 0.5);
                    createRecipes(index, "r_helmet", "r_nugget", 0.5);
                    createRecipes(index, "r_chestplate", "r_nugget", 0.5);
                    createRecipes(index, "r_leggings", "r_nugget", 0.5);
                    createRecipes(index, "r_boots", "r_nugget", 0.5);
                }
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                Fullyrandom.LOGGER.error("Parsing error loading recipe {}", new ResourceLocation("r_ore_smelting0"), jsonparseexception);
            }
        }

        private static void createRecipes(int index, String item, String result, double xp) {
            ResourceLocation resourceLocationS = new ResourceLocation(item + "_" + "smelting" + index);
            IRecipe<?> irecipes = createFurnaceIRecipe(index, "smelting", item, result, xp, resourceLocationS); map.computeIfAbsent(irecipes.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocationS, irecipes);
            ResourceLocation resourceLocationB = new ResourceLocation(item + "_" + "blasting" + index);
            IRecipe<?> irecipeb = createFurnaceIRecipe(index, "blasting", item, result, xp, resourceLocationB); map.computeIfAbsent(irecipeb.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocationB, irecipeb);
        }

        private static IRecipe<?> createFurnaceIRecipe(int ore, String type, String item, String result, double xp, ResourceLocation resource) {
            return RecipeManager.deserializeRecipe(resource, JSONUtils.getJsonObject(createFurnace(ore, type, item, result, xp), "top element"));
        }

        private static JsonElement createFurnace(int ore, String type, String item, String result, double xp) {
            JsonObject main = new JsonObject();
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", "fullyrandom:" + item + ore);
            main.addProperty("type", "minecraft:" + type);
            main.add("ingredient", ingredient);
            main.addProperty("result", "fullyrandom:" + result + ore);
            main.addProperty("experience", xp);
            return main;
        }
        // END OF FURNACE

        // START OF TOOLS
        private static void createToolRecipes(int index) {
            try {
                if (Randomizer.blockProperties.getTools(index)) {
                    createToolIRecipes(index, "r_sword", "X", "X", "#");
                    createToolIRecipes(index, "r_pickaxe", "XXX", " # ", " # ");
                    createToolIRecipes(index, "r_shovel", "X", "#", "#");
                    createToolIRecipes(index, "r_axe", "XX", "X#", " #");
                    createToolIRecipes(index, "r_hoe", "XX", " #", " #");
                }
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
            Fullyrandom.LOGGER.error("Parsing error loading recipe {}", new ResourceLocation("lol"), jsonparseexception);
            }
        }

        private static void createToolIRecipes(int index, String item, String tier, String tier1, String tier2) {
            ResourceLocation resourceLocation = new ResourceLocation(item + index);
            IRecipe<?> irecipe = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(createTool(index, item, tier, tier1, tier2), "top element"));
            map.computeIfAbsent(irecipe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocation, irecipe);
        }

        private static JsonElement createTool(int ore, String type, String row1, String row2, String row3) {
            JsonObject main = new JsonObject();
            JsonArray pattern = new JsonArray();
            JsonObject key = new JsonObject();
            JsonObject sub = new JsonObject();
            JsonObject sub2 = new JsonObject();
            JsonObject result = new JsonObject();
            result.addProperty("item", "fullyrandom:" + type + ore);
            sub.addProperty("item", "fullyrandom:r_oredrop" + ore);
            sub2.addProperty("item", "minecraft:stick");
            key.add("X", sub);
            key.add("#", sub2);
            pattern.add(row1);
            pattern.add(row2);
            pattern.add(row3);
            main.addProperty("type", "minecraft:crafting_shaped");
            main.add("pattern", pattern);
            main.add("key", key);
            main.add("result", result);
            return main;
        }
        // END OF TOOLS

        // START OF ARMOR
        private static void createArmorRecipes(int index) {
            try {
                if (Randomizer.blockProperties.getArmor(index)) {
                    createArmorIRecipes(index, "r_helmet", "XXX", "X X", "");
                    createArmorIRecipes(index, "r_chestplate", "X X", "XXX", "XXX");
                    createArmorIRecipes(index, "r_leggings", "XXX", "X X", "X X");
                    createArmorIRecipes(index, "r_boots", "X X", "X X", "");
                }
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                Fullyrandom.LOGGER.error("Parsing error loading recipe {}", new ResourceLocation("lol"), jsonparseexception);
            }
        }

        private static void createArmorIRecipes(int index, String item, String tier, String tier1, String tier2) {
            ResourceLocation resourceLocation = new ResourceLocation(item + index);
            IRecipe<?> irecipe = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(createArmor(index, item, tier, tier1, tier2), "top element"));
            map.computeIfAbsent(irecipe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocation, irecipe);
        }

        private static JsonElement createArmor(int ore, String type, String row1, String row2, String row3) {
            JsonObject main = new JsonObject();
            JsonArray pattern = new JsonArray();
            JsonObject key = new JsonObject();
            JsonObject sub = new JsonObject();
            JsonObject result = new JsonObject();
            result.addProperty("item", "fullyrandom:" + type + ore);
            sub.addProperty("item", "fullyrandom:r_oredrop" + ore);
            key.add("X", sub);
            pattern.add(row1);
            pattern.add(row2);
            if (!row3.equals("")) {
                pattern.add(row3);
            }
            main.addProperty("type", "minecraft:crafting_shaped");
            main.add("pattern", pattern);
            main.add("key", key);
            main.add("result", result);
            return main;
        }
        // END OF ARMOR

        //SHAPED
        private static void createOreRecipes(int index) {
            try {
                if (Randomizer.blockProperties.getOreType(index)) {
                    createOreIRecipes(index, "r_oredrop", "r_nugget", "XXX", "XXX", "XXX");
                }
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                Fullyrandom.LOGGER.error("Parsing error loading recipe {}", new ResourceLocation("lol"), jsonparseexception);
            }
        }

        private static void createOreIRecipes(int index, String result, String ingredient, String tier, String tier1, String tier2) {
            ResourceLocation resourceLocation = new ResourceLocation("craft_" + result + index);
            IRecipe<?> irecipe = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(createOre(index, result, ingredient, tier, tier1, tier2), "top element"));
            map.computeIfAbsent(irecipe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocation, irecipe);
        }

        private static JsonElement createOre(int ore, String resultitem, String ingredient, String tier, String tier1, String tier2) {
            JsonObject main = new JsonObject();
            JsonArray pattern = new JsonArray();
            JsonObject key = new JsonObject();
            JsonObject sub = new JsonObject();
            JsonObject result = new JsonObject();
            result.addProperty("item", "fullyrandom:" + resultitem + ore);
            sub.addProperty("item", "fullyrandom:" + ingredient + ore);
            key.add("X", sub);
            if (!tier.equals("")) {
                pattern.add(tier);
            }
            if (!tier1.equals("")) {
                pattern.add(tier1);
            }
            if (!tier2.equals("")) {
                pattern.add(tier2);
            }
            main.addProperty("type", "minecraft:crafting_shaped");
            main.add("pattern", pattern);
            main.add("key", key);
            main.add("result", result);
            return main;
        }

        // SHAPELESS
        private static void createShapelessRecipes(int index) {
            try {
                String[] ingredients = new String[]{"r_oredrop" + index};
                if (Randomizer.blockProperties.getOreType(index)) {
                    createShapelessIRecipes(index, "r_nugget", 9, ingredients);
                }
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                Fullyrandom.LOGGER.error("Parsing error loading recipe {}", new ResourceLocation("lol"), jsonparseexception);
            }
        }

        private static void createShapelessIRecipes(int index, String result, int amount, String[] ingredients) {
            ResourceLocation resourceLocation = new ResourceLocation(result + index);
            IRecipe<?> irecipe = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(createShapeless(index, result, amount, ingredients), "top element"));
            map.computeIfAbsent(irecipe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocation, irecipe);
        }

        private static JsonElement createShapeless(int index, String result, int amount, String[] ingredientArray) {
            JsonObject main = new JsonObject();
            JsonArray ingredients = new JsonArray();
            JsonObject results = new JsonObject();
            results.addProperty("item", "fullyrandom:" + result + index);
            results.addProperty("count", amount);
            for (String ingred: ingredientArray) {
                JsonObject ingre = new JsonObject();
                ingre.addProperty("item", "fullyrandom:" + ingred);
                ingredients.add(ingre);
            }
            main.addProperty("type", "minecraft:crafting_shapeless");
            main.add("ingredients", ingredients);
            main.add("result", results);
            return main;
        }

    }

}
