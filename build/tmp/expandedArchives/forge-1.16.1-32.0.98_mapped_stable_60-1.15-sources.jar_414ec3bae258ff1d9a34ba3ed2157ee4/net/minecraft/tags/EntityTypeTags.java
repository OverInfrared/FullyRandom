package net.minecraft.tags;

import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityTypeTags {
   private static final TagRegistry<EntityType<?>> field_219766_c = new TagRegistry<>();
   public static final ITag.INamedTag<EntityType<?>> SKELETONS = func_232896_a_("skeletons");
   public static final ITag.INamedTag<EntityType<?>> RAIDERS = func_232896_a_("raiders");
   public static final ITag.INamedTag<EntityType<?>> field_226155_c_ = func_232896_a_("beehive_inhabitors");
   public static final ITag.INamedTag<EntityType<?>> field_226156_d_ = func_232896_a_("arrows");
   public static final ITag.INamedTag<EntityType<?>> field_232893_e_ = func_232896_a_("impact_projectiles");

   public static ITag.INamedTag<EntityType<?>> func_232896_a_(String p_232896_0_) {
      return field_219766_c.func_232937_a_(p_232896_0_);
   }

   public static void setCollection(TagCollection<EntityType<?>> p_219759_0_) {
      field_219766_c.func_232935_a_(p_219759_0_);
   }

   @OnlyIn(Dist.CLIENT)
   public static void func_232895_a_() {
      field_219766_c.func_232932_a_();
   }

   public static TagCollection<EntityType<?>> getCollection() {
      return field_219766_c.func_232939_b_();
   }

   public static Set<ResourceLocation> func_232897_b_(TagCollection<EntityType<?>> p_232897_0_) {
      return field_219766_c.func_232940_b_(p_232897_0_);
   }
}