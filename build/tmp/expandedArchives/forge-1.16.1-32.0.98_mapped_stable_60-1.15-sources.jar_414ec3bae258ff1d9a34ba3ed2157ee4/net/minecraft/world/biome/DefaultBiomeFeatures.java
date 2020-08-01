package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.OptionalInt;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.AxisRotatingBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.ForestFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.ColumnConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.feature.NetherackBlobReplacementFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.ThreeLayerFeature;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.feature.structure.BastionRemnantConfig;
import net.minecraft.world.gen.feature.structure.BastionRemnantsPieces;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.RuinedPortalStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.JungleFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.CocoaTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class DefaultBiomeFeatures {
   private static final BlockState field_226769_ad_ = Blocks.GRASS.getDefaultState();
   private static final BlockState field_226770_ae_ = Blocks.FERN.getDefaultState();
   private static final BlockState field_226771_af_ = Blocks.PODZOL.getDefaultState();
   private static final BlockState field_226772_ag_ = Blocks.OAK_LOG.getDefaultState();
   private static final BlockState field_226773_ah_ = Blocks.OAK_LEAVES.getDefaultState();
   private static final BlockState field_226774_ai_ = Blocks.JUNGLE_LOG.getDefaultState();
   private static final BlockState field_226775_aj_ = Blocks.JUNGLE_LEAVES.getDefaultState();
   private static final BlockState field_226776_ak_ = Blocks.SPRUCE_LOG.getDefaultState();
   private static final BlockState field_226777_al_ = Blocks.SPRUCE_LEAVES.getDefaultState();
   private static final BlockState field_226778_am_ = Blocks.ACACIA_LOG.getDefaultState();
   private static final BlockState field_226779_an_ = Blocks.ACACIA_LEAVES.getDefaultState();
   private static final BlockState field_226780_ao_ = Blocks.BIRCH_LOG.getDefaultState();
   private static final BlockState field_226781_ap_ = Blocks.BIRCH_LEAVES.getDefaultState();
   private static final BlockState field_226782_aq_ = Blocks.DARK_OAK_LOG.getDefaultState();
   private static final BlockState field_226783_ar_ = Blocks.DARK_OAK_LEAVES.getDefaultState();
   private static final BlockState field_226784_as_ = Blocks.WATER.getDefaultState();
   private static final BlockState field_226785_at_ = Blocks.LAVA.getDefaultState();
   private static final BlockState field_226786_au_ = Blocks.DIRT.getDefaultState();
   private static final BlockState field_226787_av_ = Blocks.GRAVEL.getDefaultState();
   private static final BlockState field_226788_aw_ = Blocks.GRANITE.getDefaultState();
   private static final BlockState field_226789_ax_ = Blocks.DIORITE.getDefaultState();
   private static final BlockState field_226790_ay_ = Blocks.ANDESITE.getDefaultState();
   private static final BlockState field_226791_az_ = Blocks.COAL_ORE.getDefaultState();
   private static final BlockState field_226740_aA_ = Blocks.IRON_ORE.getDefaultState();
   private static final BlockState field_226741_aB_ = Blocks.GOLD_ORE.getDefaultState();
   private static final BlockState field_226742_aC_ = Blocks.REDSTONE_ORE.getDefaultState();
   private static final BlockState field_226743_aD_ = Blocks.DIAMOND_ORE.getDefaultState();
   private static final BlockState field_226744_aE_ = Blocks.LAPIS_ORE.getDefaultState();
   private static final BlockState field_226745_aF_ = Blocks.STONE.getDefaultState();
   private static final BlockState field_226746_aG_ = Blocks.EMERALD_ORE.getDefaultState();
   private static final BlockState field_226747_aH_ = Blocks.INFESTED_STONE.getDefaultState();
   private static final BlockState field_226748_aI_ = Blocks.SAND.getDefaultState();
   private static final BlockState field_226749_aJ_ = Blocks.CLAY.getDefaultState();
   private static final BlockState field_226750_aK_ = Blocks.GRASS_BLOCK.getDefaultState();
   private static final BlockState field_226751_aL_ = Blocks.MOSSY_COBBLESTONE.getDefaultState();
   private static final BlockState field_226752_aM_ = Blocks.LARGE_FERN.getDefaultState();
   private static final BlockState field_226753_aN_ = Blocks.TALL_GRASS.getDefaultState();
   private static final BlockState field_226754_aO_ = Blocks.LILAC.getDefaultState();
   private static final BlockState field_226755_aP_ = Blocks.ROSE_BUSH.getDefaultState();
   private static final BlockState field_226756_aQ_ = Blocks.PEONY.getDefaultState();
   private static final BlockState field_226757_aR_ = Blocks.BROWN_MUSHROOM.getDefaultState();
   private static final BlockState field_226758_aS_ = Blocks.RED_MUSHROOM.getDefaultState();
   private static final BlockState field_226759_aT_ = Blocks.SEAGRASS.getDefaultState();
   private static final BlockState field_226760_aU_ = Blocks.PACKED_ICE.getDefaultState();
   private static final BlockState field_226761_aV_ = Blocks.BLUE_ICE.getDefaultState();
   private static final BlockState field_226762_aW_ = Blocks.LILY_OF_THE_VALLEY.getDefaultState();
   private static final BlockState field_226763_aX_ = Blocks.BLUE_ORCHID.getDefaultState();
   private static final BlockState field_226764_aY_ = Blocks.POPPY.getDefaultState();
   private static final BlockState field_226765_aZ_ = Blocks.DANDELION.getDefaultState();
   private static final BlockState field_226793_ba_ = Blocks.DEAD_BUSH.getDefaultState();
   private static final BlockState field_226794_bb_ = Blocks.MELON.getDefaultState();
   private static final BlockState field_226795_bc_ = Blocks.PUMPKIN.getDefaultState();
   private static final BlockState field_226796_bd_ = Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, Integer.valueOf(3));
   private static final BlockState field_226797_be_ = Blocks.FIRE.getDefaultState();
   private static final BlockState field_235153_cd_ = Blocks.field_235335_bO_.getDefaultState();
   private static final BlockState field_226798_bf_ = Blocks.NETHERRACK.getDefaultState();
   private static final BlockState field_235154_cf_ = Blocks.field_235336_cN_.getDefaultState();
   private static final BlockState field_235155_cg_ = Blocks.field_235343_mB_.getDefaultState();
   private static final BlockState field_226799_bg_ = Blocks.LILY_PAD.getDefaultState();
   private static final BlockState field_226800_bh_ = Blocks.SNOW.getDefaultState();
   private static final BlockState field_226801_bi_ = Blocks.JACK_O_LANTERN.getDefaultState();
   private static final BlockState field_226802_bj_ = Blocks.SUNFLOWER.getDefaultState();
   private static final BlockState field_226803_bk_ = Blocks.CACTUS.getDefaultState();
   private static final BlockState field_226804_bl_ = Blocks.SUGAR_CANE.getDefaultState();
   private static final BlockState field_226805_bm_ = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
   private static final BlockState field_226806_bn_ = Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.UP, Boolean.valueOf(true)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
   private static final BlockState field_226807_bo_ = Blocks.MUSHROOM_STEM.getDefaultState().with(HugeMushroomBlock.UP, Boolean.valueOf(false)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
   private static final BlockState field_235156_cq_ = Blocks.field_235334_I_.getDefaultState();
   private static final BlockState field_235157_cr_ = Blocks.NETHER_QUARTZ_ORE.getDefaultState();
   private static final BlockState field_235158_cs_ = Blocks.field_235368_mh_.getDefaultState();
   private static final BlockState field_235159_ct_ = Blocks.field_235374_mn_.getDefaultState();
   private static final BlockState field_235160_cu_ = Blocks.NETHER_WART_BLOCK.getDefaultState();
   private static final BlockState field_235161_cv_ = Blocks.field_235377_mq_.getDefaultState();
   private static final BlockState field_235162_cw_ = Blocks.field_235383_mw_.getDefaultState();
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235134_a_ = Structure.field_236366_b_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<MineshaftConfig, ? extends Structure<MineshaftConfig>> field_235150_b_ = Structure.field_236367_c_.func_236391_a_(new MineshaftConfig((double)0.004F, MineshaftStructure.Type.NORMAL));
   public static final StructureFeature<MineshaftConfig, ? extends Structure<MineshaftConfig>> field_235152_c_ = Structure.field_236367_c_.func_236391_a_(new MineshaftConfig(0.004D, MineshaftStructure.Type.MESA));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235166_d_ = Structure.field_236368_d_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235167_e_ = Structure.field_236369_e_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235168_f_ = Structure.field_236370_f_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235169_g_ = Structure.field_236371_g_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<ShipwreckConfig, ? extends Structure<ShipwreckConfig>> field_235170_h_ = Structure.field_236373_i_.func_236391_a_(new ShipwreckConfig(false));
   public static final StructureFeature<ShipwreckConfig, ? extends Structure<ShipwreckConfig>> field_235171_i_ = Structure.field_236373_i_.func_236391_a_(new ShipwreckConfig(true));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235172_j_ = Structure.field_236374_j_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235173_k_ = Structure.field_236375_k_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235174_l_ = Structure.field_236376_l_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<OceanRuinConfig, ? extends Structure<OceanRuinConfig>> field_235175_m_ = Structure.field_236377_m_.func_236391_a_(new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F));
   public static final StructureFeature<OceanRuinConfig, ? extends Structure<OceanRuinConfig>> field_235176_n_ = Structure.field_236377_m_.func_236391_a_(new OceanRuinConfig(OceanRuinStructure.Type.WARM, 0.3F, 0.9F));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235177_o_ = Structure.field_236378_n_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235178_p_ = Structure.field_236382_r_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> field_235179_q_ = Structure.field_236379_o_.func_236391_a_(NoFeatureConfig.field_236559_b_);
   public static final StructureFeature<BuriedTreasureConfig, ? extends Structure<BuriedTreasureConfig>> field_235180_r_ = Structure.field_236380_p_.func_236391_a_(new BuriedTreasureConfig(0.01F));
   public static final StructureFeature<BastionRemnantConfig, ? extends Structure<BastionRemnantConfig>> field_235181_s_ = Structure.field_236383_s_.func_236391_a_(new BastionRemnantConfig(BastionRemnantsPieces.field_236257_a_));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> field_235182_t_ = Structure.field_236381_q_.func_236391_a_(new VillageConfig(new ResourceLocation("village/plains/town_centers"), 6));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> field_235183_u_ = Structure.field_236381_q_.func_236391_a_(new VillageConfig(new ResourceLocation("village/desert/town_centers"), 6));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> field_235184_v_ = Structure.field_236381_q_.func_236391_a_(new VillageConfig(new ResourceLocation("village/savanna/town_centers"), 6));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> field_235185_w_ = Structure.field_236381_q_.func_236391_a_(new VillageConfig(new ResourceLocation("village/snowy/town_centers"), 6));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> field_235186_x_ = Structure.field_236381_q_.func_236391_a_(new VillageConfig(new ResourceLocation("village/taiga/town_centers"), 6));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235187_y_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.STANDARD));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235188_z_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.DESERT));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235129_A_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.JUNGLE));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235130_B_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.SWAMP));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235131_C_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.MOUNTAIN));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235132_D_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.OCEAN));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> field_235133_E_ = Structure.field_236372_h_.func_236391_a_(new RuinedPortalFeature(RuinedPortalStructure.Location.NETHER));
   public static final BaseTreeFeatureConfig field_226739_a_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
   private static final BeehiveTreeDecorator field_235163_cx_ = new BeehiveTreeDecorator(0.002F);
   private static final BeehiveTreeDecorator field_235164_cy_ = new BeehiveTreeDecorator(0.02F);
   private static final BeehiveTreeDecorator field_235165_cz_ = new BeehiveTreeDecorator(0.05F);
   public static final BaseTreeFeatureConfig field_230132_o_ = field_226739_a_.func_236685_a_(ImmutableList.of(field_235163_cx_));
   public static final BaseTreeFeatureConfig field_230133_p_ = field_226739_a_.func_236685_a_(ImmutableList.of(field_235164_cy_));
   public static final BaseTreeFeatureConfig field_226816_k_ = field_226739_a_.func_236685_a_(ImmutableList.of(field_235165_cz_));
   public static final BaseTreeFeatureConfig field_226792_b_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226775_aj_), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1))).func_236703_a_(ImmutableList.of(new CocoaTreeDecorator(0.2F), TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_226808_c_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226775_aj_), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_226809_d_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_), new PineFoliagePlacer(1, 0, 1, 0, 3, 1), new StraightTrunkPlacer(6, 4, 0), new TwoLayerFeature(2, 0, 2))).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_226810_e_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_), new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2))).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_226811_f_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226778_am_), new SimpleBlockStateProvider(field_226779_an_), new AcaciaFoliagePlacer(2, 0, 0, 0), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2))).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_226812_g_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_230129_h_ = field_226812_g_.func_236685_a_(ImmutableList.of(field_235163_cx_));
   public static final BaseTreeFeatureConfig field_230135_r_ = field_226812_g_.func_236685_a_(ImmutableList.of(field_235164_cy_));
   public static final BaseTreeFeatureConfig field_230136_s_ = field_226812_g_.func_236685_a_(ImmutableList.of(field_235165_cz_));
   public static final BaseTreeFeatureConfig field_230130_i_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(5, 2, 6), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(field_235163_cx_)).func_225568_b_();
   public static final BaseTreeFeatureConfig field_226814_i_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(3, 0, 0, 0, 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1))).func_236701_a_(1).func_236703_a_(ImmutableList.of(LeaveVineTreeDecorator.field_236871_b_)).func_225568_b_();
   public static final BaseTreeFeatureConfig field_226815_j_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new FancyFoliagePlacer(2, 0, 4, 0, 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).func_236700_a_().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).func_225568_b_();
   public static final BaseTreeFeatureConfig field_230131_m_ = field_226815_j_.func_236685_a_(ImmutableList.of(field_235163_cx_));
   public static final BaseTreeFeatureConfig field_230134_q_ = field_226815_j_.func_236685_a_(ImmutableList.of(field_235164_cy_));
   public static final BaseTreeFeatureConfig field_226817_l_ = field_226815_j_.func_236685_a_(ImmutableList.of(field_235165_cz_));
   public static final BaseTreeFeatureConfig field_226821_p_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226773_ah_), new BushFoliagePlacer(2, 0, 1, 0, 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).func_225568_b_();
   public static final BaseTreeFeatureConfig field_226822_q_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226782_aq_), new SimpleBlockStateProvider(field_226783_ar_), new DarkOakFoliagePlacer(0, 0, 0, 0), new DarkOakTrunkPlacer(6, 2, 1), new ThreeLayerFeature(1, 1, 0, 1, 2, OptionalInt.empty()))).func_236701_a_(Integer.MAX_VALUE).func_236702_a_(Heightmap.Type.MOTION_BLOCKING).func_236700_a_().func_225568_b_();
   public static final BaseTreeFeatureConfig field_226823_r_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_), new MegaPineFoliagePlacer(0, 0, 0, 0, 4, 13), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))).func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(field_226771_af_)))).func_225568_b_();
   public static final BaseTreeFeatureConfig field_226824_s_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_), new MegaPineFoliagePlacer(0, 0, 0, 0, 4, 3), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))).func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(field_226771_af_)))).func_225568_b_();
   public static final BaseTreeFeatureConfig field_226825_t_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226775_aj_), new JungleFoliagePlacer(2, 0, 0, 0, 2), new MegaJungleTrunkPlacer(10, 2, 19), new TwoLayerFeature(1, 1, 2))).func_236703_a_(ImmutableList.of(TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).func_225568_b_();
   public static final BlockClusterFeatureConfig field_226826_u_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226769_ad_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(32).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226827_v_ = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(field_226769_ad_, 1).func_227407_a_(field_226770_ae_, 4), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(32).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226828_w_ = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(field_226769_ad_, 3).func_227407_a_(field_226770_ae_, 1), SimpleBlockPlacer.field_236447_c_)).func_227319_b_(ImmutableSet.of(field_226771_af_)).func_227315_a_(32).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226829_x_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226762_aW_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226830_y_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226763_aX_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226831_z_ = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(field_226764_aY_, 2).func_227407_a_(field_226765_aZ_, 1), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226713_A_ = (new BlockClusterFeatureConfig.Builder(PlainFlowerBlockStateProvider.field_236805_c_, SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226714_B_ = (new BlockClusterFeatureConfig.Builder(ForestFlowerBlockStateProvider.field_236802_c_, SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226715_C_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226793_ba_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(4).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226716_D_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226794_bb_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226750_aK_.getBlock())).func_227314_a_().func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226717_E_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226795_bc_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226750_aK_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226718_F_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226796_bd_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226750_aK_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_235147_ap_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226797_be_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226798_bf_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_235148_aq_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_235153_cd_), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_235154_cf_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226720_H_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226799_bg_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(10).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226721_I_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226758_aS_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226722_J_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226757_aR_), SimpleBlockPlacer.field_236447_c_)).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_235149_au_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_235155_cg_), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226723_K_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226754_aO_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226724_L_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226755_aP_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226725_M_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226756_aQ_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226726_N_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226802_bj_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226727_O_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226753_aN_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226728_P_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226752_aM_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226729_Q_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226803_bk_), new ColumnBlockPlacer(1, 2))).func_227315_a_(10).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226730_R_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226804_bl_), new ColumnBlockPlacer(2, 2))).func_227315_a_(20).func_227318_b_(4).func_227321_c_(0).func_227323_d_(4).func_227317_b_().func_227320_c_().func_227322_d_();
   public static final BlockStateProvidingFeatureConfig field_226731_S_ = new BlockStateProvidingFeatureConfig(new AxisRotatingBlockStateProvider(Blocks.HAY_BLOCK));
   public static final BlockStateProvidingFeatureConfig field_226732_T_ = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(field_226800_bh_));
   public static final BlockStateProvidingFeatureConfig field_226733_U_ = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(field_226794_bb_));
   public static final BlockStateProvidingFeatureConfig field_226734_V_ = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).func_227407_a_(field_226795_bc_, 19).func_227407_a_(field_226801_bi_, 1));
   public static final BlockStateProvidingFeatureConfig field_226735_W_ = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).func_227407_a_(field_226761_aV_, 1).func_227407_a_(field_226760_aU_, 5));
   public static final FluidState field_235135_aI_ = Fluids.WATER.getDefaultState();
   public static final FluidState field_235136_aJ_ = Fluids.LAVA.getDefaultState();
   public static final LiquidsConfig field_226736_X_ = new LiquidsConfig(field_235135_aI_, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE));
   public static final LiquidsConfig field_226737_Y_ = new LiquidsConfig(field_235136_aJ_, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE));
   public static final LiquidsConfig field_226738_Z_ = new LiquidsConfig(field_235136_aJ_, false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK));
   public static final LiquidsConfig field_235137_aN_ = new LiquidsConfig(field_235136_aJ_, true, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL, Blocks.MAGMA_BLOCK, Blocks.field_235406_np_));
   public static final LiquidsConfig field_226766_aa_ = new LiquidsConfig(field_235136_aJ_, false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK));
   public static final LiquidsConfig field_235138_aP_ = new LiquidsConfig(field_235136_aJ_, false, 4, 1, ImmutableSet.of(Blocks.SOUL_SAND));
   public static final LiquidsConfig field_235139_aQ_ = new LiquidsConfig(field_235136_aJ_, false, 5, 0, ImmutableSet.of(Blocks.SOUL_SAND));
   public static final ColumnConfig field_235140_aR_ = (new ColumnConfig.Builder()).func_236480_a_(1).func_236482_b_(1, 4).func_236479_a_();
   public static final ColumnConfig field_235141_aS_ = (new ColumnConfig.Builder()).func_236481_a_(2, 3).func_236482_b_(5, 10).func_236479_a_();
   public static final NetherackBlobReplacementFeature field_235142_aT_ = (new NetherackBlobReplacementFeature.Builder()).func_236624_a_(new Vector3i(3, 3, 3)).func_236626_b_(new Vector3i(7, 7, 7)).func_236623_a_(Blocks.NETHERRACK.getDefaultState()).func_236625_b_(Blocks.field_235337_cO_.getDefaultState()).func_236622_a_();
   public static final NetherackBlobReplacementFeature field_235143_aU_ = (new NetherackBlobReplacementFeature.Builder()).func_236624_a_(new Vector3i(3, 3, 3)).func_236626_b_(new Vector3i(7, 7, 7)).func_236623_a_(Blocks.NETHERRACK.getDefaultState()).func_236625_b_(Blocks.field_235406_np_.getDefaultState()).func_236622_a_();
   public static final BasaltDeltasFeature field_235144_aV_ = (new BasaltDeltasFeature.Builder()).func_236514_a_(Blocks.LAVA.getDefaultState()).func_236513_a_(3, 7).func_236515_a_(Blocks.MAGMA_BLOCK.getDefaultState(), 2).func_236512_a_();
   public static final BigMushroomFeatureConfig field_226767_ab_ = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(field_226805_bm_), new SimpleBlockStateProvider(field_226807_bo_), 2);
   public static final BigMushroomFeatureConfig field_226768_ac_ = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(field_226806_bn_), new SimpleBlockStateProvider(field_226807_bo_), 3);
   public static final BlockStateProvidingFeatureConfig field_235145_aY_ = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_235343_mB_.getDefaultState(), 87).func_227407_a_(Blocks.field_235382_mv_.getDefaultState(), 11).func_227407_a_(Blocks.field_235373_mm_.getDefaultState(), 1));
   public static final BlockStateProvidingFeatureConfig field_235146_aZ_ = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_235375_mo_.getDefaultState(), 85).func_227407_a_(Blocks.field_235343_mB_.getDefaultState(), 1).func_227407_a_(Blocks.field_235373_mm_.getDefaultState(), 13).func_227407_a_(Blocks.field_235382_mv_.getDefaultState(), 1));
   public static final BlockStateProvidingFeatureConfig field_235151_ba_ = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(Blocks.field_235376_mp_.getDefaultState()));

   public static void func_235189_a_(Biome p_235189_0_) {
      p_235189_0_.func_235063_a_(field_235152_c_);
      p_235189_0_.func_235063_a_(field_235173_k_);
   }

   public static void func_235196_b_(Biome p_235196_0_) {
      p_235196_0_.func_235063_a_(field_235150_b_);
      p_235196_0_.func_235063_a_(field_235173_k_);
   }

   public static void func_235197_c_(Biome p_235197_0_) {
      p_235197_0_.func_235063_a_(field_235150_b_);
      p_235197_0_.func_235063_a_(field_235170_h_);
   }

   public static void addCarvers(Biome biomeIn) {
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.14285715F)));
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.02F)));
   }

   public static void addOceanCarvers(Biome biomeIn) {
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.06666667F)));
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.02F)));
      biomeIn.addCarver(GenerationStage.Carving.LIQUID, Biome.createCarver(WorldCarver.UNDERWATER_CANYON, new ProbabilityConfig(0.02F)));
      biomeIn.addCarver(GenerationStage.Carving.LIQUID, Biome.createCarver(WorldCarver.UNDERWATER_CAVE, new ProbabilityConfig(0.06666667F)));
   }

   public static void addLakes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LAKES, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(field_226784_as_)).func_227228_a_(Placement.WATER_LAKE.func_227446_a_(new ChanceConfig(4))));
      biomeIn.addFeature(GenerationStage.Decoration.LAKES, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(field_226785_at_)).func_227228_a_(Placement.LAVA_LAKE.func_227446_a_(new ChanceConfig(80))));
   }

   public static void addDesertLakes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LAKES, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(field_226785_at_)).func_227228_a_(Placement.LAVA_LAKE.func_227446_a_(new ChanceConfig(80))));
   }

   public static void addMonsterRooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.MONSTER_ROOM.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.DUNGEONS.func_227446_a_(new ChanceConfig(8))));
   }

   public static void addStoneVariants(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226786_au_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 256))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226787_av_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(8, 0, 0, 256))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226788_aw_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 80))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226789_ax_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 80))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226790_ay_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 80))));
   }

   public static void addOres(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226791_az_, 17)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 0, 0, 128))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226740_aA_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 0, 0, 64))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226741_aB_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 0, 0, 32))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226742_aC_, 8)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(8, 0, 0, 16))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226743_aD_, 8)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(1, 0, 0, 16))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226744_aE_, 7)).func_227228_a_(Placement.COUNT_DEPTH_AVERAGE.func_227446_a_(new DepthAverageConfig(1, 16, 16))));
   }

   public static void addExtraGoldOre(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226741_aB_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 32, 32, 80))));
   }

   public static void addExtraEmeraldOre(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.EMERALD_ORE.func_225566_b_(new ReplaceBlockConfig(field_226745_aF_, field_226746_aG_)).func_227228_a_(Placement.EMERALD_ORE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void addInfestedStone(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226747_aH_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(7, 0, 0, 64))));
   }

   public static void addSedimentDisks(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226748_aI_, 7, 2, Lists.newArrayList(field_226786_au_, field_226750_aK_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(3))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226749_aJ_, 4, 1, Lists.newArrayList(field_226786_au_, field_226749_aJ_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226787_av_, 6, 2, Lists.newArrayList(field_226786_au_, field_226750_aK_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addSwampClayDisks(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226749_aJ_, 4, 1, Lists.newArrayList(field_226786_au_, field_226749_aJ_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addTaigaRocks(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.FOREST_ROCK.func_225566_b_(new BlockBlobConfig(field_226751_aL_, 0)).func_227228_a_(Placement.FOREST_ROCK.func_227446_a_(new FrequencyConfig(3))));
   }

   public static void addTaigaLargeFerns(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226728_P_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(7))));
   }

   public static void addSparseBerryBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226718_F_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(12))));
   }

   public static void addBerryBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226718_F_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addBamboo(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.func_225566_b_(new ProbabilityConfig(0.0F)).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(16))));
   }

   public static void addBambooJungleVegetation(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.func_225566_b_(new ProbabilityConfig(0.2F)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.func_227446_a_(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D, Heightmap.Type.WORLD_SURFACE_WG))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226815_j_).func_227227_a_(0.05F), Feature.field_236291_c_.func_225566_b_(field_226821_p_).func_227227_a_(0.15F), Feature.field_236291_c_.func_225566_b_(field_226825_t_).func_227227_a_(0.7F)), Feature.field_227248_z_.func_225566_b_(field_226828_w_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(30, 0.1F, 1))));
   }

   public static void addTaigaConifers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226809_d_).func_227227_a_(0.33333334F)), Feature.field_236291_c_.func_225566_b_(field_226810_e_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addScatteredOakTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226815_j_).func_227227_a_(0.1F)), Feature.field_236291_c_.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
   }

   public static void addBirchTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.func_225566_b_(field_230129_h_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addForestTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_230129_h_).func_227227_a_(0.2F), Feature.field_236291_c_.func_225566_b_(field_230131_m_).func_227227_a_(0.1F)), Feature.field_236291_c_.func_225566_b_(field_230132_o_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addTallBirchForestTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_230130_i_).func_227227_a_(0.5F)), Feature.field_236291_c_.func_225566_b_(field_230129_h_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addSavannaTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226811_f_).func_227227_a_(0.8F)), Feature.field_236291_c_.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
   }

   public static void addShatteredSavannaTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226811_f_).func_227227_a_(0.8F)), Feature.field_236291_c_.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
   }

   public static void addScatteredOakAndSpruceTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226810_e_).func_227227_a_(0.666F), Feature.field_236291_c_.func_225566_b_(field_226815_j_).func_227227_a_(0.1F)), Feature.field_236291_c_.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
   }

   public static void addOakAndSpruceTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226810_e_).func_227227_a_(0.666F), Feature.field_236291_c_.func_225566_b_(field_226815_j_).func_227227_a_(0.1F)), Feature.field_236291_c_.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
   }

   public static void addJungleTreeForest(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226815_j_).func_227227_a_(0.1F), Feature.field_236291_c_.func_225566_b_(field_226821_p_).func_227227_a_(0.5F), Feature.field_236291_c_.func_225566_b_(field_226825_t_).func_227227_a_(0.33333334F)), Feature.field_236291_c_.func_225566_b_(field_226792_b_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(50, 0.1F, 1))));
   }

   public static void addOakAndJungleTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226815_j_).func_227227_a_(0.1F), Feature.field_236291_c_.func_225566_b_(field_226821_p_).func_227227_a_(0.5F)), Feature.field_236291_c_.func_225566_b_(field_226792_b_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
   }

   public static void addSparseOakTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.func_225566_b_(field_226739_a_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
   }

   public static void addScatteredSpruceTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.func_225566_b_(field_226810_e_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
   }

   public static void func_222316_G(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226823_r_).func_227227_a_(0.33333334F), Feature.field_236291_c_.func_225566_b_(field_226809_d_).func_227227_a_(0.33333334F)), Feature.field_236291_c_.func_225566_b_(field_226810_e_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void func_222285_H(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226823_r_).func_227227_a_(0.025641026F), Feature.field_236291_c_.func_225566_b_(field_226824_s_).func_227227_a_(0.30769232F), Feature.field_236291_c_.func_225566_b_(field_226809_d_).func_227227_a_(0.33333334F)), Feature.field_236291_c_.func_225566_b_(field_226810_e_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addJungleGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226828_w_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(25))));
   }

   public static void addTallGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226727_O_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(7))));
   }

   public static void addDenseGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(5))));
   }

   public static void addVeryDenseGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(20))));
   }

   public static void addGrassAndDeadBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(20))));
   }

   public static void addDoubleFlowers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.func_225566_b_(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.field_227248_z_.func_225566_b_(field_226723_K_), Feature.field_227248_z_.func_225566_b_(field_226724_L_), Feature.field_227248_z_.func_225566_b_(field_226725_M_), Feature.field_227247_y_.func_225566_b_(field_226829_x_)), 0)).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(5))));
   }

   public static void addGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(2))));
   }

   public static void addSwampVegetation(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.func_225566_b_(field_226814_i_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226830_y_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(5))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226720_H_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(4))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(8, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(8, 0.125F))));
   }

   public static void addHugeMushrooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_BOOLEAN_SELECTOR.func_225566_b_(new TwoFeatureChoiceConfig(Feature.HUGE_RED_MUSHROOM.func_225566_b_(field_226767_ab_), Feature.HUGE_BROWN_MUSHROOM.func_225566_b_(field_226768_ac_))).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(1, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(1, 0.125F))));
   }

   public static void addOakTreesFlowersGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.func_225566_b_(field_226817_l_).func_227227_a_(0.33333334F)), Feature.field_236291_c_.func_225566_b_(field_226816_k_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226713_A_).func_227228_a_(Placement.NOISE_HEIGHTMAP_32.func_227446_a_(new NoiseDependant(-0.8D, 15, 4))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.NOISE_HEIGHTMAP_DOUBLE.func_227446_a_(new NoiseDependant(-0.8D, 5, 10))));
   }

   public static void addDeadBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(2))));
   }

   public static void addTaigaGrassDeadBushesMushrooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226827_v_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(7))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(3, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(3, 0.125F))));
   }

   public static void addDefaultFlowers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226831_z_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(2))));
   }

   public static void addExtraDefaultFlowers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226831_z_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(4))));
   }

   public static void addSparseGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addTaigaGrassAndMushrooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226827_v_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(1, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(1, 0.125F))));
   }

   public static void func_222283_Y(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226727_O_).func_227228_a_(Placement.NOISE_HEIGHTMAP_32.func_227446_a_(new NoiseDependant(-0.8D, 0, 7))));
   }

   public static void addMushrooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(4))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(8))));
   }

   public static void addReedsAndPumpkins(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(10))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
   }

   public static void addReedsPumpkinsCactus(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(13))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226729_Q_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(5))));
   }

   public static void addJunglePlants(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226716_D_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.VINES.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_HEIGHT_64.func_227446_a_(new FrequencyConfig(50))));
   }

   public static void addExtraReedsPumpkinsCactus(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(60))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226729_Q_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(10))));
   }

   public static void addExtraReedsAndPumpkins(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(20))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
   }

   public static void addDesertFeatures(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.DESERT_WELL.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.CHANCE_HEIGHTMAP.func_227446_a_(new ChanceConfig(1000))));
   }

   public static void func_235191_ai_(Biome p_235191_0_) {
      p_235191_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.FOSSIL.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.CHANCE_PASSTHROUGH.func_227446_a_(new ChanceConfig(64))));
   }

   public static void addExtraKelp(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.KELP.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.func_227446_a_(new TopSolidWithNoiseConfig(120, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG))));
   }

   public static void func_222320_ai(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SIMPLE_BLOCK.func_225566_b_(new BlockWithContextConfig(field_226759_aT_, ImmutableList.of(field_226745_aF_), ImmutableList.of(field_226784_as_), ImmutableList.of(field_226784_as_))).func_227228_a_(Placement.CARVING_MASK.func_227446_a_(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, 0.1F))));
   }

   public static void func_222309_aj(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.func_225566_b_(new SeaGrassConfig(80, 0.3D)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void func_222340_ak(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.func_225566_b_(new SeaGrassConfig(80, 0.8D)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void addKelp(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.KELP.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.func_227446_a_(new TopSolidWithNoiseConfig(80, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG))));
   }

   public static void addSprings(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(field_226736_X_).func_227228_a_(Placement.COUNT_BIASED_RANGE.func_227446_a_(new CountRangeConfig(50, 8, 8, 256))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(field_226737_Y_).func_227228_a_(Placement.COUNT_VERY_BIASED_RANGE.func_227446_a_(new CountRangeConfig(20, 8, 16, 256))));
   }

   public static void addIcebergs(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.ICEBERG.func_225566_b_(new BlockStateFeatureConfig(field_226760_aU_)).func_227228_a_(Placement.ICEBERG.func_227446_a_(new ChanceConfig(16))));
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.ICEBERG.func_225566_b_(new BlockStateFeatureConfig(field_226761_aV_)).func_227228_a_(Placement.ICEBERG.func_227446_a_(new ChanceConfig(200))));
   }

   public static void addBlueIce(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.BLUE_ICE.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.RANDOM_COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 30, 32, 64))));
   }

   public static void addFreezeTopLayer(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Feature.FREEZE_TOP_LAYER.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
   }

   public static void func_235192_as_(Biome p_235192_0_) {
      p_235192_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.GRAVEL.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 5, 0, 37))));
      p_235192_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.field_235406_np_.getDefaultState(), 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 5, 10, 37))));
      func_235190_a_(p_235192_0_, 10, 16);
      func_235193_at_(p_235192_0_);
   }

   public static void func_235190_a_(Biome p_235190_0_, int p_235190_1_, int p_235190_2_) {
      p_235190_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, field_235156_cq_, 10)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(p_235190_1_, 10, 20, 128))));
      p_235190_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, field_235157_cr_, 14)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(p_235190_2_, 10, 20, 128))));
   }

   public static void func_235193_at_(Biome p_235193_0_) {
      p_235193_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236289_V_.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES, Blocks.field_235398_nh_.getDefaultState(), 3)).func_227228_a_(Placement.COUNT_DEPTH_AVERAGE.func_227446_a_(new DepthAverageConfig(1, 16, 8))));
      p_235193_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236289_V_.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES, Blocks.field_235398_nh_.getDefaultState(), 2)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(1, 8, 16, 128))));
   }

   public static void func_235194_au_(Biome p_235194_0_) {
      p_235194_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236281_L_.func_225566_b_(HugeFungusConfig.field_236300_c_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(8))));
      p_235194_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236282_M_.func_225566_b_(field_235145_aY_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(6))));
   }

   public static void func_235195_av_(Biome p_235195_0_) {
      p_235195_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236281_L_.func_225566_b_(HugeFungusConfig.field_236302_e_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(8))));
      p_235195_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236282_M_.func_225566_b_(field_235146_aZ_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(5))));
      p_235195_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236282_M_.func_225566_b_(field_235151_ba_).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(4))));
      p_235195_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236284_O_.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 128))));
   }
}