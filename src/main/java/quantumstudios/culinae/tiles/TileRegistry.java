package quantumstudios.culinae.tiles;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quantumstudios.culinae.Culinae;

@Mod.EventBusSubscriber(modid = culinae.MODID)
public final class TileRegistry
{

    // This class is here to pave way for 1.13 update, where we may have TileEntityType
    // as a valid Forge Registry.

    private TileRegistry()
    {
        throw new UnsupportedOperationException("No instance for you");
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        GameRegistry.registerTileEntity(TileMortar.class, new ResourceLocation(culinae.MODID, "mortar"));
        GameRegistry.registerTileEntity(TileMill.class, new ResourceLocation(culinae.MODID, "mill"));
        GameRegistry.registerTileEntity(TileChoppingBoard.class, new ResourceLocation(culinae.MODID, "chopping_board"));
        GameRegistry.registerTileEntity(TileJar.class, new ResourceLocation(culinae.MODID, "jar"));
        GameRegistry.registerTileEntity(TileWok.class, new ResourceLocation(culinae.MODID, "wok"));
        GameRegistry.registerTileEntity(TileFryingPan.class, new ResourceLocation(culinae.MODID, "frying_pan"));
        GameRegistry.registerTileEntity(TileBarbecueRack.class, new ResourceLocation(culinae.MODID, "barbecue_rack"));
        GameRegistry.registerTileEntity(TileDish.class, new ResourceLocation(culinae.MODID, "placed_dish"));
        GameRegistry.registerTileEntity(TileBasin.class, new ResourceLocation(culinae.MODID, "basin"));
        GameRegistry.registerTileEntity(TileBasinHeatable.class, new ResourceLocation(culinae.MODID, "basin_heatable"));
        GameRegistry.registerTileEntity(TileDrinkroBase.class, new ResourceLocation(culinae.MODID, "drinkro_base"));
        GameRegistry.registerTileEntity(TileDrinkroTank.class, new ResourceLocation(culinae.MODID, "drinkro_tank"));
        GameRegistry.registerTileEntity(TileSqueezer.class, new ResourceLocation(culinae.MODID, "squeezer"));
        GameRegistry.registerTileEntity(TileFruitTree.class, new ResourceLocation(culinae.MODID, "fruit_tree"));
    }
}
