package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfirmScreen extends Screen {
   private final ITextComponent messageLine2;
   private final List<ITextProperties> listLines = Lists.newArrayList();
   protected ITextComponent confirmButtonText;
   protected ITextComponent cancelButtonText;
   private int ticksUntilEnable;
   protected final BooleanConsumer callbackFunction;

   public ConfirmScreen(BooleanConsumer _callbackFunction, ITextComponent _title, ITextComponent _messageLine2) {
      this(_callbackFunction, _title, _messageLine2, DialogTexts.field_240634_e_, DialogTexts.field_240635_f_);
   }

   public ConfirmScreen(BooleanConsumer p_i232270_1_, ITextComponent p_i232270_2_, ITextComponent p_i232270_3_, ITextComponent p_i232270_4_, ITextComponent p_i232270_5_) {
      super(p_i232270_2_);
      this.callbackFunction = p_i232270_1_;
      this.messageLine2 = p_i232270_3_;
      this.confirmButtonText = p_i232270_4_;
      this.cancelButtonText = p_i232270_5_;
   }

   public String func_231167_h_() {
      return super.func_231167_h_() + ". " + this.messageLine2.getString();
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 96, 150, 20, this.confirmButtonText, (p_213002_1_) -> {
         this.callbackFunction.accept(true);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, this.field_230709_l_ / 6 + 96, 150, 20, this.cancelButtonText, (p_213001_1_) -> {
         this.callbackFunction.accept(false);
      }));
      this.listLines.clear();
      this.listLines.addAll(this.field_230712_o_.func_238425_b_(this.messageLine2, this.field_230708_k_ - 50));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 70, 16777215);
      int i = 90;

      for(ITextProperties itextproperties : this.listLines) {
         this.func_238472_a_(p_230430_1_, this.field_230712_o_, itextproperties, this.field_230708_k_ / 2, i, 16777215);
         i += 9;
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void setButtonDelay(int ticksUntilEnableIn) {
      this.ticksUntilEnable = ticksUntilEnableIn;

      for(Widget widget : this.field_230710_m_) {
         widget.field_230693_o_ = false;
      }

   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (--this.ticksUntilEnable == 0) {
         for(Widget widget : this.field_230710_m_) {
            widget.field_230693_o_ = true;
         }
      }

   }

   public boolean func_231178_ax__() {
      return false;
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.callbackFunction.accept(false);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }
}