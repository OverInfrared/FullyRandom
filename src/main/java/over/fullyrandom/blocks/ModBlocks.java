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
                oreBlocks.add(new FallingBlock(Randomizer.blockProperties.getProperties(i)).setRegistryName("fullyrandom:r_ore" + i));
            else
                oreBlocks.add(new Block(Randomizer.blockProperties.getProperties(i)).setRegistryName("fullyrandom:r_ore" + i));
        }
    }

}
