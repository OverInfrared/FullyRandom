package net.minecraft.client.gui.fonts.providers;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.DefaultGlyph;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefaultGlyphProvider implements IGlyphProvider {
   @Nullable
   public IGlyphInfo func_212248_a(int p_212248_1_) {
      return DefaultGlyph.INSTANCE;
   }

   public IntSet func_230428_a_() {
      return IntSets.EMPTY_SET;
   }
}