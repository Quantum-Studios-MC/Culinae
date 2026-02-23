package quantumstudios.culinae.api.process;

public final class Processing
{

    private Processing()
    {
        throw new UnsupportedOperationException("No instance for you");
    }

    // Mortar grinding
    public static final CulinaeProcessingRecipeManager<Grinding> GRINDING = CulinaeProcessingRecipeManager.of(Grinding::descendingCompare);

    // Mill grinding
    public static final CulinaeProcessingRecipeManager<Milling> MILLING = CulinaeProcessingRecipeManager.of();

    // Jarring
    public static final CulinaeProcessingRecipeManager<Vessel> VESSEL = CulinaeProcessingRecipeManager.of();

    // Chopping board chopping
    public static final CulinaeProcessingRecipeManager<Chopping> CHOPPING = CulinaeProcessingRecipeManager.of(Chopping::descendingCompare);

    // Basin squeezing
    public static final CulinaeProcessingRecipeManager<BasinInteracting> SQUEEZING = CulinaeProcessingRecipeManager.of(BasinInteracting::descendingCompare);

    // Basin item throwing
    public static final CulinaeProcessingRecipeManager<BasinInteracting> BASIN_THROWING = CulinaeProcessingRecipeManager.of(BasinInteracting::descendingCompare);

    // Basin boiling
    public static final CulinaeProcessingRecipeManager<Boiling> BOILING = CulinaeProcessingRecipeManager.of();
}
