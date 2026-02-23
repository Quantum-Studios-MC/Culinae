package quantumstudios.culinae.entities;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;

@EventBusSubscriber(modid = culinae.MODID)
public class EntityRegistry
{
    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event)
    {
        if (CulinaeConfig.GENERAL.bambooBlowpipe)
        {
            event.getRegistry().register(EntityEntryBuilder.create().entity(EntitySeed.class).id(new ResourceLocation(culinae.MODID, "seed"), 0).name(culinae.MODID + ".seed").tracker(160, 20, true).build());
        }
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntityModBoat.class).id(new ResourceLocation(culinae.MODID, "boat"), 1).name(culinae.MODID + ".boat").tracker(80, 3, true).build());
    }
}
