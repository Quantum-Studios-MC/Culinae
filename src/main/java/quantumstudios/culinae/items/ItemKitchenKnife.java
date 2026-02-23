package quantumstudios.culinae.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import quantumstudios.culinae.Culinae;
import snownee.kiwi.item.ItemMod;

public class ItemKitchenKnife extends ItemMod
{
    public ItemKitchenKnife(String name)
    {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(culinae.CREATIVE_TAB);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        return itemStack.copy();
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }
}
