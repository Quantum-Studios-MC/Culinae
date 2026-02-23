package quantumstudios.culinae.plugins.top;

import java.text.MessageFormat;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.blocks.BlockFirePit;
import quantumstudios.culinae.tiles.TileBasinHeatable;
import quantumstudios.culinae.tiles.TileFirePit;
import quantumstudios.culinae.util.I18nUtil;

public class CulinaeMachineProvider implements IProbeInfoProvider
{

    @Override
    public String getID()
    {
        return culinae.MODID + ":machine";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
    {
        if (mode == ProbeMode.DEBUG && blockState.getBlock() instanceof BlockFirePit)
        {
            TileEntity tile = world.getTileEntity(data.getPos());
            if (tile instanceof TileFirePit)
            {
                TileFirePit tileFirePit = (TileFirePit) tile;
                probeInfo.text(TextStyleClass.LABEL + I18nUtil.translate("gui.burnTime", tileFirePit.heatHandler.getBurnTime()));
                probeInfo.text(TextStyleClass.LABEL + I18nUtil.translate("gui.heat", tileFirePit.heatHandler.getHeat()));
                // probeInfo.text(TextStyleClass.LABEL + I18nUtil.translate("gui.water_amount", tileWok.getWaterAmount()));
                // probeInfo.text(TextStyleClass.LABEL + I18nUtil.translate("gui.oil_amount", tileWok.getOilAmount()));
            }
        }
        else if (mode == ProbeMode.EXTENDED || mode == ProbeMode.DEBUG)
        {
            if (blockState.getBlock() == CulinaeRegistry.EARTHEN_BASIN || blockState.getBlock() == CulinaeRegistry.EARTHEN_BASIN_COLORED)
            {
                TileEntity tile = world.getTileEntity(data.getPos());
                if (tile instanceof TileBasinHeatable)
                {
                    TileBasinHeatable tileBasinHeatable = (TileBasinHeatable) tile;
                    if (tileBasinHeatable.getCurrentHeatingRecipe() != null)
                    {
                        MessageFormat formatter = new MessageFormat(I18nUtil.translate("gui.progress"), MinecraftForgeClient.getLocale());
                        probeInfo.text(TextStyleClass.INFO + formatter.format(new Object[] { 2 })); // "Using TheOneProbe"
                        probeInfo.progress(tileBasinHeatable.getCurrentHeatingTick(), TileBasinHeatable.HEATING_TICK, new ProgressStyle().showText(false));
                    }
                }
            }
        }

    }

}
