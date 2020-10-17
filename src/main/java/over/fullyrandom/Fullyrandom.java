package over.fullyrandom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import over.fullyrandom.blocks.ModBlocks;
import over.fullyrandom.config.Config;
import over.fullyrandom.setup.ClientProxy;
import over.fullyrandom.setup.IProxy;
import over.fullyrandom.setup.ModSetup;
import over.fullyrandom.setup.ServerProxy;
import over.fullyrandom.world.biomes.ModBiomes;
import over.fullyrandom.world.gen.OreGeneration;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Fullyrandom.MODID)
public class Fullyrandom {

    public static IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "fullyrandom";

    public static ModSetup setup = new ModSetup();

    public Fullyrandom() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::done);

        Config.loadConfig(Config.server_config, FMLPaths.CONFIGDIR.get().resolve("fullyrandom-server.toml").toString());
        Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve("fullyrandom-client.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
        OreGeneration.setupOreFeatures();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        for (Object block: ModBlocks.oreBlocks) { RenderTypeLookup.setRenderLayer((Block) block, RenderType.getCutoutMipped()); }
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    private void done(final FMLLoadCompleteEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

}
