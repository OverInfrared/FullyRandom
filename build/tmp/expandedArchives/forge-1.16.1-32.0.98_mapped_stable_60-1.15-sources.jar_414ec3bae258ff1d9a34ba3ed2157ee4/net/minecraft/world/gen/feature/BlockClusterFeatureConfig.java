package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;

public class BlockClusterFeatureConfig implements IFeatureConfig {
   public static final Codec<BlockClusterFeatureConfig> field_236587_a_ = RecordCodecBuilder.create((p_236589_0_) -> {
      return p_236589_0_.group(BlockStateProvider.field_236796_a_.fieldOf("state_provider").forGetter((p_236599_0_) -> {
         return p_236599_0_.field_227289_a_;
      }), BlockPlacer.field_236435_a_.fieldOf("block_placer").forGetter((p_236598_0_) -> {
         return p_236598_0_.field_227290_b_;
      }), BlockState.field_235877_b_.listOf().fieldOf("whitelist").forGetter((p_236597_0_) -> {
         return p_236597_0_.field_227291_c_.stream().map(Block::getDefaultState).collect(Collectors.toList());
      }), BlockState.field_235877_b_.listOf().fieldOf("blacklist").forGetter((p_236596_0_) -> {
         return ImmutableList.copyOf(p_236596_0_.field_227292_d_);
      }), Codec.INT.fieldOf("tries").withDefault(128).forGetter((p_236595_0_) -> {
         return p_236595_0_.field_227293_f_;
      }), Codec.INT.fieldOf("xspread").withDefault(7).forGetter((p_236594_0_) -> {
         return p_236594_0_.field_227294_g_;
      }), Codec.INT.fieldOf("yspread").withDefault(3).forGetter((p_236593_0_) -> {
         return p_236593_0_.field_227295_h_;
      }), Codec.INT.fieldOf("zspread").withDefault(7).forGetter((p_236592_0_) -> {
         return p_236592_0_.field_227296_i_;
      }), Codec.BOOL.fieldOf("can_replace").withDefault(false).forGetter((p_236591_0_) -> {
         return p_236591_0_.field_227297_j_;
      }), Codec.BOOL.fieldOf("project").withDefault(true).forGetter((p_236590_0_) -> {
         return p_236590_0_.field_227298_k_;
      }), Codec.BOOL.fieldOf("need_water").withDefault(false).forGetter((p_236588_0_) -> {
         return p_236588_0_.field_227299_l_;
      })).apply(p_236589_0_, BlockClusterFeatureConfig::new);
   });
   public final BlockStateProvider field_227289_a_;
   public final BlockPlacer field_227290_b_;
   public final Set<Block> field_227291_c_;
   public final Set<BlockState> field_227292_d_;
   public final int field_227293_f_;
   public final int field_227294_g_;
   public final int field_227295_h_;
   public final int field_227296_i_;
   public final boolean field_227297_j_;
   public final boolean field_227298_k_;
   public final boolean field_227299_l_;

   private BlockClusterFeatureConfig(BlockStateProvider p_i232014_1_, BlockPlacer p_i232014_2_, List<BlockState> p_i232014_3_, List<BlockState> p_i232014_4_, int p_i232014_5_, int p_i232014_6_, int p_i232014_7_, int p_i232014_8_, boolean p_i232014_9_, boolean p_i232014_10_, boolean p_i232014_11_) {
      this(p_i232014_1_, p_i232014_2_, p_i232014_3_.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet()), ImmutableSet.copyOf(p_i232014_4_), p_i232014_5_, p_i232014_6_, p_i232014_7_, p_i232014_8_, p_i232014_9_, p_i232014_10_, p_i232014_11_);
   }

   private BlockClusterFeatureConfig(BlockStateProvider p_i225836_1_, BlockPlacer p_i225836_2_, Set<Block> p_i225836_3_, Set<BlockState> p_i225836_4_, int p_i225836_5_, int p_i225836_6_, int p_i225836_7_, int p_i225836_8_, boolean p_i225836_9_, boolean p_i225836_10_, boolean p_i225836_11_) {
      this.field_227289_a_ = p_i225836_1_;
      this.field_227290_b_ = p_i225836_2_;
      this.field_227291_c_ = p_i225836_3_;
      this.field_227292_d_ = p_i225836_4_;
      this.field_227293_f_ = p_i225836_5_;
      this.field_227294_g_ = p_i225836_6_;
      this.field_227295_h_ = p_i225836_7_;
      this.field_227296_i_ = p_i225836_8_;
      this.field_227297_j_ = p_i225836_9_;
      this.field_227298_k_ = p_i225836_10_;
      this.field_227299_l_ = p_i225836_11_;
   }

   public static class Builder {
      private final BlockStateProvider field_227303_a_;
      private final BlockPlacer field_227304_b_;
      private Set<Block> field_227305_c_ = ImmutableSet.of();
      private Set<BlockState> field_227306_d_ = ImmutableSet.of();
      private int field_227307_e_ = 64;
      private int field_227308_f_ = 7;
      private int field_227309_g_ = 3;
      private int field_227310_h_ = 7;
      private boolean field_227311_i_;
      private boolean field_227312_j_ = true;
      private boolean field_227313_k_ = false;

      public Builder(BlockStateProvider p_i225838_1_, BlockPlacer p_i225838_2_) {
         this.field_227303_a_ = p_i225838_1_;
         this.field_227304_b_ = p_i225838_2_;
      }

      public BlockClusterFeatureConfig.Builder func_227316_a_(Set<Block> p_227316_1_) {
         this.field_227305_c_ = p_227316_1_;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227319_b_(Set<BlockState> p_227319_1_) {
         this.field_227306_d_ = p_227319_1_;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227315_a_(int p_227315_1_) {
         this.field_227307_e_ = p_227315_1_;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227318_b_(int p_227318_1_) {
         this.field_227308_f_ = p_227318_1_;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227321_c_(int p_227321_1_) {
         this.field_227309_g_ = p_227321_1_;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227323_d_(int p_227323_1_) {
         this.field_227310_h_ = p_227323_1_;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227314_a_() {
         this.field_227311_i_ = true;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227317_b_() {
         this.field_227312_j_ = false;
         return this;
      }

      public BlockClusterFeatureConfig.Builder func_227320_c_() {
         this.field_227313_k_ = true;
         return this;
      }

      public BlockClusterFeatureConfig func_227322_d_() {
         return new BlockClusterFeatureConfig(this.field_227303_a_, this.field_227304_b_, this.field_227305_c_, this.field_227306_d_, this.field_227307_e_, this.field_227308_f_, this.field_227309_g_, this.field_227310_h_, this.field_227311_i_, this.field_227312_j_, this.field_227313_k_);
      }
   }
}