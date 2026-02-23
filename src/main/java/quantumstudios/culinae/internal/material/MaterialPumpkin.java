package quantumstudios.culinae.internal.material;

import java.util.Calendar;

import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.prefab.SimpleMaterialImpl;

public class MaterialPumpkin extends SimpleMaterialImpl
{

    public MaterialPumpkin(String id)
    {
        super(id, -663885, 0, 1, 1, 1, 0F, MaterialCategory.VEGETABLES);
        setValidForms(Form.ALL_FORMS_INCLUDING_JUICE);
    }

    @Override
    public void onMade(CompositeFood.Builder<?> dish, Ingredient ingredient, CookingVessel vessel, final EffectCollector collector)
    {
        if (!ingredient.hasTrait(IngredientTrait.UNDERCOOKED) && !ingredient.hasTrait(IngredientTrait.OVERCOOKED) && !ingredient.hasTrait(IngredientTrait.PLAIN))
        {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 5);
            if (calendar.get(Calendar.MONTH) != Calendar.NOVEMBER)
            {
                return;
            }
            calendar.add(Calendar.DAY_OF_YEAR, -10);
            if (calendar.get(Calendar.MONTH) != Calendar.OCTOBER)
            {
                return;
            }
            ingredient.addEffect(CulinaryHub.CommonEffects.SPOOKY);
        }
    }
}
