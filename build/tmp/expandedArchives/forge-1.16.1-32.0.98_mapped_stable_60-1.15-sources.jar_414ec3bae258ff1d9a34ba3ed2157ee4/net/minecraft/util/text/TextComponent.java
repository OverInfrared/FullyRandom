package net.minecraft.util.text;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;

public abstract class TextComponent implements IFormattableTextComponent {
   protected final List<ITextComponent> siblings = Lists.newArrayList();
   private Style style = Style.field_240709_b_;

   public IFormattableTextComponent func_230529_a_(ITextComponent p_230529_1_) {
      this.siblings.add(p_230529_1_);
      return this;
   }

   public String getUnformattedComponentText() {
      return "";
   }

   public List<ITextComponent> getSiblings() {
      return this.siblings;
   }

   public IFormattableTextComponent func_230530_a_(Style p_230530_1_) {
      this.style = p_230530_1_;
      return this;
   }

   public Style getStyle() {
      return this.style;
   }

   public abstract TextComponent func_230531_f_();

   public final IFormattableTextComponent func_230532_e_() {
      TextComponent textcomponent = this.func_230531_f_();
      textcomponent.siblings.addAll(this.siblings);
      textcomponent.func_230530_a_(this.style);
      return textcomponent;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof TextComponent)) {
         return false;
      } else {
         TextComponent textcomponent = (TextComponent)p_equals_1_;
         return this.siblings.equals(textcomponent.siblings) && Objects.equals(this.getStyle(), textcomponent.getStyle());
      }
   }

   public int hashCode() {
      return Objects.hash(this.getStyle(), this.siblings);
   }

   public String toString() {
      return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
   }
}