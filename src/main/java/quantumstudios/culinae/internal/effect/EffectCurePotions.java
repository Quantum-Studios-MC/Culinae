package quantumstudios.culinae.internal.effect;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.prefab.SimpleEffectImpl;

public class EffectCurePotions extends SimpleEffectImpl
{

    public EffectCurePotions()
    {
        super("cure_potions", 0xC1FFC1);
    }

    @Override
    public void onEaten(ItemStack stack, EntityPlayer player, CompositeFood food, List<Ingredient> ingredients, EffectCollector collector)
    {
        player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }
}
