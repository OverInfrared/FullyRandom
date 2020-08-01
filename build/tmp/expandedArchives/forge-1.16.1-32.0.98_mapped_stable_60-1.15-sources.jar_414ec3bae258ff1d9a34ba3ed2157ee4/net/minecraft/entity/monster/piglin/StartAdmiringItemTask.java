package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.Items;
import net.minecraft.world.server.ServerWorld;

public class StartAdmiringItemTask<E extends PiglinEntity> extends Task<E> {
   public StartAdmiringItemTask() {
      super(ImmutableMap.of(MemoryModuleType.field_234080_N_, MemoryModuleStatus.VALUE_ABSENT));
   }

   protected boolean func_212832_a_(ServerWorld worldIn, E owner) {
      return !owner.getHeldItemOffhand().isEmpty() && owner.getHeldItemOffhand().getItem() != Items.SHIELD;
   }

   protected void func_212831_a_(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      PiglinTasks.func_234477_a_(entityIn, true);
   }
}