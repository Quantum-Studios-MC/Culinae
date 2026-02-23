package quantumstudios.culinae.plugins.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import quantumstudios.culinae.api.process.CulinaeProcessingRecipe;

public abstract class GenericRecipeWrapper<R extends CulinaeProcessingRecipe> implements IRecipeWrapper
{
    final R recipe;

    public GenericRecipeWrapper(R recipe)
    {
        this.recipe = recipe;
    }
}
