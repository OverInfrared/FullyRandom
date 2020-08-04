package over.fullyrandom.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import over.fullyrandom.Fullyrandom;
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
                ResourceLocation resourceLocation = new ResourceLocation("r_ore_smelting" + i);
                try {
                    IRecipe<?> irecipesmelt = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(createOreSmeltJson(i), "top element"));
                    map.computeIfAbsent(irecipesmelt.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocation, irecipesmelt);
                    IRecipe<?> irecipeblast = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(createOreBlastJson(i), "top element"));
                    map.computeIfAbsent(irecipeblast.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourceLocation, irecipeblast);
                } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                    Fullyrandom.LOGGER.error("Parsing error loading recipe {}", resourceLocation, jsonparseexception);
                }
            }
        }

        private static JsonElement createOreSmeltJson(int ore) {
            JsonObject main = new JsonObject();
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", "fullyrandom:r_ore" + ore);
            main.addProperty("type", "minecraft:smelting");
            main.add("ingredient", ingredient);
            main.addProperty("result", "fullyrandom:r_oredrop" + ore);
            main.addProperty("experience", 0.7);
            return main;
        }

        private static JsonElement createOreBlastJson(int ore) {
            JsonObject main = new JsonObject();
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", "fullyrandom:r_ore" + ore);
            main.addProperty("type", "minecraft:blasting");
            main.add("ingredient", ingredient);
            main.addProperty("result", "fullyrandom:r_oredrop" + ore);
            main.addProperty("experience", 0.7);
            return main;
        }

    }

}
