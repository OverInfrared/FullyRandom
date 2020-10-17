package over.fullyrandom.mixin.core.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import over.fullyrandom.Fullyrandom;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(BipedArmorLayer.class)
public class MixinBipedArmorLayer {

    @Shadow @Final private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

    /**
     * @author OverInfrared
     * @reason Load uncolored armor textures for each item.
     */
    @Overwrite(remap = false)
    public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EquipmentSlotType slot, @Nullable String type) {
        ArmorItem item = (ArmorItem)stack.getItem();
        String texture = item.getArmorMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (isLegSlot(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            if (resourcelocation.toString().contains("fullyrandom")) {
                if (resourcelocation.toString().contains("layer_1.png")) {
                    resourcelocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_1.png");
                } else if (resourcelocation.toString().contains("layer_2.png")) {
                    resourcelocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_2.png");
                } else if (resourcelocation.toString().contains("layer_1_overlay")) {
                    resourcelocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_1_overlay.png");
                } else if (resourcelocation.toString().contains("layer_2_overlay")) {
                    resourcelocation = new ResourceLocation("fullyrandom:textures/models/armor/armor_layer_2_overlay.png");
                }
            }
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }

        return resourcelocation;
    }


    private boolean isLegSlot(EquipmentSlotType slotIn) {
        return slotIn == EquipmentSlotType.LEGS;
    }

}
