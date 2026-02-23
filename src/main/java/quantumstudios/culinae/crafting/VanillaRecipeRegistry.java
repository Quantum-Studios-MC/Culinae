package quantumstudios.culinae.crafting;

import org.apache.commons.lang3.Validate;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.items.ItemBasicFood;
import quantumstudios.culinae.library.DummyVanillaRecipe;

@Mod.EventBusSubscriber(modid = culinae.MODID)
public final class VanillaRecipeRegistry
{
    // Yes, we understand that this is a huge hack, but there is no way around unless we can also kill advancements.
    @SubscribeEvent
    public static void onVanillaRecipeRegistry(final RegistryEvent.Register<IRecipe> event)
    {
        if (CulinaeConfig.HARDCORE.enable)
        {
            ModContainer CulinaeModContainer = Loader.instance().activeModContainer();
            Validate.isTrue(culinae.MODID.equals(Validate.notNull(CulinaeModContainer).getModId()));
            Loader.instance().setActiveModContainer(Loader.instance().getMinecraftModContainer());

            if (CulinaeConfig.HARDCORE.harderSugarProduction)
            {
                final ResourceLocation sugarRecipeName = new ResourceLocation("minecraft", "sugar");
                event.getRegistry().register(new DummyVanillaRecipe().setRegistryName(sugarRecipeName));
            }

            if (CulinaeConfig.HARDCORE.harderBreadProduction)
            {
                final ResourceLocation breadRecipeName = new ResourceLocation("minecraft", "bread");
                event.getRegistry().register(new DummyVanillaRecipe().setRegistryName(breadRecipeName));
            }

            if (CulinaeConfig.HARDCORE.harderCookieProduction)
            {
                final ResourceLocation cookieRecipeName = new ResourceLocation("minecraft", "cookie");
                event.getRegistry().register(new DummyVanillaRecipe().setRegistryName(cookieRecipeName));
            }

            Loader.instance().setActiveModContainer(CulinaeModContainer);
        }

        GameRegistry.addSmelting(CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.DOUGH), new ItemStack(Items.BREAD), 0.35F);
        GameRegistry.addSmelting(CulinaeRegistry.IRON_SPATULA, new ItemStack(Items.IRON_INGOT), 0.1F);
        GameRegistry.addSmelting(CulinaeRegistry.KITCHEN_KNIFE, new ItemStack(Items.IRON_INGOT), 0.1F);
        GameRegistry.addSmelting(CulinaeRegistry.WOK, new ItemStack(Items.IRON_INGOT, 3), 0.1F);
        GameRegistry.addSmelting(CulinaeRegistry.BAMBOO, CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.BAMBOO_CHARCOAL), 0.1F);
        GameRegistry.addSmelting(CulinaeRegistry.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
    }

    @SubscribeEvent
    public static void getBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() == Item.getItemFromBlock(CulinaeRegistry.BAMBOO))
        {
            event.setBurnTime(200);
        }
        else if (stack.getItem() == Item.getItemFromBlock(CulinaeRegistry.SAPLING))
        {
            event.setBurnTime(100);
        }
        else if (stack.getItem() == CulinaeRegistry.MATERIAL && stack.getItemDamage() == culinae.Materials.WOODEN_HANDLE.getMeta())
        {
            event.setBurnTime(100);
        }
        else if (stack.getItem() == CulinaeRegistry.MATERIAL && stack.getItemDamage() == culinae.Materials.BAMBOO_CHARCOAL.getMeta())
        {
            event.setBurnTime(1200);
        }
    }
}
