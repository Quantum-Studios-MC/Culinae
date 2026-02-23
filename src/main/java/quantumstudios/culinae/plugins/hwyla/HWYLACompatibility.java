package quantumstudios.culinae.plugins.hwyla;

import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import quantumstudios.culinae.blocks.BlockBasin;
import quantumstudios.culinae.blocks.BlockCulinaeCrops;
import quantumstudios.culinae.blocks.BlockModLeaves;
import quantumstudios.culinae.blocks.BlockShearedLeaves;
import quantumstudios.culinae.tiles.TileWok;

@WailaPlugin
public final class HWYLACompatibility implements IWailaPlugin
{
    static final String KEY_SHOW_CROP_MATURITY = "general.showcrop";

    @Override
    public void register(IWailaRegistrar registrar)
    {
        CulinaeCropProvider cropProvider = new CulinaeCropProvider();
        registrar.registerBodyProvider(cropProvider, BlockCulinaeCrops.class);
        registrar.registerBodyProvider(cropProvider, BlockModLeaves.class);
        registrar.registerBodyProvider(cropProvider, BlockShearedLeaves.class);
        CulinaeWokProvider wokProvider = new CulinaeWokProvider();
        registrar.registerBodyProvider(wokProvider, TileWok.class);
        registrar.registerNBTProvider(wokProvider, TileWok.class);
        CulinaeBasinProvider basinProvider = new CulinaeBasinProvider();
        registrar.registerBodyProvider(basinProvider, BlockBasin.class);
        registrar.registerNBTProvider(basinProvider, BlockBasin.class);
    }
}
