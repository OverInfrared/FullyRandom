package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public class BlockStateProviderType<P extends BlockStateProvider> extends net.minecraftforge.registries.ForgeRegistryEntry<BlockStateProviderType<?>> {
   public static final BlockStateProviderType<SimpleBlockStateProvider> field_227394_a_ = func_236800_a_("simple_state_provider", SimpleBlockStateProvider.field_236809_b_);
   public static final BlockStateProviderType<WeightedBlockStateProvider> field_227395_b_ = func_236800_a_("weighted_state_provider", WeightedBlockStateProvider.field_236811_b_);
   public static final BlockStateProviderType<PlainFlowerBlockStateProvider> field_227396_c_ = func_236800_a_("plain_flower_provider", PlainFlowerBlockStateProvider.field_236804_b_);
   public static final BlockStateProviderType<ForestFlowerBlockStateProvider> field_227397_d_ = func_236800_a_("forest_flower_provider", ForestFlowerBlockStateProvider.field_236801_b_);
   public static final BlockStateProviderType<AxisRotatingBlockStateProvider> field_236797_e_ = func_236800_a_("rotated_block_provider", AxisRotatingBlockStateProvider.field_236807_b_);
   private final Codec<P> field_236798_f_;

   private static <P extends BlockStateProvider> BlockStateProviderType<P> func_236800_a_(String p_236800_0_, Codec<P> p_236800_1_) {
      return Registry.register(Registry.field_229387_t_, p_236800_0_, new BlockStateProviderType<>(p_236800_1_));
   }

   private BlockStateProviderType(Codec<P> p_i232041_1_) {
      this.field_236798_f_ = p_i232041_1_;
   }

   public Codec<P> func_236799_a_() {
      return this.field_236798_f_;
   }
}