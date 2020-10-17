package over.fullyrandom.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.Randomizer;

public class RandomOreItem extends Item {

    // SETUP
    int id;
    public RandomOreItem(Properties properties, int id) {
        super(properties);
        this.id = id;
    }

    // START OF FUEL
    @Override
    public int getBurnTime(ItemStack itemStack) {
        if (Randomizer.blockProperties.getIsFuel(id))
            return Randomizer.blockProperties.getFuelTime(id);
        else
            return 0;
    }
    // END OF FUEL

}
