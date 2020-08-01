package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.WorkingScreen;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.FilePack;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.VanillaPack;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class DownloadingPackFinder implements IPackFinder {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern field_195752_b = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final VanillaPack vanillaPack;
   private final File field_195754_d;
   private final ReentrantLock field_195755_e = new ReentrantLock();
   private final ResourceIndex field_217819_f;
   @Nullable
   private CompletableFuture<?> field_195756_f;
   @Nullable
   private ClientResourcePackInfo field_195757_g;

   public DownloadingPackFinder(File p_i48116_1_, ResourceIndex p_i48116_2_) {
      this.field_195754_d = p_i48116_1_;
      this.field_217819_f = p_i48116_2_;
      this.vanillaPack = new VirtualAssetsPack(p_i48116_2_);
   }

   public <T extends ResourcePackInfo> void func_230230_a_(Consumer<T> p_230230_1_, ResourcePackInfo.IFactory<T> p_230230_2_) {
      T t = ResourcePackInfo.createResourcePack("vanilla", true, () -> {
         return this.vanillaPack;
      }, p_230230_2_, ResourcePackInfo.Priority.BOTTOM, IPackNameDecorator.field_232626_b_);
      if (t != null) {
         p_230230_1_.accept(t);
      }

      if (this.field_195757_g != null) {
         p_230230_1_.accept((T)this.field_195757_g);
      }

      T t1 = this.func_239453_a_(p_230230_2_);
      if (t1 != null) {
         p_230230_1_.accept(t1);
      }

   }

   public VanillaPack getVanillaPack() {
      return this.vanillaPack;
   }

   public static Map<String, String> func_195742_b() {
      Map<String, String> map = Maps.newHashMap();
      map.put("X-Minecraft-Username", Minecraft.getInstance().getSession().getUsername());
      map.put("X-Minecraft-UUID", Minecraft.getInstance().getSession().getPlayerID());
      map.put("X-Minecraft-Version", SharedConstants.getVersion().getName());
      map.put("X-Minecraft-Version-ID", SharedConstants.getVersion().getId());
      map.put("X-Minecraft-Pack-Format", String.valueOf(SharedConstants.getVersion().getPackVersion()));
      map.put("User-Agent", "Minecraft Java/" + SharedConstants.getVersion().getName());
      return map;
   }

   public CompletableFuture<?> func_217818_a(String p_217818_1_, String p_217818_2_) {
      String s = DigestUtils.sha1Hex(p_217818_1_);
      String s1 = field_195752_b.matcher(p_217818_2_).matches() ? p_217818_2_ : "";
      this.field_195755_e.lock();

      CompletableFuture completablefuture1;
      try {
         this.clearResourcePack();
         this.func_195747_e();
         File file1 = new File(this.field_195754_d, s);
         CompletableFuture<?> completablefuture;
         if (file1.exists()) {
            completablefuture = CompletableFuture.completedFuture("");
         } else {
            WorkingScreen workingscreen = new WorkingScreen();
            Map<String, String> map = func_195742_b();
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.runImmediately(() -> {
               minecraft.displayGuiScreen(workingscreen);
            });
            completablefuture = HTTPUtil.downloadResourcePack(file1, p_217818_1_, map, 104857600, workingscreen, minecraft.getProxy());
         }

         this.field_195756_f = completablefuture.thenCompose((p_217812_3_) -> {
            return !this.func_195745_a(s1, file1) ? Util.completedExceptionallyFuture(new RuntimeException("Hash check failure for file " + file1 + ", see log")) : this.func_217816_a(file1, IPackNameDecorator.field_232628_d_);
         }).whenComplete((p_217815_1_, p_217815_2_) -> {
            if (p_217815_2_ != null) {
               LOGGER.warn("Pack application failed: {}, deleting file {}", p_217815_2_.getMessage(), file1);
               func_217811_b(file1);
            }

         });
         completablefuture1 = this.field_195756_f;
      } finally {
         this.field_195755_e.unlock();
      }

      return completablefuture1;
   }

   private static void func_217811_b(File p_217811_0_) {
      try {
         Files.delete(p_217811_0_.toPath());
      } catch (IOException ioexception) {
         LOGGER.warn("Failed to delete file {}: {}", p_217811_0_, ioexception.getMessage());
      }

   }

   public void clearResourcePack() {
      this.field_195755_e.lock();

      try {
         if (this.field_195756_f != null) {
            this.field_195756_f.cancel(true);
         }

         this.field_195756_f = null;
         if (this.field_195757_g != null) {
            this.field_195757_g = null;
            Minecraft.getInstance().func_213245_w();
         }
      } finally {
         this.field_195755_e.unlock();
      }

   }

   private boolean func_195745_a(String p_195745_1_, File p_195745_2_) {
      try (FileInputStream fileinputstream = new FileInputStream(p_195745_2_)) {
         String s = DigestUtils.sha1Hex((InputStream)fileinputstream);
         if (p_195745_1_.isEmpty()) {
            LOGGER.info("Found file {} without verification hash", (Object)p_195745_2_);
            return true;
         }

         if (s.toLowerCase(Locale.ROOT).equals(p_195745_1_.toLowerCase(Locale.ROOT))) {
            LOGGER.info("Found file {} matching requested hash {}", p_195745_2_, p_195745_1_);
            return true;
         }

         LOGGER.warn("File {} had wrong hash (expected {}, found {}).", p_195745_2_, p_195745_1_, s);
      } catch (IOException ioexception) {
         LOGGER.warn("File {} couldn't be hashed.", p_195745_2_, ioexception);
      }

      return false;
   }

   private void func_195747_e() {
      try {
         List<File> list = Lists.newArrayList(FileUtils.listFiles(this.field_195754_d, TrueFileFilter.TRUE, (IOFileFilter)null));
         list.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int i = 0;

         for(File file1 : list) {
            if (i++ >= 10) {
               LOGGER.info("Deleting old server resource pack {}", (Object)file1.getName());
               FileUtils.deleteQuietly(file1);
            }
         }
      } catch (IllegalArgumentException illegalargumentexception) {
         LOGGER.error("Error while deleting old server resource pack : {}", (Object)illegalargumentexception.getMessage());
      }

   }

   public CompletableFuture<Void> func_217816_a(File p_217816_1_, IPackNameDecorator p_217816_2_) {
      PackMetadataSection packmetadatasection;
      NativeImage nativeimage;
      try (FilePack filepack = new FilePack(p_217816_1_)) {
         packmetadatasection = filepack.getMetadata(PackMetadataSection.SERIALIZER);
         nativeimage = ClientResourcePackInfo.func_239491_a_(filepack);
      } catch (IOException ioexception) {
         return Util.completedExceptionallyFuture(new IOException(String.format("Invalid resourcepack at %s", p_217816_1_), ioexception));
      }

      LOGGER.info("Applying server pack {}", (Object)p_217816_1_);
      this.field_195757_g = new ClientResourcePackInfo("server", true, () -> {
         return new FilePack(p_217816_1_);
      }, new TranslationTextComponent("resourcePack.server.name"), packmetadatasection.getDescription(), PackCompatibility.getCompatibility(packmetadatasection.getPackFormat()), ResourcePackInfo.Priority.TOP, true, p_217816_2_, nativeimage);
      return Minecraft.getInstance().func_213245_w();
   }

   @Nullable
   private <T extends ResourcePackInfo> T func_239453_a_(ResourcePackInfo.IFactory<T> p_239453_1_) {
      T t = null;
      File file1 = this.field_217819_f.getFile(new ResourceLocation("resourcepacks/programmer_art.zip"));
      if (file1 != null && file1.isFile()) {
         t = func_239454_a_(p_239453_1_, () -> {
            return func_239460_c_(file1);
         });
      }

      if (t == null && SharedConstants.developmentMode) {
         File file2 = this.field_217819_f.func_225638_a_("../resourcepacks/programmer_art");
         if (file2 != null && file2.isDirectory()) {
            t = func_239454_a_(p_239453_1_, () -> {
               return func_239459_b_(file2);
            });
         }
      }

      return t;
   }

   @Nullable
   private static <T extends ResourcePackInfo> T func_239454_a_(ResourcePackInfo.IFactory<T> p_239454_0_, Supplier<IResourcePack> p_239454_1_) {
      return ResourcePackInfo.createResourcePack("programer_art", false, p_239454_1_, p_239454_0_, ResourcePackInfo.Priority.TOP, IPackNameDecorator.field_232626_b_);
   }

   private static FolderPack func_239459_b_(File p_239459_0_) {
      return new FolderPack(p_239459_0_) {
         public String getName() {
            return "Programmer Art";
         }
      };
   }

   private static IResourcePack func_239460_c_(File p_239460_0_) {
      return new FilePack(p_239460_0_) {
         public String getName() {
            return "Programmer Art";
         }
      };
   }
}