package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextFieldWidget extends Widget implements IRenderable, IGuiEventListener {
   private final FontRenderer fontRenderer;
   private String text = "";
   private int maxStringLength = 32;
   private int cursorCounter;
   private boolean enableBackgroundDrawing = true;
   private boolean canLoseFocus = true;
   private boolean isEnabled = true;
   private boolean field_212956_h;
   private int lineScrollOffset;
   private int cursorPosition;
   private int selectionEnd;
   private int enabledColor = 14737632;
   private int disabledColor = 7368816;
   private String suggestion;
   private Consumer<String> guiResponder;
   private Predicate<String> validator = Objects::nonNull;
   private BiFunction<String, Integer, String> textFormatter = (p_195610_0_, p_195610_1_) -> {
      return p_195610_0_;
   };

   public TextFieldWidget(FontRenderer p_i232260_1_, int p_i232260_2_, int p_i232260_3_, int p_i232260_4_, int p_i232260_5_, ITextComponent p_i232260_6_) {
      this(p_i232260_1_, p_i232260_2_, p_i232260_3_, p_i232260_4_, p_i232260_5_, (TextFieldWidget)null, p_i232260_6_);
   }

   public TextFieldWidget(FontRenderer p_i232259_1_, int p_i232259_2_, int p_i232259_3_, int p_i232259_4_, int p_i232259_5_, @Nullable TextFieldWidget p_i232259_6_, ITextComponent p_i232259_7_) {
      super(p_i232259_2_, p_i232259_3_, p_i232259_4_, p_i232259_5_, p_i232259_7_);
      this.fontRenderer = p_i232259_1_;
      if (p_i232259_6_ != null) {
         this.setText(p_i232259_6_.getText());
      }

   }

   public void setResponder(Consumer<String> p_212954_1_) {
      this.guiResponder = p_212954_1_;
   }

   public void setTextFormatter(BiFunction<String, Integer, String> p_195607_1_) {
      this.textFormatter = p_195607_1_;
   }

   public void tick() {
      ++this.cursorCounter;
   }

   protected IFormattableTextComponent func_230442_c_() {
      ITextComponent itextcomponent = this.func_230458_i_();
      return new TranslationTextComponent("gui.narrate.editBox", itextcomponent, this.text);
   }

   public void setText(String textIn) {
      if (this.validator.test(textIn)) {
         if (textIn.length() > this.maxStringLength) {
            this.text = textIn.substring(0, this.maxStringLength);
         } else {
            this.text = textIn;
         }

         this.setCursorPositionEnd();
         this.setSelectionPos(this.cursorPosition);
         this.func_212951_d(textIn);
      }
   }

   public String getText() {
      return this.text;
   }

   public String getSelectedText() {
      int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
      int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
      return this.text.substring(i, j);
   }

   public void setValidator(Predicate<String> p_200675_1_) {
      this.validator = p_200675_1_;
   }

   public void writeText(String textToWrite) {
      int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
      int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
      int k = this.maxStringLength - this.text.length() - (i - j);
      String s = SharedConstants.filterAllowedCharacters(textToWrite);
      int l = s.length();
      if (k < l) {
         s = s.substring(0, k);
         l = k;
      }

      String s1 = (new StringBuilder(this.text)).replace(i, j, s).toString();
      if (this.validator.test(s1)) {
         this.text = s1;
         this.clampCursorPosition(i + l);
         this.setSelectionPos(this.cursorPosition);
         this.func_212951_d(this.text);
      }
   }

   private void func_212951_d(String p_212951_1_) {
      if (this.guiResponder != null) {
         this.guiResponder.accept(p_212951_1_);
      }

      this.field_230696_r_ = Util.milliTime() + 500L;
   }

   private void delete(int p_212950_1_) {
      if (Screen.func_231172_r_()) {
         this.deleteWords(p_212950_1_);
      } else {
         this.deleteFromCursor(p_212950_1_);
      }

   }

   public void deleteWords(int num) {
      if (!this.text.isEmpty()) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            this.deleteFromCursor(this.getNthWordFromCursor(num) - this.cursorPosition);
         }
      }
   }

   public void deleteFromCursor(int num) {
      if (!this.text.isEmpty()) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            int i = this.func_238516_r_(num);
            int j = Math.min(i, this.cursorPosition);
            int k = Math.max(i, this.cursorPosition);
            if (j != k) {
               String s = (new StringBuilder(this.text)).delete(j, k).toString();
               if (this.validator.test(s)) {
                  this.text = s;
                  this.setCursorPosition(j);
               }
            }
         }
      }
   }

   public int getNthWordFromCursor(int numWords) {
      return this.getNthWordFromPos(numWords, this.getCursorPosition());
   }

   private int getNthWordFromPos(int n, int pos) {
      return this.getNthWordFromPosWS(n, pos, true);
   }

   private int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
      int i = pos;
      boolean flag = n < 0;
      int j = Math.abs(n);

      for(int k = 0; k < j; ++k) {
         if (!flag) {
            int l = this.text.length();
            i = this.text.indexOf(32, i);
            if (i == -1) {
               i = l;
            } else {
               while(skipWs && i < l && this.text.charAt(i) == ' ') {
                  ++i;
               }
            }
         } else {
            while(skipWs && i > 0 && this.text.charAt(i - 1) == ' ') {
               --i;
            }

            while(i > 0 && this.text.charAt(i - 1) != ' ') {
               --i;
            }
         }
      }

      return i;
   }

   public void moveCursorBy(int num) {
      this.setCursorPosition(this.func_238516_r_(num));
   }

   private int func_238516_r_(int p_238516_1_) {
      return Util.func_240980_a_(this.text, this.cursorPosition, p_238516_1_);
   }

   public void setCursorPosition(int pos) {
      this.clampCursorPosition(pos);
      if (!this.field_212956_h) {
         this.setSelectionPos(this.cursorPosition);
      }

      this.func_212951_d(this.text);
   }

   public void clampCursorPosition(int p_212422_1_) {
      this.cursorPosition = MathHelper.clamp(p_212422_1_, 0, this.text.length());
   }

   public void setCursorPositionZero() {
      this.setCursorPosition(0);
   }

   public void setCursorPositionEnd() {
      this.setCursorPosition(this.text.length());
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (!this.func_212955_f()) {
         return false;
      } else {
         this.field_212956_h = Screen.func_231173_s_();
         if (Screen.func_231170_j_(p_231046_1_)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
         } else if (Screen.func_231169_i_(p_231046_1_)) {
            Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
            return true;
         } else if (Screen.func_231168_h_(p_231046_1_)) {
            if (this.isEnabled) {
               this.writeText(Minecraft.getInstance().keyboardListener.getClipboardString());
            }

            return true;
         } else if (Screen.func_231166_g_(p_231046_1_)) {
            Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
               this.writeText("");
            }

            return true;
         } else {
            switch(p_231046_1_) {
            case 259:
               if (this.isEnabled) {
                  this.field_212956_h = false;
                  this.delete(-1);
                  this.field_212956_h = Screen.func_231173_s_();
               }

               return true;
            case 260:
            case 264:
            case 265:
            case 266:
            case 267:
            default:
               return false;
            case 261:
               if (this.isEnabled) {
                  this.field_212956_h = false;
                  this.delete(1);
                  this.field_212956_h = Screen.func_231173_s_();
               }

               return true;
            case 262:
               if (Screen.func_231172_r_()) {
                  this.setCursorPosition(this.getNthWordFromCursor(1));
               } else {
                  this.moveCursorBy(1);
               }

               return true;
            case 263:
               if (Screen.func_231172_r_()) {
                  this.setCursorPosition(this.getNthWordFromCursor(-1));
               } else {
                  this.moveCursorBy(-1);
               }

               return true;
            case 268:
               this.setCursorPositionZero();
               return true;
            case 269:
               this.setCursorPositionEnd();
               return true;
            }
         }
      }
   }

   public boolean func_212955_f() {
      return this.getVisible() && this.func_230999_j_() && this.isEnabled();
   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      if (!this.func_212955_f()) {
         return false;
      } else if (SharedConstants.isAllowedCharacter(p_231042_1_)) {
         if (this.isEnabled) {
            this.writeText(Character.toString(p_231042_1_));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (!this.getVisible()) {
         return false;
      } else {
         boolean flag = p_231044_1_ >= (double)this.field_230690_l_ && p_231044_1_ < (double)(this.field_230690_l_ + this.field_230688_j_) && p_231044_3_ >= (double)this.field_230691_m_ && p_231044_3_ < (double)(this.field_230691_m_ + this.field_230689_k_);
         if (this.canLoseFocus) {
            this.setFocused2(flag);
         }

         if (this.func_230999_j_() && flag && p_231044_5_ == 0) {
            int i = MathHelper.floor(p_231044_1_) - this.field_230690_l_;
            if (this.enableBackgroundDrawing) {
               i -= 4;
            }

            String s = this.fontRenderer.func_238412_a_(this.text.substring(this.lineScrollOffset), this.getAdjustedWidth());
            this.setCursorPosition(this.fontRenderer.func_238412_a_(s, i).length() + this.lineScrollOffset);
            return true;
         } else {
            return false;
         }
      }
   }

   public void setFocused2(boolean isFocusedIn) {
      super.func_230996_d_(isFocusedIn);
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      if (this.getVisible()) {
         if (this.getEnableBackgroundDrawing()) {
            int i = this.func_230999_j_() ? -1 : -6250336;
            func_238467_a_(p_230431_1_, this.field_230690_l_ - 1, this.field_230691_m_ - 1, this.field_230690_l_ + this.field_230688_j_ + 1, this.field_230691_m_ + this.field_230689_k_ + 1, i);
            func_238467_a_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, this.field_230690_l_ + this.field_230688_j_, this.field_230691_m_ + this.field_230689_k_, -16777216);
         }

         int i2 = this.isEnabled ? this.enabledColor : this.disabledColor;
         int j = this.cursorPosition - this.lineScrollOffset;
         int k = this.selectionEnd - this.lineScrollOffset;
         String s = this.fontRenderer.func_238412_a_(this.text.substring(this.lineScrollOffset), this.getAdjustedWidth());
         boolean flag = j >= 0 && j <= s.length();
         boolean flag1 = this.func_230999_j_() && this.cursorCounter / 6 % 2 == 0 && flag;
         int l = this.enableBackgroundDrawing ? this.field_230690_l_ + 4 : this.field_230690_l_;
         int i1 = this.enableBackgroundDrawing ? this.field_230691_m_ + (this.field_230689_k_ - 8) / 2 : this.field_230691_m_;
         int j1 = l;
         if (k > s.length()) {
            k = s.length();
         }

         if (!s.isEmpty()) {
            String s1 = flag ? s.substring(0, j) : s;
            j1 = this.fontRenderer.func_238405_a_(p_230431_1_, this.textFormatter.apply(s1, this.lineScrollOffset), (float)l, (float)i1, i2);
         }

         boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
         int k1 = j1;
         if (!flag) {
            k1 = j > 0 ? l + this.field_230688_j_ : l;
         } else if (flag2) {
            k1 = j1 - 1;
            --j1;
         }

         if (!s.isEmpty() && flag && j < s.length()) {
            this.fontRenderer.func_238405_a_(p_230431_1_, this.textFormatter.apply(s.substring(j), this.cursorPosition), (float)j1, (float)i1, i2);
         }

         if (!flag2 && this.suggestion != null) {
            this.fontRenderer.func_238405_a_(p_230431_1_, this.suggestion, (float)(k1 - 1), (float)i1, -8355712);
         }

         if (flag1) {
            if (flag2) {
               AbstractGui.func_238467_a_(p_230431_1_, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
            } else {
               this.fontRenderer.func_238405_a_(p_230431_1_, "_", (float)k1, (float)i1, i2);
            }
         }

         if (k != j) {
            int l1 = l + this.fontRenderer.getStringWidth(s.substring(0, k));
            this.drawSelectionBox(k1, i1 - 1, l1 - 1, i1 + 1 + 9);
         }

      }
   }

   private void drawSelectionBox(int startX, int startY, int endX, int endY) {
      if (startX < endX) {
         int i = startX;
         startX = endX;
         endX = i;
      }

      if (startY < endY) {
         int j = startY;
         startY = endY;
         endY = j;
      }

      if (endX > this.field_230690_l_ + this.field_230688_j_) {
         endX = this.field_230690_l_ + this.field_230688_j_;
      }

      if (startX > this.field_230690_l_ + this.field_230688_j_) {
         startX = this.field_230690_l_ + this.field_230688_j_;
      }

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
      bufferbuilder.func_225582_a_((double)startX, (double)endY, 0.0D).endVertex();
      bufferbuilder.func_225582_a_((double)endX, (double)endY, 0.0D).endVertex();
      bufferbuilder.func_225582_a_((double)endX, (double)startY, 0.0D).endVertex();
      bufferbuilder.func_225582_a_((double)startX, (double)startY, 0.0D).endVertex();
      tessellator.draw();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   public void setMaxStringLength(int length) {
      this.maxStringLength = length;
      if (this.text.length() > length) {
         this.text = this.text.substring(0, length);
         this.func_212951_d(this.text);
      }

   }

   private int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int getCursorPosition() {
      return this.cursorPosition;
   }

   private boolean getEnableBackgroundDrawing() {
      return this.enableBackgroundDrawing;
   }

   public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn) {
      this.enableBackgroundDrawing = enableBackgroundDrawingIn;
   }

   public void setTextColor(int color) {
      this.enabledColor = color;
   }

   public void setDisabledTextColour(int color) {
      this.disabledColor = color;
   }

   public boolean func_231049_c__(boolean p_231049_1_) {
      return this.field_230694_p_ && this.isEnabled ? super.func_231049_c__(p_231049_1_) : false;
   }

   public boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
      return this.field_230694_p_ && p_231047_1_ >= (double)this.field_230690_l_ && p_231047_1_ < (double)(this.field_230690_l_ + this.field_230688_j_) && p_231047_3_ >= (double)this.field_230691_m_ && p_231047_3_ < (double)(this.field_230691_m_ + this.field_230689_k_);
   }

   protected void func_230995_c_(boolean p_230995_1_) {
      if (p_230995_1_) {
         this.cursorCounter = 0;
      }

   }

   private boolean isEnabled() {
      return this.isEnabled;
   }

   public void setEnabled(boolean enabled) {
      this.isEnabled = enabled;
   }

   public int getAdjustedWidth() {
      return this.getEnableBackgroundDrawing() ? this.field_230688_j_ - 8 : this.field_230688_j_;
   }

   public void setSelectionPos(int position) {
      int i = this.text.length();
      this.selectionEnd = MathHelper.clamp(position, 0, i);
      if (this.fontRenderer != null) {
         if (this.lineScrollOffset > i) {
            this.lineScrollOffset = i;
         }

         int j = this.getAdjustedWidth();
         String s = this.fontRenderer.func_238412_a_(this.text.substring(this.lineScrollOffset), j);
         int k = s.length() + this.lineScrollOffset;
         if (this.selectionEnd == this.lineScrollOffset) {
            this.lineScrollOffset -= this.fontRenderer.func_238413_a_(this.text, j, true).length();
         }

         if (this.selectionEnd > k) {
            this.lineScrollOffset += this.selectionEnd - k;
         } else if (this.selectionEnd <= this.lineScrollOffset) {
            this.lineScrollOffset -= this.lineScrollOffset - this.selectionEnd;
         }

         this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
      }

   }

   public void setCanLoseFocus(boolean canLoseFocusIn) {
      this.canLoseFocus = canLoseFocusIn;
   }

   public boolean getVisible() {
      return this.field_230694_p_;
   }

   public void setVisible(boolean isVisible) {
      this.field_230694_p_ = isVisible;
   }

   public void setSuggestion(@Nullable String p_195612_1_) {
      this.suggestion = p_195612_1_;
   }

   public int func_195611_j(int p_195611_1_) {
      return p_195611_1_ > this.text.length() ? this.field_230690_l_ : this.field_230690_l_ + this.fontRenderer.getStringWidth(this.text.substring(0, p_195611_1_));
   }

   public void setX(int p_212952_1_) {
      this.field_230690_l_ = p_212952_1_;
   }
}