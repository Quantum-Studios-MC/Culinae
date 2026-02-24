package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.api.process.Chopping;
import quantumstudios.culinae.api.process.Processing;
import snownee.kiwi.crafting.input.RegularItemStackInput;

import javax.annotation.Nullable;

@RegistryDescription
public class AxeChopping extends ProcessingRegistry<Chopping> {

    @Override
    public CulinaeProcessingRecipeManager<Chopping> getRegistry() {
        return Processing.CHOPPING;
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.axe_chopping.removeByInput", type = MethodDescription.Type.REMOVAL)
    public void removeByInput(IIngredient input) {
        getRegistry().preview().stream()
            .filter(r -> input.test(r.getInput().getExample()))
            .forEach(this::remove);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:log')).output(item('minecraft:planks'))"))
    @Property(property = "input", comp = @Comp(eq = 1))
    @Property(property = "output", comp = @Comp(eq = 1))
    @Property(property = "name")
    public static class RecipeBuilder extends AbstractRecipeBuilder<Chopping> {

        @Override
        public String getErrorMsg() { return "Error adding Culinae Axe Chopping recipe"; }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
        }

        public String getRecipeNamePrefix() { return "axe_chopping"; }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable Chopping register() {
            if (!validate()) return null;
            var processingInput = RegularItemStackInput.of(input.get(0));
            Chopping recipe = new Chopping(new ResourceLocation("groovyscript", getRecipeNamePrefix() + validateName()), processingInput, output.get(0));
            CulinaeGSContainer.axeChopping.add(recipe);
            return recipe;
        }
    }
}
