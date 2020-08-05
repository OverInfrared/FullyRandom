package over.fullyrandom.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import over.fullyrandom.Randomizer;

public class RandomArmorItem extends DyeableArmorItem implements IDyeableArmorItem {

    private int index;

    public RandomArmorItem(IArmorMaterial p_i50048_1_, EquipmentSlotType p_i50048_2_, Properties p_i50048_3_, int index) {
        super(p_i50048_1_, p_i50048_2_, p_i50048_3_);
        this.index = index;
    }

    @Override
    public int getColor(ItemStack itemStack) {
        return Randomizer.blockProperties.getColor(index);
    }


}
