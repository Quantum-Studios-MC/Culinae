package quantumstudios.culinae.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.internal.CulinaeSharedSecrets;

public class FluidJuice extends VaporizableFluid
{
    private static final int DEFAULT_COLOR = 0xF08A19;

    public FluidJuice(String name)
    {
        super(name);
    }

    public static FluidStack make(Material material, int amount)
    {
        NBTTagCompound data = new NBTTagCompound();
        data.setString(CulinaeSharedSecrets.KEY_MATERIAL, material.getID());
        return new FluidStack(CulinaeFluids.JUICE, amount, data);
    }

    @Override
    public int getColor(FluidStack stack)
    {
        Material material = CulinaryHub.API_INSTANCE.findMaterial(stack);
        return material == null ? DEFAULT_COLOR : material.getRawColorCode();
    }

    @Override
    public String getLocalizedName(FluidStack stack)
    {
        Ingredient ingredient = CulinaryHub.API_INSTANCE.findIngredient(stack);
        if (ingredient == null)
        {
            return super.getLocalizedName(stack);
        }
        else
        {
            return ingredient.getTranslation();
        }
    }

}
