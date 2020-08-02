package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
        }

    }


}
