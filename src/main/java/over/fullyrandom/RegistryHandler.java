package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;

import java.util.List;

public class RegistryHandler {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            ModBlocks.oreBlocks.forEach(block -> blockRegistryEvent.getRegistry().register((Block) block));
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            ModItems.setOreItems(MainConfig.oreAmount.get());
            ModItems.oreItems.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.oreDrops.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.swords.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.pickaxes.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.shovels.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.axes.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.hoes.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.nuggets.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.helmets.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.chestplates.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.leggings.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
            ModItems.boots.forEach(item -> { itemRegistryEvent.getRegistry().register(item); });
        }

        @SubscribeEvent
        public static void onBiomeRegistry(final RegistryEvent.Register<Biome> biomeRegistryEvent) {
            biomeRegistryEvent.getRegistry().registerAll();
        }

    }


}
