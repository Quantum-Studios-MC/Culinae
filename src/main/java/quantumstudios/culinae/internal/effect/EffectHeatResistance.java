package quantumstudios.culinae.internal.effect;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.plugins.TANCompat;
import snownee.kiwi.Kiwi;

public class EffectHeatResistance extends EffectPotions
{

    public EffectHeatResistance()
    {
        super("heat_resistance");
        if (Loader.isModLoaded("toughasnails") && Kiwi.isLoaded(new ResourceLocation(culinae.MODID, "toughasnails")) && TANCompat.heat_resistance != null)
        {
            addPotionEffect(new PotionEffect(TANCompat.heat_resistance, 600));
        }
    }

    @Override
    public void onEaten(ItemStack stack, EntityPlayer player, CompositeFood food, List<Ingredient> ingredients, EffectCollector collector)
    {
        player.extinguish();
        super.onEaten(stack, player, food, ingredients, collector);
    }

}
