package over.fullyrandom.items;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;

public class ModItems {

    public static BlockItem[] oreItems = new BlockItem[MainConfig.oreAmount.get()];

    public static void setOreItems(int amount) {
        for (int i = 0; i < amount; i++)
            oreItems[i] = (BlockItem) new BlockItem(ModBlocks.oreBlocks[i], new Item.Properties()).setRegistryName("fullyrandom:r_ore" + i);
    }

}
