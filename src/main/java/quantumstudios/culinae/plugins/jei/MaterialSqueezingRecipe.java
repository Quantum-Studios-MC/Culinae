package quantumstudios.culinae.plugins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.process.prefab.MaterialSqueezing;
import quantumstudios.culinae.fluids.FluidJuice;
import quantumstudios.culinae.items.ItemIngredient;

import java.util.*;

public class MaterialSqueezingRecipe extends GenericRecipeWrapper<MaterialSqueezing>
{
    private Collection<ItemStack> extraInputs;

    MaterialSqueezingRecipe(MaterialSqueezing recipe, Collection<ItemStack> extraInputs)
    {
        super(recipe);
        this.extraInputs = extraInputs;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<ItemStack> inputs1 = ItemIngredient.getAllValidFormsWithException(recipe.getMaterial(), EnumSet.of(Form.FULL, Form.PASTE, Form.JUICE));
        List<ItemStack> inputs2 = new ArrayList<>(extraInputs);
        inputs2.addAll(inputs1);
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(inputs2));
        ingredients.setOutput(VanillaTypes.FLUID, FluidJuice.make(recipe.getMaterial(), CulinaeConfig.GENERAL.juiceSqueezingAmount));
    }

}
