package quantumstudios.culinae.plugins.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyContainer;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.api.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.compat.vanilla.link.BasicLinkGenerator;
import com.cleanroommc.groovyscript.compat.vanilla.link.LinkGeneratorHooks;

import snownee.kiwi.IModule;

import snownee.kiwi.KiwiModule;

@KiwiModule(modid = "culinae", name = "groovyscript", dependency = "groovyscript")

public class GSPlugin implements IModule, GroovyPlugin {

    @Override

    public GroovyPropertyContainer createGroovyPropertyContainer() {

        return new CulinaeGSContainer();

    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> container) {
        LinkGeneratorHooks.registerLinkGenerator(new CulinaeLinkGenerator());
    }

    private static class CulinaeLinkGenerator extends BasicLinkGenerator {
        @Override
        public String id() {
            return "culinae";
        }
        @Override
        protected String domain() {
            return "https://github.com/iristhepianist/Culinae/";
        }
    }

}
