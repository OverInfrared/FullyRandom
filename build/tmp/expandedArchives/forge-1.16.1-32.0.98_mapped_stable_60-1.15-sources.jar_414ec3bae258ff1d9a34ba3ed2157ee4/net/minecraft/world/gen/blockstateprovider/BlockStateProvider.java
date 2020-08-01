package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public abstract class BlockStateProvider {
   public static final Codec<BlockStateProvider> field_236796_a_ = Registry.field_229387_t_.dispatch(BlockStateProvider::func_230377_a_, BlockStateProviderType::func_236799_a_);

   protected abstract BlockStateProviderType<?> func_230377_a_();

   public abstract BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_);
}