package net.minecraft.client.renderer;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemRenderer implements IResourceManagerReloadListener {
   public static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private static final Set<Item> ITEM_MODEL_BLACKLIST = Sets.newHashSet(Items.AIR);
   public float zLevel;
   private final ItemModelMesher itemModelMesher;
   private final TextureManager textureManager;
   private final ItemColors itemColors;

   public ItemRenderer(TextureManager textureManagerIn, ModelManager modelManagerIn, ItemColors itemColorsIn) {
      this.textureManager = textureManagerIn;
      this.itemModelMesher = new net.minecraftforge.client.ItemModelMesherForge(modelManagerIn);

      for(Item item : Registry.ITEM) {
         if (!ITEM_MODEL_BLACKLIST.contains(item)) {
            this.itemModelMesher.register(item, new ModelResourceLocation(Registry.ITEM.getKey(item), "inventory"));
         }
      }

      this.itemColors = itemColorsIn;
   }

   public ItemModelMesher getItemModelMesher() {
      return this.itemModelMesher;
   }

   public void func_229114_a_(IBakedModel p_229114_1_, ItemStack p_229114_2_, int p_229114_3_, int p_229114_4_, MatrixStack p_229114_5_, IVertexBuilder p_229114_6_) {
      Random random = new Random();
      long i = 42L;

      for(Direction direction : Direction.values()) {
         random.setSeed(42L);
         this.func_229112_a_(p_229114_5_, p_229114_6_, p_229114_1_.getQuads((BlockState)null, direction, random), p_229114_2_, p_229114_3_, p_229114_4_);
      }

      random.setSeed(42L);
      this.func_229112_a_(p_229114_5_, p_229114_6_, p_229114_1_.getQuads((BlockState)null, (Direction)null, random), p_229114_2_, p_229114_3_, p_229114_4_);
   }

   public void func_229111_a_(ItemStack p_229111_1_, ItemCameraTransforms.TransformType p_229111_2_, boolean p_229111_3_, MatrixStack p_229111_4_, IRenderTypeBuffer p_229111_5_, int p_229111_6_, int p_229111_7_, IBakedModel p_229111_8_) {
      if (!p_229111_1_.isEmpty()) {
         p_229111_4_.func_227860_a_();
         boolean flag = p_229111_2_ == ItemCameraTransforms.TransformType.GUI || p_229111_2_ == ItemCameraTransforms.TransformType.GROUND || p_229111_2_ == ItemCameraTransforms.TransformType.FIXED;
         if (p_229111_1_.getItem() == Items.TRIDENT && flag) {
            p_229111_8_ = this.itemModelMesher.getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
         }

         p_229111_8_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(p_229111_4_, p_229111_8_, p_229111_2_, p_229111_3_);
         p_229111_4_.func_227861_a_(-0.5D, -0.5D, -0.5D);
         if (!p_229111_8_.isBuiltInRenderer() && (p_229111_1_.getItem() != Items.TRIDENT || flag)) {
            boolean flag1;
            if (p_229111_2_ != ItemCameraTransforms.TransformType.GUI && !p_229111_2_.func_241716_a_() && p_229111_1_.getItem() instanceof BlockItem) {
               Block block = ((BlockItem)p_229111_1_.getItem()).getBlock();
               flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
            } else {
               flag1 = true;
            }
            if (p_229111_8_.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(this, p_229111_8_, p_229111_1_, p_229111_4_, p_229111_5_, p_229111_6_, p_229111_7_, flag1); }
            else {
            RenderType rendertype = RenderTypeLookup.func_239219_a_(p_229111_1_, flag1);
            IVertexBuilder ivertexbuilder;
            if (p_229111_1_.getItem() == Items.COMPASS && p_229111_1_.hasEffect()) {
               p_229111_4_.func_227860_a_();
               MatrixStack.Entry matrixstack$entry = p_229111_4_.func_227866_c_();
               if (p_229111_2_ == ItemCameraTransforms.TransformType.GUI) {
                  matrixstack$entry.func_227870_a_().func_226592_a_(0.5F);
               } else if (p_229111_2_.func_241716_a_()) {
                  matrixstack$entry.func_227870_a_().func_226592_a_(0.75F);
               }

               if (flag1) {
                  ivertexbuilder = func_241732_b_(p_229111_5_, rendertype, matrixstack$entry);
               } else {
                  ivertexbuilder = func_241731_a_(p_229111_5_, rendertype, matrixstack$entry);
               }

               p_229111_4_.func_227865_b_();
            } else if (flag1) {
               ivertexbuilder = func_239391_c_(p_229111_5_, rendertype, true, p_229111_1_.hasEffect());
            } else {
               ivertexbuilder = func_229113_a_(p_229111_5_, rendertype, true, p_229111_1_.hasEffect());
            }

            this.func_229114_a_(p_229111_8_, p_229111_1_, p_229111_6_, p_229111_7_, p_229111_4_, ivertexbuilder);
            }
         } else {
            p_229111_1_.getItem().getItemStackTileEntityRenderer().func_239207_a_(p_229111_1_, p_229111_2_, p_229111_4_, p_229111_5_, p_229111_6_, p_229111_7_);
         }

         p_229111_4_.func_227865_b_();
      }
   }

   public static IVertexBuilder func_239386_a_(IRenderTypeBuffer p_239386_0_, RenderType p_239386_1_, boolean p_239386_2_, boolean p_239386_3_) {
      return p_239386_3_ ? VertexBuilderUtils.func_227915_a_(p_239386_0_.getBuffer(p_239386_2_ ? RenderType.func_239270_k_() : RenderType.func_239271_l_()), p_239386_0_.getBuffer(p_239386_1_)) : p_239386_0_.getBuffer(p_239386_1_);
   }

   public static IVertexBuilder func_241731_a_(IRenderTypeBuffer p_241731_0_, RenderType p_241731_1_, MatrixStack.Entry p_241731_2_) {
      return VertexBuilderUtils.func_227915_a_(new MatrixApplyingVertexBuilder(p_241731_0_.getBuffer(RenderType.func_228653_j_()), p_241731_2_.func_227870_a_(), p_241731_2_.func_227872_b_()), p_241731_0_.getBuffer(p_241731_1_));
   }

   public static IVertexBuilder func_241732_b_(IRenderTypeBuffer p_241732_0_, RenderType p_241732_1_, MatrixStack.Entry p_241732_2_) {
      return VertexBuilderUtils.func_227915_a_(new MatrixApplyingVertexBuilder(p_241732_0_.getBuffer(RenderType.func_239273_n_()), p_241732_2_.func_227870_a_(), p_241732_2_.func_227872_b_()), p_241732_0_.getBuffer(p_241732_1_));
   }

   public static IVertexBuilder func_229113_a_(IRenderTypeBuffer p_229113_0_, RenderType p_229113_1_, boolean p_229113_2_, boolean p_229113_3_) {
      return p_229113_3_ ? VertexBuilderUtils.func_227915_a_(p_229113_0_.getBuffer(p_229113_2_ ? RenderType.func_228653_j_() : RenderType.func_228655_k_()), p_229113_0_.getBuffer(p_229113_1_)) : p_229113_0_.getBuffer(p_229113_1_);
   }

   public static IVertexBuilder func_239391_c_(IRenderTypeBuffer p_239391_0_, RenderType p_239391_1_, boolean p_239391_2_, boolean p_239391_3_) {
      return p_239391_3_ ? VertexBuilderUtils.func_227915_a_(p_239391_0_.getBuffer(p_239391_2_ ? RenderType.func_239273_n_() : RenderType.func_239274_p_()), p_239391_0_.getBuffer(p_239391_1_)) : p_239391_0_.getBuffer(p_239391_1_);
   }

   public void func_229112_a_(MatrixStack p_229112_1_, IVertexBuilder p_229112_2_, List<BakedQuad> p_229112_3_, ItemStack p_229112_4_, int p_229112_5_, int p_229112_6_) {
      boolean flag = !p_229112_4_.isEmpty();
      MatrixStack.Entry matrixstack$entry = p_229112_1_.func_227866_c_();

      for(BakedQuad bakedquad : p_229112_3_) {
         int i = -1;
         if (flag && bakedquad.hasTintIndex()) {
            i = this.itemColors.getColor(p_229112_4_, bakedquad.getTintIndex());
         }

         float f = (float)(i >> 16 & 255) / 255.0F;
         float f1 = (float)(i >> 8 & 255) / 255.0F;
         float f2 = (float)(i & 255) / 255.0F;
         p_229112_2_.addVertexData(matrixstack$entry, bakedquad, f, f1, f2, p_229112_5_, p_229112_6_, true);
      }

   }

   public IBakedModel getItemModelWithOverrides(ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entitylivingbaseIn) {
      Item item = stack.getItem();
      IBakedModel ibakedmodel;
      if (item == Items.TRIDENT) {
         ibakedmodel = this.itemModelMesher.getModelManager().getModel(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      } else {
         ibakedmodel = this.itemModelMesher.getItemModel(stack);
      }

      ClientWorld clientworld = worldIn instanceof ClientWorld ? (ClientWorld)worldIn : null;
      IBakedModel ibakedmodel1 = ibakedmodel.getOverrides().func_239290_a_(ibakedmodel, stack, clientworld, entitylivingbaseIn);
      return ibakedmodel1 == null ? this.itemModelMesher.getModelManager().getMissingModel() : ibakedmodel1;
   }

   public void func_229110_a_(ItemStack p_229110_1_, ItemCameraTransforms.TransformType p_229110_2_, int p_229110_3_, int p_229110_4_, MatrixStack p_229110_5_, IRenderTypeBuffer p_229110_6_) {
      this.func_229109_a_((LivingEntity)null, p_229110_1_, p_229110_2_, false, p_229110_5_, p_229110_6_, (World)null, p_229110_3_, p_229110_4_);
   }

   public void func_229109_a_(@Nullable LivingEntity p_229109_1_, ItemStack p_229109_2_, ItemCameraTransforms.TransformType p_229109_3_, boolean p_229109_4_, MatrixStack p_229109_5_, IRenderTypeBuffer p_229109_6_, @Nullable World p_229109_7_, int p_229109_8_, int p_229109_9_) {
      if (!p_229109_2_.isEmpty()) {
         IBakedModel ibakedmodel = this.getItemModelWithOverrides(p_229109_2_, p_229109_7_, p_229109_1_);
         this.func_229111_a_(p_229109_2_, p_229109_3_, p_229109_4_, p_229109_5_, p_229109_6_, p_229109_8_, p_229109_9_, ibakedmodel);
      }
   }

   public void renderItemIntoGUI(ItemStack stack, int x, int y) {
      this.renderItemModelIntoGUI(stack, x, y, this.getItemModelWithOverrides(stack, (World)null, (LivingEntity)null));
   }

   protected void renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
      RenderSystem.pushMatrix();
      this.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      this.textureManager.func_229267_b_(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, false);
      RenderSystem.enableRescaleNormal();
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.translatef((float)x, (float)y, 100.0F + this.zLevel);
      RenderSystem.translatef(8.0F, 8.0F, 0.0F);
      RenderSystem.scalef(1.0F, -1.0F, 1.0F);
      RenderSystem.scalef(16.0F, 16.0F, 16.0F);
      MatrixStack matrixstack = new MatrixStack();
      IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().func_228019_au_().func_228487_b_();
      boolean flag = !bakedmodel.func_230044_c_();
      if (flag) {
         RenderHelper.func_227783_c_();
      }

      this.func_229111_a_(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.field_229196_a_, bakedmodel);
      irendertypebuffer$impl.func_228461_a_();
      RenderSystem.enableDepthTest();
      if (flag) {
         RenderHelper.func_227784_d_();
      }

      RenderSystem.disableAlphaTest();
      RenderSystem.disableRescaleNormal();
      RenderSystem.popMatrix();
   }

   public void renderItemAndEffectIntoGUI(ItemStack stack, int xPosition, int yPosition) {
      this.func_239387_b_(Minecraft.getInstance().player, stack, xPosition, yPosition);
   }

   public void func_239390_c_(ItemStack p_239390_1_, int p_239390_2_, int p_239390_3_) {
      this.func_239387_b_((LivingEntity)null, p_239390_1_, p_239390_2_, p_239390_3_);
   }

   public void renderItemAndEffectIntoGUI(LivingEntity entityIn, ItemStack itemIn, int x, int y) {
      this.func_239387_b_(entityIn, itemIn, x, y);
   }

   private void func_239387_b_(@Nullable LivingEntity p_239387_1_, ItemStack p_239387_2_, int p_239387_3_, int p_239387_4_) {
      if (!p_239387_2_.isEmpty()) {
         this.zLevel += 50.0F;

         try {
            this.renderItemModelIntoGUI(p_239387_2_, p_239387_3_, p_239387_4_, this.getItemModelWithOverrides(p_239387_2_, (World)null, p_239387_1_));
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
            crashreportcategory.func_189529_a("Item Type", () -> {
               return String.valueOf((Object)p_239387_2_.getItem());
            });
            crashreportcategory.func_189529_a("Registry Name", () -> String.valueOf(p_239387_2_.getItem().getRegistryName()));
            crashreportcategory.func_189529_a("Item Damage", () -> {
               return String.valueOf(p_239387_2_.getDamage());
            });
            crashreportcategory.func_189529_a("Item NBT", () -> {
               return String.valueOf((Object)p_239387_2_.getTag());
            });
            crashreportcategory.func_189529_a("Item Foil", () -> {
               return String.valueOf(p_239387_2_.hasEffect());
            });
            throw new ReportedException(crashreport);
         }

         this.zLevel -= 50.0F;
      }
   }

   public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition) {
      this.renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, (String)null);
   }

   public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text) {
      if (!stack.isEmpty()) {
         MatrixStack matrixstack = new MatrixStack();
         if (stack.getCount() != 1 || text != null) {
            String s = text == null ? String.valueOf(stack.getCount()) : text;
            matrixstack.func_227861_a_(0.0D, 0.0D, (double)(this.zLevel + 200.0F));
            IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.func_228455_a_(Tessellator.getInstance().getBuffer());
            fr.func_228079_a_(s, (float)(xPosition + 19 - 2 - fr.getStringWidth(s)), (float)(yPosition + 6 + 3), 16777215, true, matrixstack.func_227866_c_().func_227870_a_(), irendertypebuffer$impl, false, 0, 15728880);
            irendertypebuffer$impl.func_228461_a_();
         }

         if (stack.getItem().showDurabilityBar(stack)) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            double health = stack.getItem().getDurabilityForDisplay(stack);
            int i = Math.round(13.0F - (float)health * 13.0F);
            int j = stack.getItem().getRGBDurabilityForDisplay(stack);
            this.draw(bufferbuilder, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
            this.draw(bufferbuilder, xPosition + 2, yPosition + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }

         ClientPlayerEntity clientplayerentity = Minecraft.getInstance().player;
         float f3 = clientplayerentity == null ? 0.0F : clientplayerentity.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getInstance().getRenderPartialTicks());
         if (f3 > 0.0F) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Tessellator tessellator1 = Tessellator.getInstance();
            BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
            this.draw(bufferbuilder1, xPosition, yPosition + MathHelper.floor(16.0F * (1.0F - f3)), 16, MathHelper.ceil(16.0F * f3), 255, 255, 255, 127);
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }

      }
   }

   private void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
      renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      renderer.func_225582_a_((double)(x + 0), (double)(y + 0), 0.0D).func_225586_a_(red, green, blue, alpha).endVertex();
      renderer.func_225582_a_((double)(x + 0), (double)(y + height), 0.0D).func_225586_a_(red, green, blue, alpha).endVertex();
      renderer.func_225582_a_((double)(x + width), (double)(y + height), 0.0D).func_225586_a_(red, green, blue, alpha).endVertex();
      renderer.func_225582_a_((double)(x + width), (double)(y + 0), 0.0D).func_225586_a_(red, green, blue, alpha).endVertex();
      Tessellator.getInstance().draw();
   }

   public void onResourceManagerReload(IResourceManager resourceManager) {
      this.itemModelMesher.rebuildCache();
   }

   @Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }
}