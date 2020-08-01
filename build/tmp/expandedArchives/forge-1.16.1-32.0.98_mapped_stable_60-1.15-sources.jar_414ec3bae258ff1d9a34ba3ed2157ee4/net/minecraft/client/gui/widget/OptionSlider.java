package net.minecraft.client.gui.widget;

import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionSlider extends GameSettingsSlider {
   private final SliderPercentageOption option;

   public OptionSlider(GameSettings settings, int xIn, int yIn, int widthIn, int heightIn, SliderPercentageOption p_i51129_6_) {
      super(settings, xIn, yIn, widthIn, heightIn, (double)((float)p_i51129_6_.func_216726_a(p_i51129_6_.get(settings))));
      this.option = p_i51129_6_;
      this.func_230979_b_();
   }

   protected void func_230972_a_() {
      this.option.set(this.field_238477_a_, this.option.func_216725_b(this.field_230683_b_));
      this.field_238477_a_.saveOptions();
   }

   protected void func_230979_b_() {
      this.func_238482_a_(this.option.func_238334_c_(this.field_238477_a_));
   }
}