package over.fullyrandom.mixin.core.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BipedArmorLayer.class)
public class MixinBipedArmorLayer {

    @ModifyVariable(method = "getArmorResource", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.BEFORE), name = "resourcelocation", remap = false)
    private ResourceLocation resourceLocation(ResourceLocation resourcelocation) {
        if (resourcelocation.toString().contains("fullyrandom"))
            return getResourceLocation(resourcelocation);
        return resourcelocation;
    }

    private ResourceLocation getResourceLocation(ResourceLocation resourceLocation) {
        if (resourceLocation.toString().contains("layer_1.png")) {
            resourceLocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_1.png");
        } else if (resourceLocation.toString().contains("layer_2.png")) {
            resourceLocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_2.png");
        } else if (resourceLocation.toString().contains("layer_1_overlay")) {
            resourceLocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_1_overlay.png");
        } else if (resourceLocation.toString().contains("layer_2_overlay")) {
            resourceLocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_2_overlay.png");
        }
        return resourceLocation;
    }

}
