package quantumstudios.culinae.internal.material;

import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.prefab.DefaultTypes;
import quantumstudios.culinae.api.prefab.SimpleMaterialImpl;

public class MaterialRice extends SimpleMaterialImpl
{
    public MaterialRice(String id)
    {
        super(id, -4671304, 0, 1, 20, 2, 2F, MaterialCategory.GRAIN);
    }

    @Override
    public void onMade(CompositeFood.Builder<?> dish, Ingredient ingredient, CookingVessel vessel, EffectCollector collector)
    {
        collector.addEffect(DefaultTypes.USE_DURATION_MODIFIER, 0.4F);
    }
}
