package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;

public abstract class ProcessingRegistry<T> extends VirtualizedRegistry<T> {

    public abstract CulinaeProcessingRecipeManager<T> getRegistry();

    @Override
    public void onReload() {
        var registry = getRegistry();
        removeScripted().forEach(registry::remove);
        restoreFromBackup().forEach(registry::add);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.add", type = MethodDescription.Type.ADDITION)
    public void add(T recipe) {
        addScripted(recipe);
        getRegistry().add(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.remove", type = MethodDescription.Type.REMOVAL)
    public void remove(T recipe) {
        addBackup(recipe);
        getRegistry().remove(recipe);
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.removeAll", type = MethodDescription.Type.REMOVAL)
    public void removeAll() {
        var registry = getRegistry();
        registry.preview().forEach(this::addBackup);
        registry.removeAll();
    }

    @MethodDescription(description = "groovyscript.wiki.culinae.streamRecipes", type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<T> streamRecipes() {
        return new SimpleObjectStream<>(getRegistry().preview())
            .setRemover(this::remove);
    }
}
