package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class BeehiveTileEntity extends TileEntity implements ITickableTileEntity {
   private final List<BeehiveTileEntity.Bee> field_226958_a_ = Lists.newArrayList();
   @Nullable
   private BlockPos field_226959_b_ = null;

   public BeehiveTileEntity() {
      super(TileEntityType.field_226985_G_);
   }

   public void markDirty() {
      if (this.func_226968_d_()) {
         this.func_226963_a_((PlayerEntity)null, this.world.getBlockState(this.getPos()), BeehiveTileEntity.State.EMERGENCY);
      }

      super.markDirty();
   }

   public boolean func_226968_d_() {
      if (this.world == null) {
         return false;
      } else {
         for(BlockPos blockpos : BlockPos.getAllInBoxMutable(this.pos.add(-1, -1, -1), this.pos.add(1, 1, 1))) {
            if (this.world.getBlockState(blockpos).getBlock() instanceof FireBlock) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean func_226969_f_() {
      return this.field_226958_a_.isEmpty();
   }

   public boolean func_226970_h_() {
      return this.field_226958_a_.size() == 3;
   }

   public void func_226963_a_(@Nullable PlayerEntity p_226963_1_, BlockState p_226963_2_, BeehiveTileEntity.State p_226963_3_) {
      List<Entity> list = this.func_226965_a_(p_226963_2_, p_226963_3_);
      if (p_226963_1_ != null) {
         for(Entity entity : list) {
            if (entity instanceof BeeEntity) {
               BeeEntity beeentity = (BeeEntity)entity;
               if (p_226963_1_.getPositionVec().squareDistanceTo(entity.getPositionVec()) <= 16.0D) {
                  if (!this.func_226972_k_()) {
                     beeentity.setAttackTarget(p_226963_1_);
                  } else {
                     beeentity.func_226450_t_(400);
                  }
               }
            }
         }
      }

   }

   private List<Entity> func_226965_a_(BlockState p_226965_1_, BeehiveTileEntity.State p_226965_2_) {
      List<Entity> list = Lists.newArrayList();
      this.field_226958_a_.removeIf((p_226966_4_) -> {
         return this.func_235651_a_(p_226965_1_, p_226966_4_, list, p_226965_2_);
      });
      return list;
   }

   public void func_226961_a_(Entity p_226961_1_, boolean p_226961_2_) {
      this.func_226962_a_(p_226961_1_, p_226961_2_, 0);
   }

   public int func_226971_j_() {
      return this.field_226958_a_.size();
   }

   public static int func_226964_a_(BlockState p_226964_0_) {
      return p_226964_0_.get(BeehiveBlock.field_226873_c_);
   }

   public boolean func_226972_k_() {
      return CampfireBlock.func_235474_a_(this.world, this.getPos());
   }

   protected void func_226973_l_() {
      DebugPacketSender.func_229750_a_(this);
   }

   public void func_226962_a_(Entity p_226962_1_, boolean p_226962_2_, int p_226962_3_) {
      if (this.field_226958_a_.size() < 3) {
         p_226962_1_.stopRiding();
         p_226962_1_.removePassengers();
         CompoundNBT compoundnbt = new CompoundNBT();
         p_226962_1_.writeUnlessPassenger(compoundnbt);
         this.field_226958_a_.add(new BeehiveTileEntity.Bee(compoundnbt, p_226962_3_, p_226962_2_ ? 2400 : 600));
         if (this.world != null) {
            if (p_226962_1_ instanceof BeeEntity) {
               BeeEntity beeentity = (BeeEntity)p_226962_1_;
               if (beeentity.func_226425_er_() && (!this.func_226975_x_() || this.world.rand.nextBoolean())) {
                  this.field_226959_b_ = beeentity.func_226424_eq_();
               }
            }

            BlockPos blockpos = this.getPos();
            this.world.playSound((PlayerEntity)null, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), SoundEvents.field_226131_af_, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }

         p_226962_1_.remove();
      }
   }

   private boolean func_235651_a_(BlockState p_235651_1_, BeehiveTileEntity.Bee p_235651_2_, @Nullable List<Entity> p_235651_3_, BeehiveTileEntity.State p_235651_4_) {
      if ((this.world.func_226690_K_() || this.world.isRaining()) && p_235651_4_ != BeehiveTileEntity.State.EMERGENCY) {
         return false;
      } else {
         BlockPos blockpos = this.getPos();
         CompoundNBT compoundnbt = p_235651_2_.field_226977_a_;
         compoundnbt.remove("Passengers");
         compoundnbt.remove("Leash");
         compoundnbt.remove("UUID");
         Direction direction = p_235651_1_.get(BeehiveBlock.field_226872_b_);
         BlockPos blockpos1 = blockpos.offset(direction);
         boolean flag = !this.world.getBlockState(blockpos1).getCollisionShape(this.world, blockpos1).isEmpty();
         if (flag && p_235651_4_ != BeehiveTileEntity.State.EMERGENCY) {
            return false;
         } else {
            Entity entity = EntityType.func_220335_a(compoundnbt, this.world, (p_226960_0_) -> {
               return p_226960_0_;
            });
            if (entity != null) {
               if (!entity.getType().isContained(EntityTypeTags.field_226155_c_)) {
                  return false;
               } else {
                  if (entity instanceof BeeEntity) {
                     BeeEntity beeentity = (BeeEntity)entity;
                     if (this.func_226975_x_() && !beeentity.func_226425_er_() && this.world.rand.nextFloat() < 0.9F) {
                        beeentity.func_226431_g_(this.field_226959_b_);
                     }

                     if (p_235651_4_ == BeehiveTileEntity.State.HONEY_DELIVERED) {
                        beeentity.func_226413_eG_();
                        if (p_235651_1_.getBlock().isIn(BlockTags.field_226151_aa_)) {
                           int i = func_226964_a_(p_235651_1_);
                           if (i < 5) {
                              int j = this.world.rand.nextInt(100) == 0 ? 2 : 1;
                              if (i + j > 5) {
                                 --j;
                              }

                              this.world.setBlockState(this.getPos(), p_235651_1_.with(BeehiveBlock.field_226873_c_, Integer.valueOf(i + j)));
                           }
                        }
                     }

                     this.func_235650_a_(p_235651_2_.field_226978_b_, beeentity);
                     if (p_235651_3_ != null) {
                        p_235651_3_.add(beeentity);
                     }

                     float f = entity.getWidth();
                     double d3 = flag ? 0.0D : 0.55D + (double)(f / 2.0F);
                     double d0 = (double)blockpos.getX() + 0.5D + d3 * (double)direction.getXOffset();
                     double d1 = (double)blockpos.getY() + 0.5D - (double)(entity.getHeight() / 2.0F);
                     double d2 = (double)blockpos.getZ() + 0.5D + d3 * (double)direction.getZOffset();
                     entity.setLocationAndAngles(d0, d1, d2, entity.rotationYaw, entity.rotationPitch);
                  }

                  this.world.playSound((PlayerEntity)null, blockpos, SoundEvents.field_226132_ag_, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  return this.world.addEntity(entity);
               }
            } else {
               return false;
            }
         }
      }
   }

   private void func_235650_a_(int p_235650_1_, BeeEntity p_235650_2_) {
      int i = p_235650_2_.getGrowingAge();
      if (i < 0) {
         p_235650_2_.setGrowingAge(Math.min(0, i + p_235650_1_));
      } else if (i > 0) {
         p_235650_2_.setGrowingAge(Math.max(0, i - p_235650_1_));
      }

      p_235650_2_.setInLove(Math.max(0, p_235650_2_.func_234178_eO_() - p_235650_1_));
      p_235650_2_.func_226426_eu_();
   }

   private boolean func_226975_x_() {
      return this.field_226959_b_ != null;
   }

   private void func_226976_y_() {
      Iterator<BeehiveTileEntity.Bee> iterator = this.field_226958_a_.iterator();

      BeehiveTileEntity.Bee beehivetileentity$bee;
      for(BlockState blockstate = this.getBlockState(); iterator.hasNext(); beehivetileentity$bee.field_226978_b_++) {
         beehivetileentity$bee = iterator.next();
         if (beehivetileentity$bee.field_226978_b_ > beehivetileentity$bee.field_226979_c_) {
            BeehiveTileEntity.State beehivetileentity$state = beehivetileentity$bee.field_226977_a_.getBoolean("HasNectar") ? BeehiveTileEntity.State.HONEY_DELIVERED : BeehiveTileEntity.State.BEE_RELEASED;
            if (this.func_235651_a_(blockstate, beehivetileentity$bee, (List<Entity>)null, beehivetileentity$state)) {
               iterator.remove();
            }
         }
      }

   }

   public void tick() {
      if (!this.world.isRemote) {
         this.func_226976_y_();
         BlockPos blockpos = this.getPos();
         if (this.field_226958_a_.size() > 0 && this.world.getRandom().nextDouble() < 0.005D) {
            double d0 = (double)blockpos.getX() + 0.5D;
            double d1 = (double)blockpos.getY();
            double d2 = (double)blockpos.getZ() + 0.5D;
            this.world.playSound((PlayerEntity)null, d0, d1, d2, SoundEvents.field_226134_ai_, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }

         this.func_226973_l_();
      }
   }

   public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
      super.func_230337_a_(p_230337_1_, p_230337_2_);
      this.field_226958_a_.clear();
      ListNBT listnbt = p_230337_2_.getList("Bees", 10);

      for(int i = 0; i < listnbt.size(); ++i) {
         CompoundNBT compoundnbt = listnbt.getCompound(i);
         BeehiveTileEntity.Bee beehivetileentity$bee = new BeehiveTileEntity.Bee(compoundnbt.getCompound("EntityData"), compoundnbt.getInt("TicksInHive"), compoundnbt.getInt("MinOccupationTicks"));
         this.field_226958_a_.add(beehivetileentity$bee);
      }

      this.field_226959_b_ = null;
      if (p_230337_2_.contains("FlowerPos")) {
         this.field_226959_b_ = NBTUtil.readBlockPos(p_230337_2_.getCompound("FlowerPos"));
      }

   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.put("Bees", this.func_226974_m_());
      if (this.func_226975_x_()) {
         compound.put("FlowerPos", NBTUtil.writeBlockPos(this.field_226959_b_));
      }

      return compound;
   }

   public ListNBT func_226974_m_() {
      ListNBT listnbt = new ListNBT();

      for(BeehiveTileEntity.Bee beehivetileentity$bee : this.field_226958_a_) {
         beehivetileentity$bee.field_226977_a_.remove("UUID");
         CompoundNBT compoundnbt = new CompoundNBT();
         compoundnbt.put("EntityData", beehivetileentity$bee.field_226977_a_);
         compoundnbt.putInt("TicksInHive", beehivetileentity$bee.field_226978_b_);
         compoundnbt.putInt("MinOccupationTicks", beehivetileentity$bee.field_226979_c_);
         listnbt.add(compoundnbt);
      }

      return listnbt;
   }

   static class Bee {
      private final CompoundNBT field_226977_a_;
      private int field_226978_b_;
      private final int field_226979_c_;

      private Bee(CompoundNBT p_i225767_1_, int p_i225767_2_, int p_i225767_3_) {
         p_i225767_1_.remove("UUID");
         this.field_226977_a_ = p_i225767_1_;
         this.field_226978_b_ = p_i225767_2_;
         this.field_226979_c_ = p_i225767_3_;
      }
   }

   public static enum State {
      HONEY_DELIVERED,
      BEE_RELEASED,
      EMERGENCY;
   }
}