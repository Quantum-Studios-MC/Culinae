package quantumstudios.culinae.internal.effect;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.prefab.DefaultTypes;
import quantumstudios.culinae.api.prefab.SimpleEffectImpl;

public class EffectSustainedRelease extends SimpleEffectImpl
{

    public EffectSustainedRelease()
    {
        super("sustained_release", 0xF82423);
    }

    @Override
    public void onEaten(ItemStack stack, EntityPlayer player, CompositeFood food, List<Ingredient> ingredients, EffectCollector collector)
    {
        int foodLevel = collector.getEffect(DefaultTypes.FOOD_LEVEL);
        collector.addEffect(DefaultTypes.FOOD_LEVEL, -foodLevel);
        if (foodLevel > 0)
        {
            player.addPotionEffect(new PotionEffect(CulinaeRegistry.SUSTAINED_RELEASE, foodLevel * 100 - 1, 0, false, false));
        }
    }

    @Override
    public int getPriority()
    {
        return -5;
    }

}
