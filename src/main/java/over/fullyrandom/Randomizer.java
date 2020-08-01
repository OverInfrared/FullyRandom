package over.fullyrandom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import over.fullyrandom.blocks.RandomOre;
import over.fullyrandom.config.MainConfig;

import java.util.Random;

public class Randomizer {

    public static int getSeed(int id) {
        return new Random(new Random(MainConfig.seed.get()).nextInt(Math.abs(new Random(id).nextInt())) * new Random(id).nextInt()).nextInt();
    }

    public static class blockProperties {

        static int getColor(int id) {
            return new Random(getSeed(id)).nextInt(0xffffff + 1);
        }

        // Overlays
        public static String getOverlay(int id) {
            Overlay[] overlays = Overlay.values();
            return overlays[new Random(getSeed(id)).nextInt(overlays.length)].toString();
        }

        private enum Overlay {
            REGULAR,
            EMERALD,
            LAPIS,
            QUARTZ
        }


    }

}
