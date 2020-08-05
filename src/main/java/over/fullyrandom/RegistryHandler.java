package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;

public class RegistryHandler {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            ModBlocks.setOreBlocks(MainConfig.oreAmount.get());
            for (Object block: ModBlocks.oreBlocks) {
                blockRegistryEvent.getRegistry().register((Block) block);
            }
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            ModItems.setOreItems(MainConfig.oreAmount.get());
            itemRegistryEvent.getRegistry().registerAll(ModItems.oreItems);
            itemRegistryEvent.getRegistry().registerAll(ModItems.oreDrops);
            ModItems.swords.forEach(item -> { if (item != null) itemRegistryEvent.getRegistry().register(item); });
            ModItems.pickaxes.forEach(item -> { if (item != null) itemRegistryEvent.getRegistry().register(item); });
            ModItems.shovels.forEach(item -> { if (item != null) itemRegistryEvent.getRegistry().register(item); });
            ModItems.axes.forEach(item -> { if (item != null) itemRegistryEvent.getRegistry().register(item); });
            ModItems.hoes.forEach(item -> { if (item != null) itemRegistryEvent.getRegistry().register(item); });
        }

    }


}
