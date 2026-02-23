package quantumstudios.culinae.plugins.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.items.ItemIngredient;
import snownee.kiwi.crafting.input.ProcessingInput;

public class ChoppingBoardKnifeRecipe implements IRecipeWrapper
{
    private final List<ItemStack> knifes = new ArrayList<>();
    private final ProcessingInput input;
    private final Material output;

    ChoppingBoardKnifeRecipe(ProcessingInput input, Material material)
    {
        this.input = input;
        this.output = material;
        knifes.addAll(OreDictionary.getOres("itemFoodCutter", false));
        if (knifes.isEmpty())
        {
            knifes.add(new ItemStack(CulinaeRegistry.KITCHEN_KNIFE));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<ItemStack> examples = input.examples();
        examples = examples.stream().filter(i -> CulinaryHub.API_INSTANCE.findMaterial(i) == output).collect(Collectors.toList());
        if (examples.isEmpty())
        {
            return;
        }
        ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(examples, knifes));
        ingredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(ItemIngredient.getAllValidFormsWithException(output, EnumSet.of(Form.FULL, Form.JUICE))));
    }

}
