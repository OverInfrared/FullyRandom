package over.fullyrandom.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.world.biomes.ModBiomes;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup("fullyrandom") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack((IItemProvider) ModBlocks.oreBlocks.get(0));
        }
    };

    public void init() {
        //ModBiomes.setup();
    }

}
