package quantumstudios.culinae;

import java.util.stream.Stream;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quantumstudios.culinae.fluids.CulinaeFluids;

public class CulinaeItemGroup extends CreativeTabs
{
    CulinaeItemGroup()
    {
        super(culinae.MODID);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(CulinaeRegistry.KITCHEN_KNIFE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
        super.displayAllRelevantItems(list);

        list.add(new ItemStack(Items.APPLE));
        list.add(new ItemStack(Items.GOLDEN_APPLE));
        list.add(new ItemStack(Items.GOLDEN_APPLE, 1, 1));
        list.add(new ItemStack(Items.CARROT));
        list.add(new ItemStack(Items.GOLDEN_CARROT));
        list.add(new ItemStack(Items.POTATO));
        list.add(new ItemStack(Items.BEETROOT));
        list.add(new ItemStack(Blocks.PUMPKIN));
        list.add(new ItemStack(Items.MELON));
        list.add(new ItemStack(Items.CHORUS_FRUIT));
        list.add(new ItemStack(Items.PORKCHOP));
        list.add(new ItemStack(Items.BEEF));
        list.add(new ItemStack(Items.CHICKEN));
        list.add(new ItemStack(Items.RABBIT));
        list.add(new ItemStack(Items.MUTTON));
        list.add(new ItemStack(Items.FISH));
        list.add(new ItemStack(Items.FISH, 1, 1));
        list.add(new ItemStack(Items.FISH, 1, 3));
        list.add(new ItemStack(Blocks.BROWN_MUSHROOM));
        list.add(new ItemStack(Blocks.RED_MUSHROOM));
        list.add(new ItemStack(Items.EGG));

        Stream.of(CulinaeFluids.SOY_MILK, CulinaeFluids.MILK, CulinaeFluids.SOY_SAUCE, CulinaeFluids.RICE_VINEGAR, CulinaeFluids.FRUIT_VINEGAR, CulinaeFluids.SESAME_OIL, CulinaeFluids.EDIBLE_OIL, CulinaeFluids.SUGARCANE_JUICE).map(fluid -> new FluidStack(fluid, Fluid.BUCKET_VOLUME)).map(FluidUtil::getFilledBucket).forEach(list::add);
    }
}
