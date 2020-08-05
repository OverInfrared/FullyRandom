package over.fullyrandom.items;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
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
    public static ArrayList<Item> helmets = new ArrayList<>();
    public static ArrayList<Item> chestplates = new ArrayList<>();
    public static ArrayList<Item> leggings = new ArrayList<>();
    public static ArrayList<Item> boots = new ArrayList<>();

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
            if (Randomizer.blockProperties.getArmor(i)) {
                helmets.add(new RandomArmorItem(Randomizer.blockProperties.getArmorTier(i, (float) (363.0/528.0), (float) (3.0/8.0)), EquipmentSlotType.HEAD, new Item.Properties().group(Fullyrandom.setup.itemGroup), i).setRegistryName("r_helmet" + i));
                chestplates.add(new RandomArmorItem(Randomizer.blockProperties.getArmorTier(i, 1f, 1f), EquipmentSlotType.CHEST, new Item.Properties().group(Fullyrandom.setup.itemGroup), i).setRegistryName("r_chestplate" + i));
                leggings.add(new RandomArmorItem(Randomizer.blockProperties.getArmorTier(i, (float) (495.0/528.0), (float) (6.0/8.0)), EquipmentSlotType.LEGS, new Item.Properties().group(Fullyrandom.setup.itemGroup), i).setRegistryName("r_leggings" + i));
                boots.add(new RandomArmorItem(Randomizer.blockProperties.getArmorTier(i, (float) (429.0/528.0), (float) (3.0/8.0)), EquipmentSlotType.FEET, new Item.Properties().group(Fullyrandom.setup.itemGroup), i).setRegistryName("r_boots" + i));
            } else {
                helmets.add(null); chestplates.add(null); leggings.add(null); boots.add(null);
            }
        }
    }

}
