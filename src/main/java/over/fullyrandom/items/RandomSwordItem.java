package over.fullyrandom.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import over.fullyrandom.Randomizer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RandomSwordItem extends SwordItem {

    private final int id;

    public RandomSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, int id) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.id = id;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (Randomizer.blockProperties.getIsEffected(id))
            target.addPotionEffect(Randomizer.blockProperties.getEffect(id));
        return super.hitEntity(stack, target, attacker);
    }
}
