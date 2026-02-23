package quantumstudios.culinae.plugins.jei;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.items.ItemIngredient;
import snownee.kiwi.crafting.input.ProcessingInput;

public class MortarPasteRecipe implements IRecipeWrapper
{
    private final ProcessingInput input;
    private final Material material;

    MortarPasteRecipe(ProcessingInput input, Material mat)
    {
        this.input = input;
        this.material = mat;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<ItemStack> examples = input.examples();
        examples = examples.stream().filter(i -> CulinaryHub.API_INSTANCE.findIngredient(i).getMaterial() == material).collect(Collectors.toList());
        if (examples.isEmpty())
        {
            return;
        }
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(examples));
        ingredients.setOutput(VanillaTypes.ITEM, ItemIngredient.make(material, Form.PASTE));
    }

}
