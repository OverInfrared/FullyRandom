package over.fullyrandom.mixin.core.client.renderer.model;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import over.fullyrandom.Randomizer;

@Mixin(ModelResourceLocation.class)
public abstract class MixinModelResourceLocation extends ResourceLocation {

    protected MixinModelResourceLocation(String[] resourceParts) {
        super(resourceParts);
    }

    private static int index = 0;
    /**
     * @author OverInfrared
     */
    @Overwrite
    protected static String[] parsePathString(String pathIn) {

        if (pathIn.contains("fullyrandom:r_ore") && !pathIn.contains("fullyrandom:r_oredrop")) {
            if (pathIn.contains("r_ore0"))
                index = 0;
            String overlay = Randomizer.blockProperties.getOverlay(index);
            String texture = Randomizer.blockProperties.material.get(index).name();
            if (!pathIn.contains("inventory")) {
                pathIn = "fullyrandom:" + texture.toLowerCase() + "_" + overlay.toLowerCase() + "#";
            } else {
                pathIn = "fullyrandom:" + texture.toLowerCase() + "_" + overlay.toLowerCase() + "#inventory";
            }
            index++;
        }

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

}
