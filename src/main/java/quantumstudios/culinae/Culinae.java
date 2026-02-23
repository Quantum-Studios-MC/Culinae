package quantumstudios.culinae;

import java.util.Arrays;
import java.util.Calendar;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import quantumstudios.culinae.command.CommandRegistry;
import quantumstudios.culinae.crafting.DrinkBrewingRecipe;
import quantumstudios.culinae.crafting.RecipeRegistry;
import quantumstudios.culinae.events.BetterHarvest;
import quantumstudios.culinae.events.DisableGenericFood;
import quantumstudios.culinae.events.OreDictHandler;
import quantumstudios.culinae.events.SpawnHandler;
import quantumstudios.culinae.internal.CulinaeInternalGateway;
import quantumstudios.culinae.internal.capabilities.CulinarySkillCapability;
import quantumstudios.culinae.internal.capabilities.FoodContainerCapability;
import quantumstudios.culinae.items.BehaviorArmDispense;
import quantumstudios.culinae.items.BehaviorWokInteraction;
import quantumstudios.culinae.items.ItemCrops;
import quantumstudios.culinae.network.CulinaeGuiHandler;
import quantumstudios.culinae.network.PacketCustomEvent;
import quantumstudios.culinae.network.PacketNameFood;
import quantumstudios.culinae.network.PacketSkillLevelIncreased;
import quantumstudios.culinae.world.gen.WorldGenBamboo;
import quantumstudios.culinae.world.gen.WorldGenCitrusTrees;
import quantumstudios.culinae.world.gen.WorldGenGarden;
import snownee.kiwi.item.IVariant;
import snownee.kiwi.network.NetworkChannel;

@Mod(
        modid = culinae.MODID, name = culinae.NAME, version = "@VERSION_INJECT@", useMetadata = true, acceptedMinecraftVersions = "[1.12, 1.13)"
)
public class Culinae
{
    public static final String MODID = "culinae";
    public static final String NAME = "Culinae";
    public static final CreativeTabs CREATIVE_TAB = new CulinaeItemGroup();

    public static Logger logger;

    private static final Culinae INSTANCE = new Culinae();

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @SidedProxy(
            serverSide = "quantumstudios.culinae.server.CulinaeServerProxy", clientSide = "quantumstudios.culinae.client.CulinaeClientProxy"
    )
    public static CulinaeSidedProxy sidedDelegate;

    @Mod.InstanceFactory
    public static Culinae getInstance()
    {
        return INSTANCE;
    }

    private Culinae()
    {
        // No-op, private access only
    }

    public static boolean aprilFools;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        Calendar calendar = Calendar.getInstance();
        aprilFools = calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) == 1;
        CulinaeInternalGateway.init();
        CulinarySkillCapability.init();
        FoodContainerCapability.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        CulinaeInternalGateway.deferredInit();
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new CulinaeGuiHandler());

        MinecraftForge.EVENT_BUS.register(new SpawnHandler());
        if (CulinaeConfig.GENERAL.betterHarvest)
        {
            MinecraftForge.EVENT_BUS.register(new BetterHarvest());
        }
        if (CulinaeConfig.HARDCORE.enable && CulinaeConfig.HARDCORE.disableGenericFood)
        {
            MinecraftForge.EVENT_BUS.register(new DisableGenericFood());
        }
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CulinaeRegistry.MATERIAL, new BehaviorArmDispense());
        BehaviorWokInteraction behaviorWokInteraction = new BehaviorWokInteraction();
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(CulinaeRegistry.PLACED_DISH), behaviorWokInteraction);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CulinaeRegistry.SPICE_BOTTLE, behaviorWokInteraction);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CulinaeRegistry.IRON_SPATULA, behaviorWokInteraction);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CulinaeRegistry.INGREDIENT, behaviorWokInteraction);
        OreDictHandler.init();
        RecipeRegistry.init();
        NetworkChannel.INSTANCE.register(PacketCustomEvent.class);
        NetworkChannel.INSTANCE.register(PacketSkillLevelIncreased.class);
        NetworkChannel.INSTANCE.register(PacketNameFood.class);
        if (CulinaeConfig.GENERAL.basicSeedsWeight > 0)
        {
            MinecraftForge.addGrassSeed(CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SESAME), CulinaeConfig.GENERAL.basicSeedsWeight);
            MinecraftForge.addGrassSeed(CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SOYBEAN), CulinaeConfig.GENERAL.basicSeedsWeight);
            MinecraftForge.addGrassSeed(CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.PEANUT), CulinaeConfig.GENERAL.basicSeedsWeight);
        }
        Arrays.sort(CulinaeConfig.WORLD_GEN.bamboosGenDimensions);
        Arrays.sort(CulinaeConfig.WORLD_GEN.fruitTreesGenDimensions);
        Arrays.sort(CulinaeConfig.WORLD_GEN.cropsGenDimensions);
        if (CulinaeConfig.WORLD_GEN.cropsGenRate > 0 && CulinaeConfig.WORLD_GEN.cropsGenDimensions.length > 0)
        {
            MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenGarden());
        }
        if (CulinaeConfig.WORLD_GEN.bamboosGenRate > 0 && CulinaeConfig.WORLD_GEN.bamboosGenDimensions.length > 0)
        {
            MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenBamboo());
        }
        if (CulinaeConfig.WORLD_GEN.fruitTreesGenRate > 0 && CulinaeConfig.WORLD_GEN.fruitTreesGenDimensions.length > 0)
        {
            MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenCitrusTrees());
        }
        BrewingRecipeRegistry.addRecipe(new DrinkBrewingRecipe());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        RecipeRegistry.postInit();
    }

    public static enum Materials implements IVariant<Void>
    {
        WOODEN_ARM,
        WOODEN_HANDLE,
        SALT,
        CRUDE_SALT,
        CHILI_POWDER,
        SICHUAN_PEPPER_POWDER,
        BAMBOO_CHARCOAL,
        UNREFINED_SUGAR;

        @Override
        public int getMeta()
        {
            return ordinal();
        }

        @Override
        public Void getValue()
        {
            return null;
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e)
    {
        CommandRegistry.registryCommands(e);
    }
}
