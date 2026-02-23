package quantumstudios.culinae.plugins.jei;

import java.util.Arrays;
import java.util.Collections;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import quantumstudios.culinae.api.process.Chopping;

public class ChoppingBoardAxeRecipe extends GenericRecipeWrapper<Chopping>
{
    ChoppingBoardAxeRecipe(Chopping recipe)
    {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(recipe.input.examples(), JEICompat.AXES));
        ingredients.setOutputs(VanillaTypes.ITEM, Collections.singletonList(recipe.getOutput()));
    }
}
