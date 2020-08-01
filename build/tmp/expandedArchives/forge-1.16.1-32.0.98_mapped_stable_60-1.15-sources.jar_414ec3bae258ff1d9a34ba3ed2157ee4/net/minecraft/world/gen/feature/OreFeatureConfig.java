package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.util.IStringSerializable;

public class OreFeatureConfig implements IFeatureConfig {
   public static final Codec<OreFeatureConfig> field_236566_a_ = RecordCodecBuilder.create((p_236568_0_) -> {
      return p_236568_0_.group(OreFeatureConfig.FillerBlockType.field_236571_d_.fieldOf("target").forGetter((p_236570_0_) -> {
         return p_236570_0_.target;
      }), BlockState.field_235877_b_.fieldOf("state").forGetter((p_236569_0_) -> {
         return p_236569_0_.state;
      }), Codec.INT.fieldOf("size").withDefault(0).forGetter((p_236567_0_) -> {
         return p_236567_0_.size;
      })).apply(p_236568_0_, OreFeatureConfig::new);
   });
   public final OreFeatureConfig.FillerBlockType target;
   public final int size;
   public final BlockState state;

   public OreFeatureConfig(OreFeatureConfig.FillerBlockType target, BlockState state, int size) {
      this.size = size;
      this.state = state;
      this.target = target;
   }

   public static enum FillerBlockType implements IStringSerializable, net.minecraftforge.common.IExtensibleEnum {
      NATURAL_STONE("natural_stone", (p_214739_0_) -> {
         if (p_214739_0_ == null) {
            return false;
         } else {
            return p_214739_0_.isIn(Blocks.STONE) || p_214739_0_.isIn(Blocks.GRANITE) || p_214739_0_.isIn(Blocks.DIORITE) || p_214739_0_.isIn(Blocks.ANDESITE);
         }
      }),
      NETHERRACK("netherrack", new BlockMatcher(Blocks.NETHERRACK)),
      NETHER_ORE_REPLACEABLES("nether_ore_replaceables", (p_236572_0_) -> {
         if (p_236572_0_ == null) {
            return false;
         } else {
            return p_236572_0_.isIn(Blocks.NETHERRACK) || p_236572_0_.isIn(Blocks.field_235337_cO_) || p_236572_0_.isIn(Blocks.field_235406_np_);
         }
      });

      public static final Codec<OreFeatureConfig.FillerBlockType> field_236571_d_ = IStringSerializable.func_233023_a_(OreFeatureConfig.FillerBlockType::values, OreFeatureConfig.FillerBlockType::func_214736_a);
      private static final Map<String, OreFeatureConfig.FillerBlockType> field_214741_c = Arrays.stream(values()).collect(Collectors.toMap(OreFeatureConfig.FillerBlockType::func_214737_a, (p_236573_0_) -> {
         return p_236573_0_;
      }));
      private final String field_214742_d;
      private final Predicate<BlockState> field_214743_e;

      private FillerBlockType(String p_i50618_3_, Predicate<BlockState> p_i50618_4_) {
         this.field_214742_d = p_i50618_3_;
         this.field_214743_e = p_i50618_4_;
      }

      public String func_214737_a() {
         return this.field_214742_d;
      }

      public static OreFeatureConfig.FillerBlockType func_214736_a(String p_214736_0_) {
         return field_214741_c.get(p_214736_0_);
      }

      public static FillerBlockType create(String enumName, String p_i50618_3_, Predicate<BlockState> p_i50618_4_) {
         throw new IllegalStateException("Enum not extended");
      }

      @Override
      @Deprecated
      public void init() {
          field_214741_c.put(func_214737_a(), this);
      }

      public Predicate<BlockState> func_214738_b() {
         return this.field_214743_e;
      }

      public String getName() {
         return this.field_214742_d;
      }
   }
}