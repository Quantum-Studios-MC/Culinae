package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPropertyContainer;

/**
 * GroovyScript integration container for the Culinae mod.
 * Provides access to scripting functionalities such as axe chopping, mortar, and mill operations.
 * Allows users to dynamically modify recipes and behaviors using GroovyScript.
 */
public class CulinaeGSContainer extends GroovyPropertyContainer {

    public final AxeChopping axeChopping = new AxeChopping();
    public final Mortar mortar = new Mortar();
    public final Mill mill = new Mill();

    public CulinaeGSContainer() {
        addProperty(axeChopping);
        addProperty(mortar);
        addProperty(mill);
    }
}
