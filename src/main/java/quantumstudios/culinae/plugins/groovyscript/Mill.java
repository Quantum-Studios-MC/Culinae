package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.api.process.Milling;
import quantumstudios.culinae.api.process.Processing;

import javax.annotation.Nullable;

@RegistryDescription
public class Mill extends ProcessingRegistry<Milling> {

    @Override
    public CulinaeProcessingRecipeManager<Milling> getRegistry() {
        return Processing.MILLING;
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mill.removeByInput", type = MethodDescription.Type.REMOVAL)
    public void removeByInput(IIngredient input) {
        getRegistry().preview().stream()
            .filter(r -> input.test(r.getInput().getExample()))
            .forEach(this::remove);
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:wheat')).output(item('culinae:flour'))"))
    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @Property(property = "input", comp = @Comp(eq = 1))
    @Property(property = "output", comp = @Comp(eq = 1))
    @Property(property = "name")
    public static class RecipeBuilder extends AbstractRecipeBuilder<Milling> {

        @Override
        public String getErrorMsg() { return "Error adding Culinae Mill recipe"; }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
        }

        public String getRecipeNamePrefix() { return "mill"; }

        public String validateName() {
            if (super.name == null || super.name.trim().isEmpty()) {
                throw new IllegalArgumentException("Recipe name must be set and not empty");
            }
            // Additional validation if needed (e.g., no special chars)
            return super.name;
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable Milling register() {
            if (!validate()) return null;
            Milling recipe = new Milling(new ResourceLocation("groovyscript", getRecipeNamePrefix() + validateName()), input.get(0), output.get(0));
            CulinaeGSContainer.mill.add(recipe);
            return recipe;
        }
    }
}
