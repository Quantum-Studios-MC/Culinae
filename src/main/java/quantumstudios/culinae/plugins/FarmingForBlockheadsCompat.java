package quantumstudios.culinae.plugins;

import java.util.Locale;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.items.ItemCrops;
import quantumstudios.culinae.items.ItemCrops.Variant;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;

@KiwiModule(modid = culinae.MODID, name = "farmingforblockheads", dependency = "farmingforblockheads", optional = true)
public class FarmingForBlockheadsCompat implements IModule
{
    @Override
    public void init()
    {
        for (Variant variant : CulinaeRegistry.CROPS.getVariants())
        {
            addTrade(variant == ItemCrops.Variant.BAMBOO_SHOOT ? MarketCategory.SAPLINGS : MarketCategory.SEEDS, new ItemStack(CulinaeRegistry.CROPS, 1, variant.getMeta()));
        }
        NonNullList<ItemStack> stacks = NonNullList.create();
        CulinaeRegistry.SAPLING.getSubBlocks(culinae.CREATIVE_TAB, stacks);
        for (ItemStack stack : stacks)
        {
            addTrade(MarketCategory.SAPLINGS, stack);
        }
    }

    private static void addTrade(MarketCategory category, ItemStack stack)
    {
        NBTTagCompound data = new NBTTagCompound();
        data.setTag("OutputItem", stack.serializeNBT());
        data.setTag("CostItem", new ItemStack(Items.EMERALD).serializeNBT());
        data.setString("Category", category.toString());
        FMLInterModComms.sendMessage("farmingforblockheads", "RegisterMarketEntry", data);
    }

    private enum MarketCategory
    {
        SEEDS, SAPLINGS, OTHER;

        @Override
        public String toString()
        {
            return "farmingforblockheads:" + super.toString().toLowerCase(Locale.ROOT);
        }
    }
}
