package quantumstudios.culinae.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.AnimationTESR;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.client.model.ChoppingBoardColorProxy;
import quantumstudios.culinae.client.model.ChoppingBoardModel;
import quantumstudios.culinae.client.renderer.*;
import quantumstudios.culinae.fluids.CulinaeFluidBlocks;
import quantumstudios.culinae.fluids.CulinaeFluids;
import quantumstudios.culinae.tiles.*;
import snownee.kiwi.client.ModelUtil;

@Mod.EventBusSubscriber(modid = culinae.MODID, value = Side.CLIENT)
public final class CulinaeBlockRendering
{
    @SubscribeEvent
    public static void onBlockColorsInit(ColorHandlerEvent.Block event)
    {
        BlockColors blockColors = event.getBlockColors();
        blockColors.registerBlockColorHandler(ChoppingBoardColorProxy.INSTANCE, CulinaeRegistry.CHOPPING_BOARD);
        blockColors.registerBlockColorHandler((state, blockAccess, pos, tintIndex) -> {
            if (tintIndex == 0)
            {
                return blockAccess != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(blockAccess, pos) : ColorizerFoliage.getFoliageColorBasic();
            }
            if (tintIndex == 1)
            {
                Block block = state.getBlock();
                if (block == CulinaeRegistry.LEAVES_CITRON)
                {
                    return 0xDDCC58;
                }
                if (block == CulinaeRegistry.LEAVES_GRAPEFRUIT)
                {
                    return 0xF4502B;
                }
                if (block == CulinaeRegistry.LEAVES_LEMON)
                {
                    return 0xEBCA4B;
                }
                if (block == CulinaeRegistry.LEAVES_LIME)
                {
                    return 0xCADA76;
                }
                if (block == CulinaeRegistry.LEAVES_MANDARIN)
                {
                    return 0xF08A19;
                }
                if (block == CulinaeRegistry.LEAVES_ORANGE)
                {
                    return 0xF08A19;
                }
                if (block == CulinaeRegistry.LEAVES_POMELO)
                {
                    return 0xF7F67E;
                }
            }
            return -1;
        }, CulinaeRegistry.BAMBOO_PLANT, CulinaeRegistry.BAMBOO, CulinaeRegistry.LEAVES_CITRON, CulinaeRegistry.LEAVES_GRAPEFRUIT, CulinaeRegistry.LEAVES_LEMON, CulinaeRegistry.LEAVES_LIME, CulinaeRegistry.LEAVES_MANDARIN, CulinaeRegistry.LEAVES_MANDARIN, CulinaeRegistry.LEAVES_ORANGE, CulinaeRegistry.LEAVES_POMELO, CulinaeRegistry.SHEARED_LEAVES);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        OBJLoader.INSTANCE.addDomain(culinae.MODID);

        ModelLoaderRegistry.registerLoader(ChoppingBoardModel.Loader.INSTANCE);

        ModelUtil.mapFluidModel(CulinaeFluidBlocks.SOY_MILK);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.MILK);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.SOY_SAUCE);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.FRUIT_VINEGAR);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.RICE_VINEGAR);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.EDIBLE_OIL);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.SESAME_OIL);
        ModelUtil.mapFluidModel(CulinaeFluidBlocks.SUGARCANE_JUICE);

        ClientRegistry.bindTileEntitySpecialRenderer(TileMortar.class, new TESRMortar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMill.class, new AnimationTESR<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChoppingBoard.class, new TESRChoppingBoard());
        ClientRegistry.bindTileEntitySpecialRenderer(TileWok.class, new TESRWok());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFryingPan.class, new TESRFryingPan());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBarbecueRack.class, new TESRBarbecueRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSqueezer.class, new AnimationTESR<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBasin.class, new TESRBasin());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDrinkroBase.class, new TESRDrinkroBase());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDrinkroTank.class, new TESRDrinkroTank());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event)
    {
        event.getMap().registerSprite(CulinaeFluids.JUICE.getStill());
        event.getMap().registerSprite(CulinaeFluids.JUICE.getFlowing());
    }

}
