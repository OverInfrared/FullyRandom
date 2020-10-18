package over.fullyrandom.mixin.core.common.item.crafting;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import over.fullyrandom.json.Recipes;

import java.util.HashMap;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {

    @Redirect(method = "apply", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;"))
    private HashMap<IRecipeType<?>, ImmutableMap.Builder<ResourceLocation, IRecipe<?>>> apply() {
        return Recipes.getMap();
    }

}
