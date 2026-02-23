package quantumstudios.culinae.plugins.top;

import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.blocks.BlockCulinaeCrops;
import quantumstudios.culinae.blocks.BlockModLeaves;
import quantumstudios.culinae.blocks.BlockShearedLeaves;
import quantumstudios.culinae.util.I18nUtil;

public class CulinaeCropProvider implements IProbeInfoProvider
{

    @Override
    public String getID()
    {
        return culinae.MODID + ":crop";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
    {
        if (blockState.getBlock() instanceof BlockCulinaeCrops)
        {
            if (Tools.show(mode, Config.getRealConfig().getShowCropPercentage()))
            {
                BlockCulinaeCrops crops = (BlockCulinaeCrops) blockState.getBlock();
                int age = crops.getAge(blockState, world, data.getPos());
                int maxAge = crops.getMaxAge();
                if (age == maxAge)
                {
                    probeInfo.text(TextStyleClass.OK + I18nUtil.translate("gui.fully_grown"));
                }
                else
                {
                    probeInfo.text(TextStyleClass.LABEL + I18nUtil.translate("gui.grown", TextStyleClass.WARNING + "" + (age * 100) / maxAge));
                }
            }
        }
        else if (blockState.getBlock() instanceof BlockModLeaves)
        {
            if (Tools.show(mode, Config.getRealConfig().getShowCropPercentage()))
            {
                probeInfo.text(I18nUtil.translate( "gui.leaves." + blockState.getValue(BlockModLeaves.AGE)));
            }
        }
        else if (blockState.getBlock() instanceof BlockShearedLeaves)
        {
            if (Tools.show(mode, Config.getRealConfig().getShowCropPercentage()))
            {
                probeInfo.text(I18nUtil.translate( "gui.leaves.0"));
            }
        }
    }

}
