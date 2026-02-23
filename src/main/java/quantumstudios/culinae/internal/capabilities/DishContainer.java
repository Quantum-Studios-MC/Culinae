package quantumstudios.culinae.internal.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CulinaryCapabilities;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.prefab.SimpleFoodContainerImpl;
import quantumstudios.culinae.internal.CulinaeSharedSecrets;
import quantumstudios.culinae.internal.food.Dish;

/**
 * The {@link quantumstudios.culinae.api.FoodContainer} implementation used by
 * {@link quantumstudios.culinae.items.ItemDish}.
 */
public class DishContainer extends SimpleFoodContainerImpl implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{

    @Nonnull
    @Override
    public ItemStack getEmptyContainer(ItemStack currentContainer)
    {
        return new ItemStack(CulinaeRegistry.PLACED_DISH);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CulinaryCapabilities.FOOD_CONTAINER;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CulinaryCapabilities.FOOD_CONTAINER)
        {
            return CulinaryCapabilities.FOOD_CONTAINER.cast(this);
        }
        else
        {
            return null;
        }
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        if (this.food == null)
        {
            return new NBTTagCompound();
        }
        else
        {
            return CulinaryHub.API_INSTANCE.serialize(this.food);
        }
    }

    @Override
    public void deserializeNBT(NBTTagCompound data)
    {
        ResourceLocation id = new ResourceLocation(data.getString(CulinaeSharedSecrets.KEY_TYPE));
        this.food = CulinaryHub.API_INSTANCE.deserialize(id, data);
        if (food == null)
        {
            // Backward compatibility: assume failed-to-load data are from previous version.
            // See TileDish.readFromNBT for more info
            this.food = Dish.deserialize(data);
        }
    }
}
