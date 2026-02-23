package quantumstudios.culinae.client.particle;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quantumstudios.culinae.Culinae;

@SideOnly(Side.CLIENT)
// @EventBusSubscriber(modid = culinae.MODID, value = Side.CLIENT)
public class CulinaeParticles
{
    public static final ResourceLocation PARTICLES = new ResourceLocation(culinae.MODID, "misc/particles");

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent event)
    {
        event.getMap().registerSprite(PARTICLES);
    }
}
