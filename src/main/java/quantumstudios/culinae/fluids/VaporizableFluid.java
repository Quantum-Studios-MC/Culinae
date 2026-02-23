package quantumstudios.culinae.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import quantumstudios.culinae.Culinae;

/**
 * Specialized version of {@link Fluid} where {@link #doesVaporize} always
 * returns {@code true}.
 */
public class VaporizableFluid extends Fluid
{
    public VaporizableFluid(String name)
    {
        this(name, new ResourceLocation(culinae.MODID, "block/" + name + "_still"), new ResourceLocation(culinae.MODID, "block/" + name + "_flow"));
    }

    public VaporizableFluid(String fluidName, ResourceLocation still, ResourceLocation flowing)
    {
        super(fluidName, still, flowing);
    }

    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        return true;
    }
}
