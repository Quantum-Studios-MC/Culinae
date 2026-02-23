package quantumstudios.culinae.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.client.renderer.RenderEntitySeed;
import quantumstudios.culinae.client.renderer.RenderModBoat;
import quantumstudios.culinae.entities.EntityModBoat;
import quantumstudios.culinae.entities.EntitySeed;

@Mod.EventBusSubscriber(modid = culinae.MODID, value = Side.CLIENT)
public final class CulinaeEntityRendering
{

    @SubscribeEvent
    public static void entityRendererRegistration(ModelRegistryEvent event)
    {
        if (CulinaeConfig.GENERAL.bambooBlowpipe)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntitySeed.class, m -> new RenderEntitySeed(m, Minecraft.getMinecraft().getRenderItem()));
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityModBoat.class, RenderModBoat::new);

    }
}
