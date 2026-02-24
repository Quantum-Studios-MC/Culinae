package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.api.process.Grinding;
import quantumstudios.culinae.api.process.Processing;
import snownee.kiwi.crafting.input.RegularItemStackInput;

import javax.annotation.Nullable;
import java.util.List;

@RegistryDescription
public class Mortar extends VirtualizedRegistry<Grinding> {

    @Override
    public void onReload() {
        removeScripted().forEach(Processing.GRINDING::remove);
        restoreFromBackup().forEach(Processing.GRINDING::add);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mortar.add", type = MethodDescription.Type.ADDITION)
    public void add(Grinding recipe) {
        addScripted(recipe);
        Processing.GRINDING.add(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mortar.remove", type = MethodDescription.Type.REMOVAL)
    public void remove(Grinding recipe) {
        addBackup(recipe);
        Processing.GRINDING.remove(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mortar.removeByInput", type = MethodDescription.Type.REMOVAL)
    public void removeByInput(IIngredient input) {
        Processing.GRINDING.preview().stream()
            .filter(r -> input.test(r.getInputs().get(0).getExample()))
            .forEach(r -> { addBackup(r); Processing.GRINDING.remove(r); });
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mortar.removeAll", type = MethodDescription.Type.REMOVAL)
    public void removeAll() {
        Processing.GRINDING.preview().forEach(this::addBackup);
        Processing.GRINDING.removeAll();
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.mortar.streamRecipes", type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<Grinding> streamRecipes() {
        return new SimpleObjectStream<>(Processing.GRINDING.preview())
            .setRemover(this::remove);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:wheat')).output(item('culinae:flour'))"))
    public static class RecipeBuilder extends AbstractRecipeBuilder<Grinding> {

        @Override
        public String getErrorMsg() { return "Error adding Culinae Mortar recipe"; }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable Grinding register() {
            if (!validate()) return null;
            var processingInput = RegularItemStackInput.of(input.get(0));
            Grinding recipe = new Grinding(new ResourceLocation("groovyscript", Integer.toString(processingInput.hashCode() + output.get(0).hashCode())), List.of(processingInput), output.get(0), 1);
            CulinaeGSContainer.mortar.add(recipe);
            return recipe;
        }
    }
}
