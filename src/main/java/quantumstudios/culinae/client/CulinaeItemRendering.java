package quantumstudios.culinae.client;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.ItemHandlerHelper;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CulinaryCapabilities;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.FoodContainer;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.api.Spice;
import quantumstudios.culinae.client.model.ChoppingBoardColorProxy;
import quantumstudios.culinae.internal.CulinaeSharedSecrets;
import quantumstudios.culinae.internal.food.Drink;
import snownee.kiwi.util.NBTHelper;

@Mod.EventBusSubscriber(modid = culinae.MODID, value = Side.CLIENT)
public final class CulinaeItemRendering
{
    public static final ResourceLocation EMPTY_MODEL = new ResourceLocation(culinae.MODID, "empty");

    private CulinaeItemRendering()
    {
        throw new UnsupportedOperationException("No instance for you");
    }

    @SubscribeEvent
    public static void onItemColorsInit(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();
        itemColors.registerItemColorHandler(ChoppingBoardColorProxy.INSTANCE, CulinaeRegistry.CHOPPING_BOARD);
        itemColors.registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 0)
            {
                NBTTagCompound data = stack.getTagCompound();
                if (data != null)
                {
                    Material material = CulinaryHub.API_INSTANCE.findMaterial(data.getString(CulinaeSharedSecrets.KEY_MATERIAL));
                    if (material != null)
                    {
                        int doneness = data.getInteger(CulinaeSharedSecrets.KEY_DONENESS);

                        //debug code
                        //doneness = (int) (Minecraft.getSystemTime() / 10 % 200);

                        return material.getColorCode(doneness);
                    }
                }
            }
            return -1;
        }, CulinaeRegistry.INGREDIENT);

        itemColors.registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 0 && CulinaeRegistry.SPICE_BOTTLE.hasItem(stack))
            {
                Spice spice = CulinaeRegistry.SPICE_BOTTLE.getSpice(stack);
                if (spice != null)
                {
                    return spice.getColorCode();
                }
            }
            else if (tintIndex == 1 && CulinaeRegistry.SPICE_BOTTLE.hasFluid(stack))
            {
                IFluidHandlerItem handler = CulinaeRegistry.SPICE_BOTTLE.getFluidHandler(stack);
                if (handler != null)
                {
                    FluidStack fluid = handler.drain(Integer.MAX_VALUE, false);
                    if (fluid != null)
                    {
                        return fluid.getFluid().getColor(fluid);
                    }
                }
            }
            return -1;
        }, CulinaeRegistry.SPICE_BOTTLE);

        itemColors.registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 0)
            {
                stack = ItemHandlerHelper.copyStackWithSize(stack, 1);
                IFluidHandlerItem handlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (handlerItem != null)
                {
                    FluidStack fluid = handlerItem.drain(Integer.MAX_VALUE, false);
                    if (fluid != null)
                    {
                        return fluid.getFluid().getColor(fluid);
                    }
                }
                return NBTHelper.of(stack).getInt("liquidColor", -1);
            }
            return -1;
        }, CulinaeRegistry.BOTTLE);

        itemColors.registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 1 && stack.hasCapability(CulinaryCapabilities.FOOD_CONTAINER, null))
            {
                FoodContainer container = stack.getCapability(CulinaryCapabilities.FOOD_CONTAINER, null);
                CompositeFood food = container.get();
                if (food != null && food.getClass() == Drink.class)
                {
                    return ((Drink) food).getColor();
                }
                return NBTHelper.of(stack).getInt("liquidColor", -1);
            }
            return -1;
        }, CulinaeRegistry.DRINK);

        itemColors.registerItemColorHandler((
                stack, tintIndex
        ) -> tintIndex == 0 ? ColorizerFoliage.getFoliageColorBasic() : -1, CulinaeRegistry.SHEARED_LEAVES);
    }
}
