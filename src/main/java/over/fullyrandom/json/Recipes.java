package over.fullyrandom.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;
import over.fullyrandom.config.MainConfig;
import java.util.Map;

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
            }
        }
        // START OF FURNACE
        private static void createFurnaceRecipes(int index) {
            ResourceLocation resourceSmelting = new ResourceLocation("r_ore_smelting" + index);
            ResourceLocation resourceBlasting = new ResourceLocation("r_ore_blasting" + index);
            try {
                IRecipe<?> irecipesmelt = RecipeManager.deserializeRecipe(resourceSmelting, JSONUtils.getJsonObject(createFurnace(index, "smelting"), "top element"));
                map.computeIfAbsent(irecipesmelt.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceSmelting, irecipesmelt);
                IRecipe<?> irecipeblast = RecipeManager.deserializeRecipe(resourceBlasting, JSONUtils.getJsonObject(createFurnace(index, "blasting"), "top element"));
                map.computeIfAbsent(irecipeblast.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceBlasting, irecipeblast);
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                Fullyrandom.LOGGER.error("Parsing error loading recipe {}", resourceBlasting, jsonparseexception);
            }
        }

        private static JsonElement createFurnace(int ore, String type) {
            JsonObject main = new JsonObject();
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", "fullyrandom:r_ore" + ore);
            main.addProperty("type", "minecraft:" + type);
            main.add("ingredient", ingredient);
            main.addProperty("result", "fullyrandom:r_oredrop" + ore);
            main.addProperty("experience", 0.7);
            return main;
        }

        // END OF FURNACE

        // START OF TOOLS
        private static void createToolRecipes(int index) {
            ResourceLocation resourceSword = new ResourceLocation("r_ore_sword" + index);
            ResourceLocation resourcePickaxe = new ResourceLocation("r_ore_pickaxe" + index);
            ResourceLocation resourceShovel = new ResourceLocation("r_ore_shovel" + index);
            ResourceLocation resourceAxe = new ResourceLocation("r_ore_axe" + index);
            ResourceLocation resourceHoe = new ResourceLocation("r_ore_hoe" + index);

            if (Randomizer.blockProperties.getTools(index)) {
                try {
                    IRecipe<?> irecipesword = RecipeManager.deserializeRecipe(resourceSword, JSONUtils.getJsonObject(createTool(index, "r_sword", "X", "X", "#"), "top element"));
                    map.computeIfAbsent(irecipesword.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceSword, irecipesword);
                    IRecipe<?> irecipepickaxe = RecipeManager.deserializeRecipe(resourcePickaxe, JSONUtils.getJsonObject(createTool(index, "r_pickaxe", "XXX", " # ", " # "), "top element"));
                    map.computeIfAbsent(irecipepickaxe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourcePickaxe, irecipepickaxe);
                    IRecipe<?> irecipeshovel = RecipeManager.deserializeRecipe(resourceShovel, JSONUtils.getJsonObject(createTool(index, "r_shovel", "X", "#", "#"), "top element"));
                    map.computeIfAbsent(irecipeshovel.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceShovel, irecipeshovel);
                    IRecipe<?> irecipeaxe = RecipeManager.deserializeRecipe(resourceAxe, JSONUtils.getJsonObject(createTool(index, "r_axe", "XX", "X#", " #"), "top element"));
                    map.computeIfAbsent(irecipeaxe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceAxe, irecipeaxe);
                    IRecipe<?> irecipehoe = RecipeManager.deserializeRecipe(resourceHoe, JSONUtils.getJsonObject(createTool(index, "r_hoe", "XX", " #", " #"), "top element"));
                    map.computeIfAbsent(irecipehoe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceHoe, irecipehoe);
                } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                    Fullyrandom.LOGGER.error("Parsing error loading recipe {}", resourceHoe, jsonparseexception);
                }
            }
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

        private static void createArmorRecipes(int index) {
            ResourceLocation resourceHelmet = new ResourceLocation("r_ore_helmet" + index);
            ResourceLocation resourceChestplate = new ResourceLocation("r_ore_chestplate" + index);
            ResourceLocation resourceLeggings = new ResourceLocation("r_ore_leggings" + index);
            ResourceLocation resourceBoots = new ResourceLocation("r_ore_boots" + index);

            if (Randomizer.blockProperties.getArmor(index)) {
                try {
                    IRecipe<?> irecipesword = RecipeManager.deserializeRecipe(resourceHelmet, JSONUtils.getJsonObject(createArmor(index, "r_helmet", "XXX", "X X", ""), "top element"));
                    map.computeIfAbsent(irecipesword.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceHelmet, irecipesword);
                    IRecipe<?> irecipepickaxe = RecipeManager.deserializeRecipe(resourceChestplate, JSONUtils.getJsonObject(createArmor(index, "r_chestplate", "X X", "XXX", "XXX"), "top element"));
                    map.computeIfAbsent(irecipepickaxe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceChestplate, irecipepickaxe);
                    IRecipe<?> irecipeshovel = RecipeManager.deserializeRecipe(resourceLeggings, JSONUtils.getJsonObject(createArmor(index, "r_leggings", "XXX", "X X", "X X"), "top element"));
                    map.computeIfAbsent(irecipeshovel.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLeggings, irecipeshovel);
                    IRecipe<?> irecipeaxe = RecipeManager.deserializeRecipe(resourceBoots, JSONUtils.getJsonObject(createArmor(index, "r_boots", "X X", "X X", ""), "top element"));
                    map.computeIfAbsent(irecipeaxe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceBoots, irecipeaxe);
                } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                    Fullyrandom.LOGGER.error("Parsing error loading recipe {}", resourceHelmet, jsonparseexception);
                }
            }
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

    }

}
