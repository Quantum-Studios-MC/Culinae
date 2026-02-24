package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import quantumstudios.culinae.api.process.Chopping;
import quantumstudios.culinae.api.process.Processing;
import snownee.kiwi.crafting.input.RegularItemStackInput;

import javax.annotation.Nullable;

@RegistryDescription
public class AxeChopping extends VirtualizedRegistry<Chopping> {

    @Override
    public void onReload() {
        removeScripted().forEach(Processing.CHOPPING::remove);
        restoreFromBackup().forEach(Processing.CHOPPING::add);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.axe_chopping.add", type = MethodDescription.Type.ADDITION)
    public void add(Chopping recipe) {
        addScripted(recipe);
        Processing.CHOPPING.add(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.axe_chopping.remove", type = MethodDescription.Type.REMOVAL)
    public void remove(Chopping recipe) {
        addBackup(recipe);
        Processing.CHOPPING.remove(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.axe_chopping.removeByInput", type = MethodDescription.Type.REMOVAL)
    public void removeByInput(IIngredient input) {
        Processing.CHOPPING.preview().stream()
            .filter(r -> input.test(r.input.getExample()))
            .forEach(r -> { addBackup(r); Processing.CHOPPING.remove(r); });
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.axe_chopping.removeAll", type = MethodDescription.Type.REMOVAL)
    public void removeAll() {
        Processing.CHOPPING.preview().forEach(this::addBackup);
        Processing.CHOPPING.removeAll();
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.axe_chopping.streamRecipes", type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<Chopping> streamRecipes() {
        return new SimpleObjectStream<>(Processing.CHOPPING.preview())
            .setRemover(this::remove);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:log')).output(item('minecraft:planks'))"))
    public static class RecipeBuilder extends AbstractRecipeBuilder<Chopping> {

        @Override
        public String getErrorMsg() { return "Error adding Culinae Axe Chopping recipe"; }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable Chopping register() {
            if (!validate()) return null;
            var processingInput = RegularItemStackInput.of(input.get(0));
            Chopping recipe = new Chopping(new ResourceLocation("groovyscript", Integer.toString(processingInput.hashCode() + output.get(0).hashCode())), processingInput, output.get(0));
            CulinaeGSContainer.axeChopping.add(recipe);
            return recipe;
        }
    }
}
