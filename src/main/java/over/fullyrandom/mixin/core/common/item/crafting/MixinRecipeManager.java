package over.fullyrandom.mixin.core.common.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.json.Recipes;

import java.util.Map;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {

    @Shadow private Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes;
    @Shadow private boolean someRecipesErrored;

    /**
     * @author OverInfrared
     * @reason Creates and loads built in recipes.
     */
    @Overwrite
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        this.someRecipesErrored = false;
        Map<IRecipeType<?>, ImmutableMap.Builder<ResourceLocation, IRecipe<?>>> map = Maps.newHashMap();

        for(Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.

            if (!resourcelocation.toString().contains("fullyrandom")) {
                try {
                    if (entry.getValue().isJsonObject() && !net.minecraftforge.common.crafting.CraftingHelper.processConditions(entry.getValue().getAsJsonObject(), "conditions")) {
                        Fullyrandom.LOGGER.info("Skipping loading recipe {} as it's conditions were not met", resourcelocation);
                        continue;
                    }
                    IRecipe<?> irecipe = RecipeManager.deserializeRecipe(resourcelocation, JSONUtils.getJsonObject(entry.getValue(), "top element"));
                    if (irecipe == null) {
                        Fullyrandom.LOGGER.info("Skipping loading recipe {} as it's serializer returned null", resourcelocation);
                        continue;
                    }
                    map.computeIfAbsent(irecipe.getType(), (p_223391_0_) -> ImmutableMap.builder()).put(resourcelocation, irecipe);
                } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                    Fullyrandom.LOGGER.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
                }
            } else {
                new Recipes(map);
            }
        }

        this.recipes = map.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, (p_223400_0_) -> p_223400_0_.getValue().build()));
        Fullyrandom.LOGGER.info("Loaded {} recipes", (int)map.size());
    }

}
