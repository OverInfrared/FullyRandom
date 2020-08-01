package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.items.ModItems;

@Mod.EventBusSubscriber(modid = "fullyrandom", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {

    @SubscribeEvent
    public static void registerBlockColors(final ColorHandlerEvent.Block event) {
        for (Block block: ModBlocks.oreBlocks) {
            event.getBlockColors().register((state, world, pos, tintIndex) -> { if (tintIndex == 1) { return  } else { return 0xFFffffff; } }, block);
        }
    }

    @SubscribeEvent
    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        for (BlockItem item: ModItems.oreItems) {
            event.getItemColors().register((stack, tintIndex) -> 0xFFa02d16, item);
        }
    }

}
