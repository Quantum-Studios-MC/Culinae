package quantumstudios.culinae.internal.material;

import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.prefab.SimpleMaterialImpl;

public class MaterialChorusFruit extends SimpleMaterialImpl
{

    public MaterialChorusFruit(String id)
    {
        super(id, -6271615, 0, 1, 1, 1, -0.1F, MaterialCategory.FRUIT, MaterialCategory.SUPERNATURAL);
        setValidForms(Form.ALL_FORMS_INCLUDING_JUICE);
    }

    @Override
    public void onMade(CompositeFood.Builder<?> dish, Ingredient ingredient, CookingVessel vessel, final EffectCollector collector)
    {
        ingredient.addEffect(CulinaryHub.CommonEffects.TELEPORT);
    }

}
