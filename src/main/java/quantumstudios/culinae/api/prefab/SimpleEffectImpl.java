package quantumstudios.culinae.api.prefab;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.Effect;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;

public class SimpleEffectImpl implements Effect
{
    private final String name;
    private final int color;

    public SimpleEffectImpl(String name, int color)
    {
        this.name = name;
        this.color = color;
    }

    @Override
    public void onEaten(ItemStack stack, EntityPlayer player, CompositeFood food, List<Ingredient> ingredients, EffectCollector collector)
    {
        // Do nothing by default
    }

    @Override
    public int getPriority()
    {
        return 0;
    }

    @Override
    public String getID()
    {
        return name;
    }

    @Override
    public String getName()
    {
        return "culinae.effect." + name + ".name";
    }

    @Override
    public String getDescription()
    {
        return "culinae.effect." + name + ".tip";
    }

    @Override
    public int getColor()
    {
        return this.color;
    }

}
