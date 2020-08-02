package over.fullyrandom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import over.fullyrandom.config.MainConfig;

import java.util.ArrayList;
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

        public static ArrayList<AppearsIn> material = new ArrayList<>();
        public static void getMaterial(int id) {
            Randomizer.blockProperties.AppearsIn[] mat = Randomizer.blockProperties.AppearsIn.values();
            material.add(mat[new Random(getSeed(id)).nextInt(mat.length)]);
        }

        public enum AppearsIn {
            STONE(Material.ROCK, SoundType.STONE, 2.0f, ToolType.PICKAXE),
            GRAVEL(Material.EARTH, SoundType.GROUND, 0.5f, ToolType.SHOVEL),
            ENDSTONE(Material.ROCK, SoundType.STONE, 3.0f, ToolType.PICKAXE),
            SAND(Material.SAND, SoundType.SAND, 0.5f, ToolType.SHOVEL),
            NETHERRACK(Material.ROCK, SoundType.STONE, 0.4f, ToolType.PICKAXE);

            public final Material mat;
            public final SoundType sound;
            public final float hardness;
            public final ToolType tool;

            AppearsIn(Material mat, SoundType sound, float hardness, ToolType tool) {
                this.mat = mat;
                this.sound = sound;
                this.hardness = hardness;
                this.tool = tool;
            }
        }

        public static boolean getOreType(int id) {
            return new Random(getSeed(id)).nextBoolean();
        }

    }

}
