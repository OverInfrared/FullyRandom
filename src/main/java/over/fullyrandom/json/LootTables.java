package over.fullyrandom.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;
import over.fullyrandom.config.MainConfig;

public class LootTables {

    private static ImmutableMap.Builder<ResourceLocation, LootTable> builder;
    private static Gson GSON_INSTANCE;
    private static net.minecraft.resources.IResource res;
    private static LootTableManager lootTableManager;

    public LootTables(ImmutableMap.Builder<ResourceLocation, LootTable> builder, Gson GSON_INSTANCE, net.minecraft.resources.IResource res, LootTableManager lootTableManager) {
        LootTables.builder = builder;
        LootTables.GSON_INSTANCE = GSON_INSTANCE;
        LootTables.res = res;
        LootTables.lootTableManager = lootTableManager;

        createLootTables();
    }

    public static void createLootTables() {
        OreLootTables.createOreLootTables();
    }

    private static class OreLootTables{

        private static void createOreLootTables() {
            for (int i = 0; i< MainConfig.oreAmount.get(); i++) {
                ResourceLocation resourceLocation = new ResourceLocation("fullyrandom:blocks/r_ore" + i);
                LootTable loottable = net.minecraftforge.common.ForgeHooks.loadLootTable(GSON_INSTANCE, resourceLocation, createOreJson(i), res == null || !res.getPackName().equals("Default"), lootTableManager);
                builder.put(resourceLocation, loottable);
            }
        }

        private static JsonElement createOreJson(int ore) {

            JsonObject main = new JsonObject();
            JsonArray pools = new JsonArray();
            JsonObject sub = new JsonObject();
            JsonArray entries = new JsonArray();
            JsonObject sub2 = new JsonObject();
            JsonArray conditions = new JsonArray();
            JsonObject sub3 = new JsonObject();

            if (Randomizer.blockProperties.getOreType(ore)) {

                sub2.addProperty("type", "minecraft:item");
                sub2.addProperty("name", "fullyrandom:r_ore" + ore);

                entries.add(sub2);

                sub3.addProperty("condition", "minecraft:survives_explosion");
                conditions.add(sub3);

                sub.addProperty("name", "pool1");
                sub.addProperty("rolls", 1);
                sub.add("entries", entries);
                sub.add("conditions", conditions);

            } else {

                if (Randomizer.blockProperties.getOreResource(ore)) {

                    JsonArray children = new JsonArray();
                    JsonObject sub4 = new JsonObject();
                    JsonObject predicate = new JsonObject();
                    JsonArray enchantments = new JsonArray();
                    JsonObject sub5 = new JsonObject();
                    JsonObject levels = new JsonObject();
                    JsonObject sub6 = new JsonObject();
                    JsonArray functions = new JsonArray();
                    JsonObject sub7 = new JsonObject();
                    JsonObject sub8 = new JsonObject();

                    levels.addProperty("min", 1);

                    sub5.addProperty("enchantment", "minecraft:silk_touch");
                    sub5.add("levels", levels);
                    enchantments.add(sub5);

                    predicate.add("enchantments", enchantments);

                    sub4.addProperty("condition", "minecraft:match_tool");
                    sub4.add("predicate", predicate);
                    conditions.add(sub4);

                    sub3.addProperty("type", "minecraft:item");
                    sub3.add("conditions", conditions);
                    sub3.addProperty("name", "fullyrandom:r_ore" + ore);
                    children.add(sub3);

                    sub7.addProperty("function", "minecraft:apply_bonus");
                    sub7.addProperty("enchantment", "minecraft:fortune");
                    sub7.addProperty("formula", "minecraft:ore_drops");
                    functions.add(sub7);

                    sub8.addProperty("function", "minecraft:explosion_decay");
                    functions.add(sub8);

                    sub6.addProperty("type", "minecraft:item");
                    sub6.add("functions", functions);
                    sub6.addProperty("name", "fullyrandom:r_ore" + ore);
                    children.add(sub6);

                    sub2.addProperty("type", "minecraft:alternatives");
                    sub2.add("children", children);
                    entries.add(sub2);

                    sub.addProperty("rolls", 1);
                    sub.add("entries", entries);

                } else {

                    JsonArray children = new JsonArray();
                    JsonObject sub4 = new JsonObject();
                    JsonObject predicate = new JsonObject();
                    JsonArray enchantments = new JsonArray();
                    JsonObject sub5 = new JsonObject();
                    JsonObject levels = new JsonObject();
                    JsonObject sub6 = new JsonObject();
                    JsonArray functions = new JsonArray();
                    JsonObject sub7 = new JsonObject();
                    JsonObject count = new JsonObject();
                    JsonObject sub8 = new JsonObject();
                    JsonObject sub9 = new JsonObject();

                    levels.addProperty("min", 1);

                    sub5.addProperty("enchantment", "minecraft:silk_touch");
                    sub5.add("levels", levels);
                    enchantments.add(sub5);

                    predicate.add("enchantments", enchantments);

                    sub4.addProperty("condition", "minecraft:match_tool");
                    sub4.add("predicate", predicate);
                    conditions.add(sub4);

                    sub3.addProperty("type", "minecraft:item");
                    sub3.add("conditions", conditions);
                    sub3.addProperty("name", "fullyrandom:r_ore" + ore);
                    children.add(sub3);

                    count.addProperty("min", 4.0);
                    count.addProperty("max", 9.0);
                    count.addProperty("type", "minecraft:uniform");

                    sub7.addProperty("function", "minecraft:set_count");
                    sub7.add("count", count);
                    functions.add(sub7);

                    sub8.addProperty("function", "minecraft:apply_bonus");
                    sub8.addProperty("enchantment", "minecraft:fortune");
                    sub8.addProperty("formula", "minecraft:ore_drops");
                    functions.add(sub8);

                    sub9.addProperty("function", "minecraft:explosion_decay");
                    functions.add(sub9);

                    sub6.addProperty("type", "minecraft:item");
                    sub6.add("functions", functions);
                    sub6.addProperty("name", "fullyrandom:r_ore" + ore);
                    children.add(sub6);

                    sub2.addProperty("type", "minecraft:alternatives");
                    sub2.add("children", children);
                    entries.add(sub2);

                    sub.addProperty("rolls", 1);
                    sub.add("entries", entries);

                }

            }
            pools.add(sub);
            main.addProperty("type", "minecraft:block");
            main.add("pools", pools);

            return main;

        }

    }


}
