package over.fullyrandom.mixin.core.common;

import com.google.common.collect.Queues;
import com.google.gson.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.config.MainConfig;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Deque;

@Mixin(ForgeHooks.class)
public class MixinForgeHooks {

    @Shadow private static ThreadLocal<Deque<ForgeHooks.LootTableContext>> lootContext;

    /**
     * @author OverInfrared
     */
    @Overwrite
    @Nullable
    public static LootTable loadLootTable(Gson gson, ResourceLocation name, JsonElement data, boolean custom, LootTableManager lootTableManager)
    {
        Deque<ForgeHooks.LootTableContext> que = lootContext.get();
        if (que == null)
        {
            que = Queues.newArrayDeque();
            lootContext.set(que);
        }

        LootTable ret = null;
        try
        {
            if (!name.toString().contains("fullyrandom")) {
                que.push(new ForgeHooks.LootTableContext(name, custom));
                ret = gson.fromJson(data, LootTable.class);
                que.pop();
            } else {
                for (int i = 0; i< MainConfig.oreAmount.get(); i++) {
                    que.push(new ForgeHooks.LootTableContext(new ResourceLocation("fullyrandom:blocks/r_ore" + i), custom));
                    Fullyrandom.LOGGER.info(getOreLootTable(i));
                    ret = gson.fromJson(getOreLootTable(i), LootTable.class);
                    que.pop();
                }
            }
        }
        catch (JsonParseException e)
        {
            que.pop();
            throw e;
        }

        if (!custom)
            ret = ForgeEventFactory.loadLootTable(name, ret, lootTableManager);

        if (ret != null)
            ret.freeze();

        return ret;
    }

    private static JsonElement getOreLootTable(int ore) {

        JsonObject main = new JsonObject();
        JsonArray pools = new JsonArray();
        JsonObject sub = new JsonObject();
        JsonArray entries = new JsonArray();
        JsonObject sub2 = new JsonObject();
        JsonArray conditions = new JsonArray();
        JsonObject sub3 = new JsonObject();

        sub2.addProperty("type", "minecraft:item");
        sub2.addProperty("name", "fullyrandom:r_ore" + ore);

        entries.add(sub2);

        sub3.addProperty("condition", "minecraft:survives_explosion");
        conditions.add(sub3);

        sub.addProperty("name", "pool1");
        sub.addProperty("rolls", 1);
        sub.add("entries", entries);
        sub.add("conditions", conditions);

        pools.add(sub);
        main.addProperty("type", "minecraft:block");
        main.add("pools", pools);

        return main;

    }

}
