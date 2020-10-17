package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = "fullyrandom", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {

    @SubscribeEvent
    public static void registerBlockColors(final ColorHandlerEvent.Block event) {
        for (int i = 0; i < MainConfig.oreAmount.get(); i++) {
            int finalI = i;
            event.getBlockColors().register((state, world, pos, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } }, (Block) ModBlocks.oreBlocks.get(i));
        }
    }

    @SubscribeEvent
    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        ModItems.oreItems.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.oreDrops.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.swords.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.pickaxes.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.shovels.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.axes.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.hoes.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.nuggets.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.helmets.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.chestplates.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.leggings.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
        ModItems.boots.forEach(item -> event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(ModItems.getStringNumber(Objects.requireNonNull(item.getRegistryName()).toString())); } else { return 0xFFffffff; }}, item));
    }

}
