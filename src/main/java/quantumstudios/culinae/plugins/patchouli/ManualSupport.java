package quantumstudios.culinae.plugins.patchouli;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import vazkii.patchouli.api.PatchouliAPI;

@KiwiModule(modid = culinae.MODID, name = "patchouli", dependency = "patchouli")
public class ManualSupport implements IModule
{
    @Override
    @SideOnly(Side.CLIENT)
    public void preInit()
    {
        MinecraftForge.EVENT_BUS.register(new PatchouliClientHandler());
    }

    @Override
    public void init()
    {
        CulinaeRegistry.MANUAL.setOpenManualHandler((world, player, hand) -> {
            if (player instanceof EntityPlayerMP)
            {
                PatchouliAPI.instance.openBookGUI((EntityPlayerMP) player, new ResourceLocation(culinae.MODID, "culinary_101"));
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        });

        PatchouliAPI.instance.setConfigFlag("culinae:enable_axe_chopping", CulinaeConfig.GENERAL.axeChopping);
        PatchouliAPI.instance.setConfigFlag("culinae:enable_sunlight_heating", CulinaeConfig.GENERAL.basinHeatingInDaylight);
        PatchouliAPI.instance.setConfigFlag("culinae:enable_fruit_dropping", CulinaeConfig.GENERAL.fruitDrops);
        PatchouliAPI.instance.setConfigFlag("culinae:enable_garden_world_gen", CulinaeConfig.WORLD_GEN.cropsGenRate > 0 && CulinaeConfig.WORLD_GEN.cropsGenDimensions.length > 0);
        PatchouliAPI.instance.setConfigFlag("culinae:enable_grass_seeds", CulinaeConfig.GENERAL.basicSeedsWeight > 0);
        PatchouliAPI.instance.setConfigFlag("culinae:drinkro_uses_energy", CulinaeConfig.GENERAL.drinkroUsesFE > 0);
        PatchouliAPI.instance.setConfigFlag("culinae:squeezer_uses_energy", CulinaeConfig.GENERAL.squeezerUsesFE > 0);
    }
}
