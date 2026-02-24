package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.api.process.Grinding;
import quantumstudios.culinae.api.process.Processing;
import snownee.kiwi.crafting.input.RegularItemStackInput;

import javax.annotation.Nullable;
import java.util.List;

@RegistryDescription
public class Mortar extends ProcessingRegistry<Grinding> {

    @Override
    public CulinaeProcessingRecipeManager<Grinding> getRegistry() {
        return Processing.GRINDING;
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mortar.removeByInput", type = MethodDescription.Type.REMOVAL)
    public void removeByInput(IIngredient input) {
        getRegistry().preview().stream()
            .filter(r -> input.test(r.getInput().getExample()))
            .forEach(this::remove);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:wheat')).output(item('culinae:flour'))"))
    @Property(property = "input", comp = @Comp(eq = 1))
    @Property(property = "output", comp = @Comp(eq = 1))
    @Property(property = "name")
    public static class RecipeBuilder extends AbstractRecipeBuilder<Grinding> {

        @Override
        public String getErrorMsg() { return "Error adding Culinae Mortar recipe"; }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
        }

        public String getRecipeNamePrefix() { return "mortar"; }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable Grinding register() {
            if (!validate()) return null;
            var processingInput = RegularItemStackInput.of(input.get(0));
            Grinding recipe = new Grinding(new ResourceLocation("groovyscript", getRecipeNamePrefix() + validateName()), List.of(processingInput), output.get(0), 1);
            CulinaeGSContainer.mortar.add(recipe);
            return recipe;
        }
    }
}
