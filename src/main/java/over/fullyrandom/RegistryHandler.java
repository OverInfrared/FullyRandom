package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import over.fullyrandom.blocks.ModBlocks;

public class RegistryHandler {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

            blockRegistryEvent.getRegistry().register(ModBlocks.TestBlock.setRegistryName("fullyrandom:test_block_no"));
            blockRegistryEvent.getRegistry().register(ModBlocks.TestBlock2.setRegistryName("fullyrandom:test_block_yes"));

        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.TestBlock, new Item.Properties()).setRegistryName("fullyrandom:test_block_no"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.TestBlock2, new Item.Properties()).setRegistryName("fullyrandom:test_block_yes"));
            Fullyrandom.LOGGER.info(ModBlocks.TestBlock.getRegistryName());

        }

    }


}
