package quantumstudios.culinae.internal.material;

import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.Effect;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.prefab.SimpleMaterialImpl;

public class MaterialWithEffect extends SimpleMaterialImpl
{
    private final Effect effect;

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue)
    {
        this(id, effect, rawColor, cookedColor, waterValue, oilValue, heatValue, 0.1F);
    }

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue, float foodSaturationModifier)
    {
        super(id, rawColor, cookedColor, waterValue, oilValue, heatValue, foodSaturationModifier);
        this.effect = effect;
    }

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue, float foodSaturationModifier, MaterialCategory... categories)
    {
        super(id, rawColor, cookedColor, waterValue, oilValue, heatValue, foodSaturationModifier, categories);
        this.effect = effect;
    }

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue, float foodSaturationModifier, float boilHeat, int boilTime, MaterialCategory... categories)
    {
        super(id, rawColor, cookedColor, waterValue, oilValue, heatValue, foodSaturationModifier, boilHeat, boilTime, categories);
        this.effect = effect;
    }

    @Override
    public void onMade(CompositeFood.Builder<?> dish, Ingredient ingredient, CookingVessel vessel, final EffectCollector collector)
    {
        if (ingredient.hasTrait(IngredientTrait.UNDERCOOKED) || ingredient.hasTrait(IngredientTrait.OVERCOOKED))
        {
            return;
        }
        ingredient.addEffect(effect);
    }
}
