package over.fullyrandom.mixin.core.client.renderer.model;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;

@Mixin(ModelResourceLocation.class)
public abstract class MixinModelResourceLocation extends ResourceLocation {

    @Shadow public abstract boolean equals(Object p_equals_1_);

    protected MixinModelResourceLocation(String[] resourceParts) {
        super(resourceParts);
    }

    /**
     * @author OverInfrared
     */
    @Overwrite
    protected static String[] parsePathString(String pathIn) {

        pathIn = createPath(pathIn);

        String[] astring = new String[]{null, pathIn, ""};
        int i = pathIn.indexOf(35);
        String s = pathIn;
        if (i >= 0) {
            astring[2] = pathIn.substring(i + 1);
            if (i > 1) {
                s = pathIn.substring(0, i);
            }
        }

        System.arraycopy(ResourceLocation.decompose(s, ':'), 0, astring, 0, 2);

        //Fullyrandom.LOGGER.info(pathIn);

        return astring;
    }

    private static String createPath(String pathIn) {

        if (pathIn.contains("fullyrandom:r_")) {

            int value = Integer.parseInt(pathIn.replaceAll("[^0-9]", ""));
            pathIn = pathIn.replaceAll("[^a-z]", "");
            String inventory = "#";
            if (pathIn.contains("inventory")) {
                inventory = "#inventory";
                pathIn = pathIn.replace("inventory", "");
            }

            if (pathIn.equals("fullyrandomrore")) {
                String overlay = Randomizer.blockProperties.getOverlay(value);
                String texture = Randomizer.blockProperties.material.get(value).name();
                pathIn = "fullyrandom:" + texture.toLowerCase() + "_" + overlay.toLowerCase() + inventory;
                return pathIn;
            } else if (pathIn.equals("fullyrandomroredrop")) {
                String texture = Randomizer.blockProperties.getDrop(value).name().toLowerCase();
                pathIn = "fullyrandom:" + texture + inventory;
                return pathIn;
            } 

            pathIn = createToolPath(pathIn);

        } else {
            return pathIn;
        }
        return pathIn;

    }

    private static String createToolPath(String pathIn) {

        if (pathIn.equals("fullyrandomrsword")) {
            return pathIn = "fullyrandom:sword#inventory";
        } else if (pathIn.equals("fullyrandomrpickaxe")) {
            return pathIn = "fullyrandom:pickaxe#inventory";
        } else if (pathIn.equals("fullyrandomrshovel")) {
            return pathIn = "fullyrandom:shovel#inventory";
        } else if (pathIn.equals("fullyrandomraxe")) {
            return pathIn = "fullyrandom:axe#inventory";
        } else if (pathIn.equals("fullyrandomrhoe")) {
            return pathIn = "fullyrandom:hoe#inventory";
        }

        return pathIn;

    }

}

