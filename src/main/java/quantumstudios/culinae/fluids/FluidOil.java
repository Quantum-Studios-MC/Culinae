package quantumstudios.culinae.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import quantumstudios.culinae.Culinae;

public class FluidOil extends Fluid
{
    public FluidOil(String name)
    {
        super(name, new ResourceLocation(culinae.MODID, "block/" + name + "_still"), new ResourceLocation(culinae.MODID, "block/" + name + "_flow"), 0x88FFFFFF);
        setDensity(800);
    }

}
