package quantumstudios.culinae.internal.food;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.Effect;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.Seasoning;

public class IngredientFood extends CompositeFood
{
    public static final ResourceLocation INGREDIENT_ID = new ResourceLocation(culinae.MODID, "ingredient");

    public static final class Builder extends CompositeFood.Builder<IngredientFood>
    {

        @Override
        public Class<IngredientFood> getType()
        {
            return IngredientFood.class;
        }

        @Override
        public Optional<IngredientFood> build(CookingVessel vessel, EntityPlayer cook)
        {
            if (getIngredients().isEmpty())
            {
                return Optional.empty();
            }
            FoodValueCounter counter = new FoodValueCounter(0, 0.4F);
            this.apply(counter, vessel);
            float saturationModifier = counter.getSaturation();
            int foodLevel = counter.getHungerRegen();
            IngredientFood completed = new IngredientFood(getIngredients(), getSeasonings(), getEffects(), foodLevel, saturationModifier);
            return Optional.of(completed);
        }

        @Override
        public boolean canAddIntoThis(EntityPlayer cook, Ingredient ingredient, CookingVessel vessel)
        {
            return ingredient.getForm() != Form.JUICE;
        }

        @Override
        public boolean canAddIntoThis(EntityPlayer cook, Seasoning seasoning, CookingVessel vessel)
        {
            return false;
        }

    }

    protected IngredientFood(List<Ingredient> ingredients, List<Seasoning> seasonings, List<Effect> effects, int hungerHeal, float saturation)
    {
        super(ingredients, seasonings, effects, hungerHeal, saturation, 1);
    }

    @Override
    public ResourceLocation getIdentifier()
    {
        return INGREDIENT_ID;
    }

    @Override
    public Collection<String> getKeywords()
    {
        return Collections.singletonList("ingredient");
    }

    @Override
    public ItemStack getBaseItem()
    {
        return new ItemStack(CulinaeRegistry.INGREDIENT);
    }

    @Override
    public String getOrComputeModelType()
    {
        return "normal";
    }

    @Override
    public void setModelType(String type)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxServes()
    {
        return 1;
    }
}
