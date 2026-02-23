package quantumstudios.culinae.internal.material;

import quantumstudios.culinae.api.CompositeFood.Builder;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.prefab.SimpleMaterialImpl;

public class MaterialTomato extends SimpleMaterialImpl
{

    public MaterialTomato(String id)
    {
        super(id, -2681308, 0, 1, 1, 1, 0F, MaterialCategory.VEGETABLES);
        setValidForms(Form.ALL_FORMS_INCLUDING_JUICE);
    }

    @Override
    public void onMade(Builder<?> dish, Ingredient ingredient, CookingVessel vessel, EffectCollector collector)
    {
        if (ingredient.hasTrait(IngredientTrait.PLAIN) || ingredient.hasTrait(IngredientTrait.OVERCOOKED))
        {
            return;
        }
        if (dish.getSeasonings().stream().anyMatch(e -> e.hasKeyword("sugar")))
        {
            dish.addEffect(CulinaryHub.CommonEffects.SUSTAINED_RELEASE);
        }
    }

}
