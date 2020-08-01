package net.minecraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OnAStickItem<T extends Entity & IRideable> extends Item {
   private final EntityType<T> field_234680_a_;
   private final int field_234681_b_;

   public OnAStickItem(Item.Properties p_i231594_1_, EntityType<T> p_i231594_2_, int p_i231594_3_) {
      super(p_i231594_1_);
      this.field_234680_a_ = p_i231594_2_;
      this.field_234681_b_ = p_i231594_3_;
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (worldIn.isRemote) {
         return ActionResult.func_226250_c_(itemstack);
      } else {
         Entity entity = playerIn.getRidingEntity();
         if (playerIn.isPassenger() && entity instanceof IRideable && entity.getType() == this.field_234680_a_) {
            IRideable irideable = (IRideable)entity;
            if (irideable.boost()) {
               itemstack.damageItem(this.field_234681_b_, playerIn, (p_234682_1_) -> {
                  p_234682_1_.sendBreakAnimation(handIn);
               });
               if (itemstack.isEmpty()) {
                  ItemStack itemstack1 = new ItemStack(Items.FISHING_ROD);
                  itemstack1.setTag(itemstack.getTag());
                  return ActionResult.func_226248_a_(itemstack1);
               }

               return ActionResult.func_226248_a_(itemstack);
            }
         }

         playerIn.addStat(Stats.ITEM_USED.get(this));
         return ActionResult.func_226250_c_(itemstack);
      }
   }
}