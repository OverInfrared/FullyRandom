package over.fullyrandom.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import over.fullyrandom.Randomizer;
import over.fullyrandom.config.MainConfig;

import java.util.ArrayList;

public class ModBlocks {

    public static final ArrayList<Object> oreBlocks = setOreBlocks(MainConfig.oreAmount.get());

    public static ArrayList<Object> setOreBlocks(int amount) {
        ArrayList<Object> blocks = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Randomizer.blockProperties.getMaterial(i);
            if (Randomizer.blockProperties.material.get(i).name().equals("SAND") || Randomizer.blockProperties.material.get(i).name().equals("GRAVEL"))
                blocks.add(new FallingBlock(Randomizer.blockProperties.getProperties(i)).setRegistryName("fullyrandom:r_ore" + i));
            else
                blocks.add(new Block(Randomizer.blockProperties.getProperties(i)).setRegistryName("fullyrandom:r_ore" + i));
        }
        return blocks;
    }

}
