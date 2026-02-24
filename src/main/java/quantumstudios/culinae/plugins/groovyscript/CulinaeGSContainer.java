package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPropertyContainer;

public class CulinaeGSContainer extends GroovyPropertyContainer {

    public final AxeChopping axeChopping = new AxeChopping();
    public final Mortar mortar = new Mortar();
    public final Mill mill = new Mill();

}
