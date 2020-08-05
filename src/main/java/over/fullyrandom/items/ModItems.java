package over.fullyrandom.items;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.MainConfig;

import java.util.ArrayList;

public class ModItems {

    public static BlockItem[] oreItems = new BlockItem[MainConfig.oreAmount.get()];
    public static Item[] oreDrops = new Item[MainConfig.oreAmount.get()];
    public static ArrayList<Item> pickaxes = new ArrayList<>();
    public static ArrayList<Item> swords = new ArrayList<>();
    public static ArrayList<Item> shovels = new ArrayList<>();
    public static ArrayList<Item> axes = new ArrayList<>();
    public static ArrayList<Item> hoes = new ArrayList<>();

    public static void setOreItems(int amount) {
        for (int i = 0; i < amount; i++) {
            oreItems[i] = (BlockItem) new BlockItem((Block) ModBlocks.oreBlocks.get(i), new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("fullyrandom:r_ore" + i);
            oreDrops[i] = new Item(new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("fullyrandom:r_oredrop" + i);
            if (Randomizer.blockProperties.getTools(i)) {
                pickaxes.add(new PickaxeItem(Randomizer.blockProperties.getToolTier(i, (float) (5.0/7.0)), 0, -2.8f, new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("r_pickaxe" + i));
                swords.add(new SwordItem(Randomizer.blockProperties.getToolTier(i, 1), 0, -2.4f, new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("r_sword" + i));
                shovels.add(new ShovelItem(Randomizer.blockProperties.getToolTier(i, (float) (5.0/7.0)), 0, -3.0f, new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("r_shovel" + i));
                axes.add(new AxeItem(Randomizer.blockProperties.getToolTier(i, (float) (9.0/7.0)), 0, -3.0f, new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("r_axe" + i));
                hoes.add(new HoeItem(Randomizer.blockProperties.getToolTier(i, 0), 0, 0, new Item.Properties().group(Fullyrandom.setup.itemGroup)).setRegistryName("r_hoe" + i));
            } else {
                pickaxes.add(null); swords.add(null); shovels.add(null); axes.add(null); hoes.add(null);
            }
        }
    }

}
