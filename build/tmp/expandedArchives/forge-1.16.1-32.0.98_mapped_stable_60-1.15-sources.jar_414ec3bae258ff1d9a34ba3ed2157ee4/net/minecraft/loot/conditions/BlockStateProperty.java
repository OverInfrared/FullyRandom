package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockStateProperty implements ILootCondition {
   private final Block block;
   private final StatePropertiesPredicate properties;

   private BlockStateProperty(Block p_i225896_1_, StatePropertiesPredicate p_i225896_2_) {
      this.block = p_i225896_1_;
      this.properties = p_i225896_2_;
   }

   public LootConditionType func_230419_b_() {
      return LootConditionManager.field_237465_h_;
   }

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootParameters.BLOCK_STATE);
   }

   public boolean test(LootContext p_test_1_) {
      BlockState blockstate = p_test_1_.get(LootParameters.BLOCK_STATE);
      return blockstate != null && this.block == blockstate.getBlock() && this.properties.func_227181_a_(blockstate);
   }

   public static BlockStateProperty.Builder builder(Block blockIn) {
      return new BlockStateProperty.Builder(blockIn);
   }

   public static class Builder implements ILootCondition.IBuilder {
      private final Block block;
      private StatePropertiesPredicate desiredProperties = StatePropertiesPredicate.field_227178_a_;

      public Builder(Block blockIn) {
         this.block = blockIn;
      }

      public BlockStateProperty.Builder func_227567_a_(StatePropertiesPredicate.Builder p_227567_1_) {
         this.desiredProperties = p_227567_1_.func_227196_b_();
         return this;
      }

      public ILootCondition build() {
         return new BlockStateProperty(this.block, this.desiredProperties);
      }
   }

   public static class Serializer implements ILootSerializer<BlockStateProperty> {
      public void func_230424_a_(JsonObject p_230424_1_, BlockStateProperty p_230424_2_, JsonSerializationContext p_230424_3_) {
         p_230424_1_.addProperty("block", Registry.BLOCK.getKey(p_230424_2_.block).toString());
         p_230424_1_.add("properties", p_230424_2_.properties.func_227180_a_());
      }

      public BlockStateProperty func_230423_a_(JsonObject p_230423_1_, JsonDeserializationContext p_230423_2_) {
         ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_230423_1_, "block"));
         Block block = Registry.BLOCK.getValue(resourcelocation).orElseThrow(() -> {
            return new IllegalArgumentException("Can't find block " + resourcelocation);
         });
         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.func_227186_a_(p_230423_1_.get("properties"));
         statepropertiespredicate.func_227183_a_(block.getStateContainer(), (p_227568_1_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_227568_1_);
         });
         return new BlockStateProperty(block, statepropertiespredicate);
      }
   }
}