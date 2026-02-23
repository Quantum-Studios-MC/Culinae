package quantumstudios.culinae.internal.material;

import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.MaterialCategory;

public class MaterialPufferfish extends MaterialWithEffect
{

    public MaterialPufferfish(String id)
    {
        super(id, CulinaryHub.CommonEffects.ALWAYS_EDIBLE, 0xFFFFE1C4, 0, 1, 1, 1, 0.2F, MaterialCategory.FISH);
        setValidForms(Form.ALL_FORMS);
    }

    @Override
    public void onCrafted(Ingredient ingredient)
    {
        ingredient.addEffect(CulinaryHub.CommonEffects.PUFFERFISH_POISON);
    }

    @Override
    public void onMade(CompositeFood.Builder<?> dish, Ingredient ingredient, CookingVessel vessel, EffectCollector collector)
    {
        super.onMade(dish, ingredient, vessel, collector);
        ingredient.addEffect(CulinaryHub.CommonEffects.WATER_BREATHING);
    }

}
