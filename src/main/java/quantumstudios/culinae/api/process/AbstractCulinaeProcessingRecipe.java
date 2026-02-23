package quantumstudios.culinae.api.process;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public abstract class AbstractCulinaeProcessingRecipe implements CulinaeProcessingRecipe
{

    protected static final String INTERNAL_NAMESPACE = "Culinae";

    private final ResourceLocation identifier;

    protected AbstractCulinaeProcessingRecipe(ResourceLocation identifier)
    {
        this.identifier = identifier;
    }

    @Override
    public @Nonnull ResourceLocation getIdentifier()
    {
        return this.identifier;
    }
}
