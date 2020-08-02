package over.fullyrandom.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.loot.LootTable;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;
import over.fullyrandom.config.MainConfig;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ModBlocks {

    public static ArrayList<Object> oreBlocks = new ArrayList<>();

    public static void setOreBlocks(int amount) {
        for (int i = 0; i < amount; i++) {
            Randomizer.blockProperties.getMaterial(i);
            if (Randomizer.blockProperties.material.get(i).name().equals("SAND") || Randomizer.blockProperties.material.get(i).name().equals("GRAVEL"))
                oreBlocks.add(new FallingBlock(AbstractBlock.Properties.create(Randomizer.blockProperties.material.get(i).mat).sound(Randomizer.blockProperties.material.get(i).sound).hardnessAndResistance(Randomizer.blockProperties.material.get(i).hardness).harvestTool(Randomizer.blockProperties.material.get(i).tool).harvestLevel(new Random(Randomizer.getSeed(i)).nextInt(2 + 1) + 1)).setRegistryName("fullyrandom:r_ore" + i));
            else
                oreBlocks.add(new Block(AbstractBlock.Properties.create(Randomizer.blockProperties.material.get(i).mat).sound(Randomizer.blockProperties.material.get(i).sound).hardnessAndResistance(Randomizer.blockProperties.material.get(i).hardness).harvestTool(Randomizer.blockProperties.material.get(i).tool).harvestLevel(new Random(Randomizer.getSeed(i)).nextInt(2 + 1) + 1)).setRegistryName("fullyrandom:r_ore" + i));
        }
    }

}
