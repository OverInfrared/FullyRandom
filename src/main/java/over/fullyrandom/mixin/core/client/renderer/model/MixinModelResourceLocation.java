package over.fullyrandom.mixin.core.client.renderer.model;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import over.fullyrandom.Fullyrandom;

@Mixin(ModelResourceLocation.class)
public class MixinModelResourceLocation extends ResourceLocation {

    protected MixinModelResourceLocation(String[] resourceParts) {
        super(resourceParts);
    }

    /**
     * @author fghj
     */
    @Overwrite
    protected static String[] parsePathString(String pathIn) {

        if (pathIn.equals("fullyrandom:test_block_yes#")) {
            pathIn = "fullyrandom:test_block_no#";
        }

        if (pathIn.equals("fullyrandom:test_block_yes#inventory")) {
            pathIn = "fullyrandom:test_block_no#inventory";
        }

        String[] astring = new String[]{null, pathIn, ""};
        int i = pathIn.indexOf(35);
        String s = pathIn;
        if (i >= 0) {
            astring[2] = pathIn.substring(i + 1, pathIn.length());
            if (i > 1) {
                s = pathIn.substring(0, i);
            }
        }

        System.arraycopy(ResourceLocation.decompose(s, ':'), 0, astring, 0, 2);

        Fullyrandom.LOGGER.info(pathIn);

        return astring;
    }

}