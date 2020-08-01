package over.fullyrandom.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.common.ToolType;
import over.fullyrandom.Randomizer;

import java.util.Random;

public class RandomOre extends Block {

    private static int id;
    RandomOre(int num) {
        super(getRandomProperties());
        id = num;
    }

    private static Properties getRandomProperties() {
        AppearsIn material = getRandomMaterial();
        return Properties.create(material.mat).sound(material.sound).hardnessAndResistance(material.hardness).harvestTool(material.tool).harvestLevel(new Random(Randomizer.getSeed(id)).nextInt(2 + 1) + 1);
    }

    private static AppearsIn getRandomMaterial() {
        AppearsIn[] mat = AppearsIn.values();
        return mat[new Random(Randomizer.getSeed(id)).nextInt(mat.length)];
    }

    public enum AppearsIn {
        STONE(Material.ROCK, SoundType.STONE, 2.0f, ToolType.PICKAXE),
        GRAVEL(Material.EARTH, SoundType.GROUND, 0.5f, ToolType.SHOVEL),
        END_STONE(Material.ROCK, SoundType.STONE, 3.0f, ToolType.PICKAXE),
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
}