package quantumstudios.culinae.client.renderer;

import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quantumstudios.culinae.Culinae;

@SideOnly(Side.CLIENT)
public class RenderModBoat extends RenderBoat
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(culinae.MODID, "textures/entity/boat.png");

    public RenderModBoat(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBoat entity)
    {
        return TEXTURE;
    }
}
