package over.fullyrandom.mixin.core.common.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import over.fullyrandom.json.LootTables;

@Mixin(LootTableManager.class)
public abstract class MixinLootTableManager extends JsonReloadListener {

    private static final Gson GSON_INSTANCE = LootSerializers.func_237388_c_().create();
    @Shadow @Final @Mutable private final LootPredicateManager lootPredicateManager;
    private final LootTableManager lootTableManager;

    public MixinLootTableManager(Gson p_i51536_1_, String p_i51536_2_, LootPredicateManager field_227507_d_, LootTableManager lootTableManager) {
        super(p_i51536_1_, p_i51536_2_);
        this.lootPredicateManager = field_227507_d_;
        this.lootTableManager = lootTableManager;
    }

    @Redirect(method = "apply", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap;builder()Lcom/google/common/collect/ImmutableMap$Builder;"))
    private ImmutableMap.Builder<ResourceLocation, LootTable> apply() {
        ImmutableMap.Builder<ResourceLocation, LootTable> builder = ImmutableMap.builder();
        return LootTables.getLootTables(builder, GSON_INSTANCE, this.lootTableManager);
    }

}
