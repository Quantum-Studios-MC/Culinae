package quantumstudios.culinae.library;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import snownee.kiwi.util.NBTHelper;

public class RarityManager
{
    private static final String RARITY_TAG = culinae.MODID + ":Rarity";

    public static ItemStack setRarity(ItemStack stack, EnumRarity rarity)
    {
        return setRarity(stack, rarity.ordinal());
    }

    public static ItemStack setRarity(ItemStack stack, int rarity)
    {
        if (CulinaeConfig.GENERAL.rareCrops && !stack.isEmpty())
        {
            if (rarity == 0)
            {
                NBTHelper.of(stack).remove(RARITY_TAG);
            }
            else
            {
                NBTHelper.of(stack).setInt(RARITY_TAG, rarity);
            }
        }
        return stack;
    }

    public static EnumRarity getRarity(ItemStack stack)
    {
        int rarity = stack.getRarity().ordinal() + NBTHelper.of(stack).getInt(RARITY_TAG);
        EnumRarity[] values = EnumRarity.values();
        rarity = MathHelper.clamp(rarity, 0, values.length - 1);
        return values[rarity];
    }
}
