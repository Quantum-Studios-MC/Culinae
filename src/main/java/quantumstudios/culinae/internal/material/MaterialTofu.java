package quantumstudios.culinae.internal.material;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import quantumstudios.culinae.api.CompositeFood.Builder;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.MaterialCategory;

public class MaterialTofu extends MaterialWithEffect
{

    public MaterialTofu(String id)
    {
        super(id, CulinaryHub.CommonEffects.HARMONY, -2311026, 0, 1, 1, 1, 0.4F, MaterialCategory.PROTEIN, MaterialCategory.GRAIN);
        setValidForms(EnumSet.of(Form.CUBED, Form.SLICED, Form.DICED, Form.MINCED));
    }

    @Override
    public void onMade(Builder<?> dish, Ingredient ingredient, CookingVessel vessel, EffectCollector collector)
    {
        removeBadTraits(ingredient);
        for (Ingredient i : dish.getIngredients())
        {
            if (i == ingredient)
            {
                continue;
            }
            if (removeBadTraits(i))
            {
                return;
            }
        }
    }

    private boolean removeBadTraits(Ingredient ingredient)
    {
        List<IngredientTrait> traits = ingredient.getAllTraits().stream().filter(IngredientTrait::isBad).collect(Collectors.toList());
        for (IngredientTrait trait : traits)
        {
            ingredient.removeTrait(trait);
        }
        return !traits.isEmpty();
    }

}
