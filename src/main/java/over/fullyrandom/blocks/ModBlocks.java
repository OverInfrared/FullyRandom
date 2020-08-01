package over.fullyrandom.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import over.fullyrandom.config.MainConfig;

public class ModBlocks {

    public static Block[] oreBlocks = new Block[MainConfig.oreAmount.get()];

    public static void setOreBlocks(int amount) {
        for (int i = 0; i < amount; i++)
            oreBlocks[i] = new RandomOre().setRegistryName("fullyrandom:r_ore" + i);
    }

}
