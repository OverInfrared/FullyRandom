package net.minecraft.client.gui.fonts;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Font implements AutoCloseable {
   private static final EmptyGlyph field_212460_b = new EmptyGlyph();
   private static final IGlyph field_212461_c = () -> {
      return 4.0F;
   };
   private static final Random RANDOM = new Random();
   private final TextureManager textureManager;
   private final ResourceLocation id;
   private TexturedGlyph fallbackGlyph;
   private TexturedGlyph field_228156_h_;
   private final List<IGlyphProvider> glyphProviders = Lists.newArrayList();
   private final Int2ObjectMap<TexturedGlyph> field_212463_j = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<IGlyph> glyphs = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<IntList> glyphsByWidth = new Int2ObjectOpenHashMap<>();
   private final List<FontTexture> textures = Lists.newArrayList();

   public Font(TextureManager textureManagerIn, ResourceLocation resourceLocationIn) {
      this.textureManager = textureManagerIn;
      this.id = resourceLocationIn;
   }

   public void setGlyphProviders(List<IGlyphProvider> glyphProvidersIn) {
      this.func_230154_b_();
      this.deleteTextures();
      this.field_212463_j.clear();
      this.glyphs.clear();
      this.glyphsByWidth.clear();
      this.fallbackGlyph = this.createTexturedGlyph(DefaultGlyph.INSTANCE);
      this.field_228156_h_ = this.createTexturedGlyph(WhiteGlyph.INSTANCE);
      IntSet intset = new IntOpenHashSet();

      for(IGlyphProvider iglyphprovider : glyphProvidersIn) {
         intset.addAll(iglyphprovider.func_230428_a_());
      }

      Set<IGlyphProvider> set = Sets.newHashSet();
      intset.forEach((int p_238558_3_) -> {
         for(IGlyphProvider iglyphprovider1 : glyphProvidersIn) {
            IGlyph iglyph = (IGlyph)(p_238558_3_ == 32 ? field_212461_c : iglyphprovider1.func_212248_a(p_238558_3_));
            if (iglyph != null) {
               set.add(iglyphprovider1);
               if (iglyph != DefaultGlyph.INSTANCE) {
                  this.glyphsByWidth.computeIfAbsent(MathHelper.ceil(iglyph.getAdvance(false)), (p_238563_0_) -> {
                     return new IntArrayList();
                  }).add(p_238558_3_);
               }
               break;
            }
         }

      });
      glyphProvidersIn.stream().filter(set::contains).forEach(this.glyphProviders::add);
   }

   public void close() {
      this.func_230154_b_();
      this.deleteTextures();
   }

   private void func_230154_b_() {
      for(IGlyphProvider iglyphprovider : this.glyphProviders) {
         iglyphprovider.close();
      }

      this.glyphProviders.clear();
   }

   private void deleteTextures() {
      for(FontTexture fonttexture : this.textures) {
         fonttexture.close();
      }

      this.textures.clear();
   }

   public IGlyph func_238557_a_(int p_238557_1_) {
      return this.glyphs.computeIfAbsent(p_238557_1_, (p_212457_1_) -> {
         return (IGlyph)(p_212457_1_ == 32 ? field_212461_c : this.func_212455_c(p_212457_1_));
      });
   }

   private IGlyphInfo func_212455_c(int p_212455_1_) {
      for(IGlyphProvider iglyphprovider : this.glyphProviders) {
         IGlyphInfo iglyphinfo = iglyphprovider.func_212248_a(p_212455_1_);
         if (iglyphinfo != null) {
            return iglyphinfo;
         }
      }

      return DefaultGlyph.INSTANCE;
   }

   public TexturedGlyph func_238559_b_(int p_238559_1_) {
      return this.field_212463_j.computeIfAbsent(p_238559_1_, (p_212458_1_) -> {
         return (TexturedGlyph)(p_212458_1_ == 32 ? field_212460_b : this.createTexturedGlyph(this.func_212455_c(p_212458_1_)));
      });
   }

   private TexturedGlyph createTexturedGlyph(IGlyphInfo glyphInfoIn) {
      for(FontTexture fonttexture : this.textures) {
         TexturedGlyph texturedglyph = fonttexture.createTexturedGlyph(glyphInfoIn);
         if (texturedglyph != null) {
            return texturedglyph;
         }
      }

      FontTexture fonttexture1 = new FontTexture(new ResourceLocation(this.id.getNamespace(), this.id.getPath() + "/" + this.textures.size()), glyphInfoIn.isColored());
      this.textures.add(fonttexture1);
      this.textureManager.func_229263_a_(fonttexture1.getTextureLocation(), fonttexture1);
      TexturedGlyph texturedglyph1 = fonttexture1.createTexturedGlyph(glyphInfoIn);
      return texturedglyph1 == null ? this.fallbackGlyph : texturedglyph1;
   }

   public TexturedGlyph obfuscate(IGlyph glyph) {
      IntList intlist = this.glyphsByWidth.get(MathHelper.ceil(glyph.getAdvance(false)));
      return intlist != null && !intlist.isEmpty() ? this.func_238559_b_(intlist.getInt(RANDOM.nextInt(intlist.size()))) : this.fallbackGlyph;
   }

   public TexturedGlyph func_228157_b_() {
      return this.field_228156_h_;
   }
}