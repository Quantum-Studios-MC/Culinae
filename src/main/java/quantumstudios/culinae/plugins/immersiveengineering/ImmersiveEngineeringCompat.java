package quantumstudios.culinae.plugins.immersiveengineering;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.process.Chopping;
import quantumstudios.culinae.api.process.Processing;
import quantumstudios.culinae.api.process.prefab.SimpleThrowing;
import quantumstudios.culinae.items.ItemCrops.Variant;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.util.definition.OreDictDefinition;

@KiwiModule(
        modid = culinae.MODID, name = ImmersiveEngineeringCompat.MODID, dependency = ImmersiveEngineeringCompat.MODID, optional = true
)
public class ImmersiveEngineeringCompat implements IModule
{
    static final String MODID = "immersiveengineering";

    @Override
    public void init()
    {
        if (CulinaeConfig.GENERAL.axeChopping)
        {
            Item stick = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "material"));
            if (stick != null)
            {
                Processing.CHOPPING.add(new Chopping(new ResourceLocation(MODID, "treated_stick"), OreDictDefinition.of("plankTreatedWood"), new ItemStack(stick, CulinaeConfig.GENERAL.axeChoppingStickOutput)));
            }
        }

        Fluid creosote = FluidRegistry.getFluid("creosote");
        Item treated_wood = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "treated_wood"));
        if (creosote != null && treated_wood != null)
        {
            Processing.BASIN_THROWING.add(new SimpleThrowing(new ResourceLocation(MODID, "treated_wood"), OreDictDefinition.of("plankWood"), new FluidStack(creosote, 125), new ItemStack(treated_wood)));
        }

        for (Variant variant : CulinaeRegistry.CROPS.getVariants())
        {
            BelljarHandler.registerHandler(new CulinaePlantHandler(variant));
        }

    }
}
