package quantumstudios.culinae.internal.effect;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.prefab.DefaultTypes;
import quantumstudios.culinae.api.prefab.SimpleEffectImpl;

public class EffectRare extends SimpleEffectImpl
{

    public EffectRare()
    {
        super("rare", 0);
    }

    @Override
    public void onEaten(ItemStack stack, EntityPlayer player, CompositeFood food, List<Ingredient> ingredients, EffectCollector collector)
    {
        collector.addEffect(DefaultTypes.FOOD_LEVEL, 1);
    }

    @Override
    public boolean showInTooltips()
    {
        return false;
    }
}
