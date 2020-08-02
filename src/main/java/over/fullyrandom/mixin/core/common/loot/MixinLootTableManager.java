package over.fullyrandom.mixin.core.common.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.loot.*;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import over.fullyrandom.Fullyrandom;

import java.util.Map;

@Mixin(LootTableManager.class)
public class MixinLootTableManager {

    @Overwrite
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        ImmutableMap.Builder<ResourceLocation, LootTable> builder = ImmutableMap.builder();
        JsonElement jsonelement = objectIn.remove(LootTables.EMPTY);
        if (jsonelement != null) {
            Fullyrandom.LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", (Object)LootTables.EMPTY);
        }

        objectIn.forEach((p_237403_1_, p_237403_2_) -> {
            try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(p_237403_1_));){
                LootTable loottable = net.minecraftforge.common.ForgeHooks.loadLootTable(GSON_INSTANCE, p_237403_1_, p_237403_2_, res == null || !res.getPackName().equals("Default"), this);
                builder.put(p_237403_1_, loottable);
            } catch (Exception exception) {
                LOGGER.error("Couldn't parse loot table {}", p_237403_1_, exception);
            }

        });
        builder.put(LootTables.EMPTY, LootTable.EMPTY_LOOT_TABLE);
        ImmutableMap<ResourceLocation, LootTable> immutablemap = builder.build();
        ValidationTracker validationtracker = new ValidationTracker(LootParameterSets.GENERIC, this.field_227507_d_::func_227517_a_, immutablemap::get);
        immutablemap.forEach((p_227509_1_, p_227509_2_) -> {
            func_227508_a_(validationtracker, p_227509_1_, p_227509_2_);
        });
        validationtracker.getProblems().forEach((p_215303_0_, p_215303_1_) -> {
            LOGGER.warn("Found validation problem in " + p_215303_0_ + ": " + p_215303_1_);
        });
        this.registeredLootTables = immutablemap;
    }

}
