package quantumstudios.culinae.plugins.jei;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.api.process.Grinding;
import snownee.kiwi.crafting.input.ProcessingInput;

public class MortarGenericRecipe extends GenericRecipeWrapper<Grinding>
{
    MortarGenericRecipe(Grinding recipe)
    {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<List<ItemStack>> inputs = recipe.getInputs().stream().map(ProcessingInput::examples).collect(Collectors.toList());
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
}
