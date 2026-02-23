package quantumstudios.culinae.client.model;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CulinaryCapabilities;
import quantumstudios.culinae.api.FoodContainer;
import quantumstudios.culinae.client.CulinaeItemRendering;

public final class DishMeshDefinition implements ItemMeshDefinition
{

    public static final DishMeshDefinition INSTANCE = new DishMeshDefinition();

    private DishMeshDefinition()
    {
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack)
    {
        FoodContainer container = stack.getCapability(CulinaryCapabilities.FOOD_CONTAINER, null);
        CompositeFood food;
        if (container != null && (food = container.get()) != null)
        {
            return new ModelResourceLocation(new ResourceLocation(culinae.MODID, "dish/" + food.getOrComputeModelType()), "inventory");
        }
        else if (stack.hasTagCompound() && stack.getTagCompound().hasKey("model", Constants.NBT.TAG_STRING))
        {
            return new ModelResourceLocation(new ResourceLocation(culinae.MODID, "dish/" + stack.getTagCompound().getString("model")), "inventory");
        }
        else
        {
            return new ModelResourceLocation(CulinaeItemRendering.EMPTY_MODEL, "inventory");
        }
    }
}
