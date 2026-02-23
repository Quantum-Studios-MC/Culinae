package quantumstudios.culinae.plugins.top;

import java.util.function.Function;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import quantumstudios.culinae.Culinae;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;

@KiwiModule(modid = culinae.MODID, name = "theoneprobe", dependency = "theoneprobe", optional = true)
public class TOPCompat implements IModule
{
    @Override
    public void preInit()
    {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "quantumstudios.culinae.plugins.top.TOPCompat$GetTheOneProbe");
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void>
    {

        @Override
        public Void apply(ITheOneProbe probe)
        {
            probe.registerProvider(new CulinaeCropProvider());
            probe.registerProvider(new CulinaeMachineProvider());
            return null;
        }

    }
}
