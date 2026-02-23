package quantumstudios.culinae.library;

import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;

/*
 * Essentially a hack that we can decrease the amount of hunger and
 * saturation modifier that a basic food item can provide.
 */
public class CulinaeFoodStats extends FoodStats
{
    public static Set<String> IDs;

    @Override
    public void addStats(ItemFood food, ItemStack item)
    {
        if (IDs == null)
        {
            IDs = Sets.newHashSet(CulinaeConfig.HARDCORE.lowerFoodLevelBlacklist);
        }
        if (CulinaeConfig.HARDCORE.enable && CulinaeConfig.HARDCORE.lowerFoodLevel && !food.getRegistryName().getNamespace().equals(culinae.MODID))
        {
            String foodItemId = Objects.requireNonNull(food.getRegistryName()).toString();
            if (IDs.contains(foodItemId))
            {
                super.addStats(food, item);
                return;
            }
            double retainRatio = CulinaeConfig.HARDCORE.foodLevelRetainRatio;
            this.addStats((int) (Math.max(1, food.getHealAmount(item) * retainRatio)), (float) (food.getSaturationModifier(item) * retainRatio));
        }
        else
        {
            super.addStats(food, item);
        }
    }
}
