package net.minecraft.world.storage;

import net.minecraft.util.math.BlockPos;

public interface ISpawnWorldInfo extends IWorldInfo {
   void setSpawnX(int x);

   void setSpawnY(int y);

   void setSpawnZ(int z);

   default void setSpawn(BlockPos spawnPoint) {
      this.setSpawnX(spawnPoint.getX());
      this.setSpawnY(spawnPoint.getY());
      this.setSpawnZ(spawnPoint.getZ());
   }
}