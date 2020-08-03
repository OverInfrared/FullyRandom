package over.fullyrandom.mixin.core.common.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.*;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.*;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.config.MainConfig;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@Mixin(LootTableManager.class)
public class MixinLootTableManager extends JsonReloadListener {

    private static final Gson GSON_INSTANCE = LootSerializers.func_237388_c_().create();
    @Shadow private Map<ResourceLocation, LootTable> registeredLootTables;
    @Shadow @Final @Mutable private final LootPredicateManager lootPredicateManager;
    private final LootTableManager lootTableManager;

    public MixinLootTableManager(Gson p_i51536_1_, String p_i51536_2_, LootPredicateManager field_227507_d_, LootTableManager lootTableManager) {
        super(p_i51536_1_, p_i51536_2_);
        this.lootPredicateManager = field_227507_d_;
        this.lootTableManager = lootTableManager;

    }


    /**
     * @author OverInfrared
     */
    @ParametersAreNonnullByDefault
    @Overwrite
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        ImmutableMap.Builder<ResourceLocation, LootTable> builder = ImmutableMap.builder();
        JsonElement jsonelement = objectIn.remove(LootTables.EMPTY);
        if (jsonelement != null) {
            Fullyrandom.LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", (Object)LootTables.EMPTY);
        }

        objectIn.forEach((p_237403_1_, p_237403_2_) -> {
            try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(p_237403_1_));){

                if (!p_237403_1_.toString().contains("fullyrandom")) {
                    LootTable loottable = net.minecraftforge.common.ForgeHooks.loadLootTable(GSON_INSTANCE, p_237403_1_, p_237403_2_, res == null || !res.getPackName().equals("Default"), lootTableManager);
                    builder.put(p_237403_1_, loottable);
                } else {
                    new over.fullyrandom.json.LootTables(builder, GSON_INSTANCE, res, lootTableManager);
                }
            } catch (Exception exception) {
                Fullyrandom.LOGGER.error("Couldn't parse loot table {}", p_237403_1_, exception);
            }

        });
        builder.put(LootTables.EMPTY, LootTable.EMPTY_LOOT_TABLE);
        ImmutableMap<ResourceLocation, LootTable> immutablemap = builder.build();
        ValidationTracker validationtracker = new ValidationTracker(LootParameterSets.GENERIC, this.lootPredicateManager::func_227517_a_, immutablemap::get);
        immutablemap.forEach((p_227509_1_, p_227509_2_) -> {
            LootTableManager.func_227508_a_(validationtracker, p_227509_1_, p_227509_2_);
        });
        validationtracker.getProblems().forEach((p_215303_0_, p_215303_1_) -> {
            Fullyrandom.LOGGER.warn("Found validation problem in " + p_215303_0_ + ": " + p_215303_1_);
        });
        this.registeredLootTables = immutablemap;
    }

}
