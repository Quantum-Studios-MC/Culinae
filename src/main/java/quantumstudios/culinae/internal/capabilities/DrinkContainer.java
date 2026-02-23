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
import quantumstudios.culinae.api.CulinaryCapabilities;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.prefab.SimpleFoodContainerImpl;
import quantumstudios.culinae.internal.CulinaeSharedSecrets;
import quantumstudios.culinae.internal.food.Drink;

/**
 * The {@link quantumstudios.culinae.api.FoodContainer} implementation used by
 * {@link quantumstudios.culinae.items.ItemDish}.
 */
public class DrinkContainer extends SimpleFoodContainerImpl implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{

    @Nonnull
    @Override
    public ItemStack getEmptyContainer(ItemStack currentContainer)
    {
        if (this.food != null && this.food.getClass() == Drink.class)
        {
            return ((Drink) this.food).getDrinkType().getContainerPost();
        }
        return ItemStack.EMPTY;
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
        // TODO (3TUSK): check if the CompositeFood is indeed a Drink?
    }
}
