package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPlugin;

import com.cleanroommc.groovyscript.api.GroovyPropertyContainer;

import snownee.kiwi.IModule;

import snownee.kiwi.KiwiModule;

@KiwiModule(modid = "culinae", name = "groovyscript", dependency = "groovyscript")

public class GSPlugin implements IModule, GroovyPlugin {

    @Override

    public GroovyPropertyContainer createGroovyPropertyContainer() {

        return new CulinaeGSContainer();

    }

}
