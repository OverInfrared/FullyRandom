package net.minecraft.command;

import java.util.UUID;
import net.minecraft.util.text.ITextComponent;

public interface ICommandSource {
   ICommandSource field_213139_a_ = new ICommandSource() {
      public void sendMessage(ITextComponent component, UUID p_145747_2_) {
      }

      public boolean shouldReceiveFeedback() {
         return false;
      }

      public boolean shouldReceiveErrors() {
         return false;
      }

      public boolean allowLogging() {
         return false;
      }
   };

   void sendMessage(ITextComponent component, UUID p_145747_2_);

   boolean shouldReceiveFeedback();

   boolean shouldReceiveErrors();

   boolean allowLogging();
}