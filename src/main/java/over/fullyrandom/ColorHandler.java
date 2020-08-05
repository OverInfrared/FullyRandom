package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;
import over.fullyrandom.items.ModItems;

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
        for (int i = 0; i < MainConfig.oreAmount.get(); i++ ) {
            int finalI = i;
            event.getItemColors().register((stack, tintIndex) -> Randomizer.blockProperties.getColor(finalI), ModItems.oreItems[i]);
            event.getItemColors().register((stack, tintIndex) -> Randomizer.blockProperties.getColor(finalI), ModItems.oreDrops[i]);
            if (Randomizer.blockProperties.getTools(i)) {
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.swords.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.pickaxes.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.shovels.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.axes.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 1) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.hoes.get(i));

            }
            if (Randomizer.blockProperties.getArmor(i)) {
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.helmets.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.chestplates.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.leggings.get(i));
                event.getItemColors().register((stack, tintIndex) -> { if (tintIndex == 0) { return Randomizer.blockProperties.getColor(finalI); } else { return 0xFFffffff; } } , ModItems.boots.get(i));
            }
        }
    }

}
