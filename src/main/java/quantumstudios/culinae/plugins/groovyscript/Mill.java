package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.api.process.Milling;
import quantumstudios.culinae.api.process.Processing;

import javax.annotation.Nullable;

@RegistryDescription
public class Mill extends VirtualizedRegistry<Milling> {

    @Override
    public void onReload() {
        removeScripted().forEach(Processing.MILLING::remove);
        restoreFromBackup().forEach(Processing.MILLING::add);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mill.add", type = MethodDescription.Type.ADDITION)
    public void add(Milling recipe) {
        addScripted(recipe);
        Processing.MILLING.add(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mill.remove", type = MethodDescription.Type.REMOVAL)
    public void remove(Milling recipe) {
        addBackup(recipe);
        Processing.MILLING.remove(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mill.removeByInput", type = MethodDescription.Type.REMOVAL)
    public void removeByInput(IIngredient input) {
        Processing.MILLING.preview().stream()
            .filter(r -> input.test(r.getInput().getExample()))
            .forEach(r -> { addBackup(r); Processing.MILLING.remove(r); });
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mill.removeAll", type = MethodDescription.Type.REMOVAL)
    public void removeAll() {
        Processing.MILLING.preview().forEach(this::addBackup);
        Processing.MILLING.removeAll();
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mill.streamRecipes", type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<Milling> streamRecipes() {
        return new SimpleObjectStream<>(Processing.MILLING.preview())
            .setRemover(this::remove);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:wheat')).output(item('culinae:flour'))"))
    public static class RecipeBuilder extends AbstractRecipeBuilder<Milling> {

        @Override
        public String getErrorMsg() { return "Error adding Culinae Mill recipe"; }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable Milling register() {
            if (!validate()) return null;
            Milling recipe = new Milling(new ResourceLocation("groovyscript", Integer.toString(input.get(0).hashCode() + output.get(0).hashCode())), input.get(0), output.get(0));
            CulinaeGSContainer.mill.add(recipe);
            return recipe;
        }
    }
}
