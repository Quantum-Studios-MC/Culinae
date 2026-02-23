package quantumstudios.culinae.debug;

import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingStrategy;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.Seasoning;

/**
 * An example implementation of {@code CookingStrategy} that does nothing
 * besides logging every components out.
 */
public class DebuggingCookingStrategy implements CookingStrategy
{

    @Override
    public void beginCook(CompositeFood.Builder dish)
    {
        culinae.logger.debug("Inspecting CompositeFood@{}", System.identityHashCode(dish));
    }

    @Override
    public void preCook(Seasoning seasoning, CookingVessel vessel)
    {
        culinae.logger.debug("  - Seasoning: {}", seasoning);
    }

    @Override
    public void cook(Ingredient ingredient, CookingVessel vessel)
    {
        culinae.logger.debug("  - Ingredient: {}", ingredient);
    }

    @Override
    public void postCook(CompositeFood.Builder dish, CookingVessel vessel)
    {

    }

    @Override
    public void endCook()
    {
        culinae.logger.debug("End of inspection");
    }

}
