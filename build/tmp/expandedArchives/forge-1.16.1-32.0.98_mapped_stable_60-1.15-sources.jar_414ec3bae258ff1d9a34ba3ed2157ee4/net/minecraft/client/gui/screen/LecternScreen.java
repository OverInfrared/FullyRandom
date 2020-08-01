package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.LecternContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LecternScreen extends ReadBookScreen implements IHasContainer<LecternContainer> {
   private final LecternContainer field_214182_c;
   private final IContainerListener field_214183_d = new IContainerListener() {
      public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
         LecternScreen.this.func_214175_g();
      }

      public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
         LecternScreen.this.func_214175_g();
      }

      public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
         if (varToUpdate == 0) {
            LecternScreen.this.func_214176_h();
         }

      }
   };

   public LecternScreen(LecternContainer p_i51082_1_, PlayerInventory p_i51082_2_, ITextComponent p_i51082_3_) {
      this.field_214182_c = p_i51082_1_;
   }

   public LecternContainer getContainer() {
      return this.field_214182_c;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_214182_c.addListener(this.field_214183_d);
   }

   public void func_231175_as__() {
      this.field_230706_i_.player.closeScreen();
      super.func_231175_as__();
   }

   public void func_231164_f_() {
      super.func_231164_f_();
      this.field_214182_c.removeListener(this.field_214183_d);
   }

   protected void addDoneButton() {
      if (this.field_230706_i_.player.isAllowEdit()) {
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, 196, 98, 20, DialogTexts.field_240632_c_, (p_214181_1_) -> {
            this.field_230706_i_.displayGuiScreen((Screen)null);
         }));
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 2, 196, 98, 20, new TranslationTextComponent("lectern.take_book"), (p_214178_1_) -> {
            this.func_214179_c(3);
         }));
      } else {
         super.addDoneButton();
      }

   }

   protected void previousPage() {
      this.func_214179_c(1);
   }

   protected void nextPage() {
      this.func_214179_c(2);
   }

   protected boolean showPage2(int pageNum) {
      if (pageNum != this.field_214182_c.getPage()) {
         this.func_214179_c(100 + pageNum);
         return true;
      } else {
         return false;
      }
   }

   private void func_214179_c(int p_214179_1_) {
      this.field_230706_i_.playerController.sendEnchantPacket(this.field_214182_c.windowId, p_214179_1_);
   }

   public boolean func_231177_au__() {
      return false;
   }

   private void func_214175_g() {
      ItemStack itemstack = this.field_214182_c.getBook();
      this.func_214155_a(ReadBookScreen.IBookInfo.func_216917_a(itemstack));
   }

   private void func_214176_h() {
      this.showPage(this.field_214182_c.getPage());
   }
}