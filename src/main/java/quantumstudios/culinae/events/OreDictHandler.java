package quantumstudios.culinae.events;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.items.ItemBasicFood;
import quantumstudios.culinae.items.ItemCrops;
import quantumstudios.culinae.items.ItemMortar;
import snownee.kiwi.item.IVariant;

import java.util.function.Consumer;

public class OreDictHandler
{
    public static void init()
    {
        // NUT
        OreDictionary.registerOre("cropPeanut", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.PEANUT));

        // GRAIN
        OreDictionary.registerOre("cropWheat", Items.WHEAT);
        OreDictionary.registerOre("listAllgrain", Items.WHEAT);
        OreDictionary.registerOre("cropSesame", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SESAME));
        OreDictionary.registerOre("cropRice", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.RICE));
        OreDictionary.registerOre("cropSoybean", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SOYBEAN));
        OreDictionary.registerOre("cropCorn", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.CORN));
        OreDictionary.registerOre("foodRice", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.WHITE_RICE));

        // VEGGIE
        OreDictionary.registerOre("cropBeetroot", Items.BEETROOT);
        OreDictionary.registerOre("listAllveggie", Items.BEETROOT);
        OreDictionary.registerOre("cropPotato", Items.POTATO);
        OreDictionary.registerOre("listAllveggie", Items.POTATO);
        OreDictionary.registerOre("cropCarrot", Items.CARROT);
        OreDictionary.registerOre("listAllveggie", Items.CARROT);
        OreDictionary.registerOre("cropPumpkin", Blocks.PUMPKIN);
        OreDictionary.registerOre("listAllveggie", Blocks.PUMPKIN);
        OreDictionary.registerOre("foodMushroom", Blocks.BROWN_MUSHROOM);
        OreDictionary.registerOre("foodMushroom", Blocks.RED_MUSHROOM);
        OreDictionary.registerOre("cropTomato", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.TOMATO));
        OreDictionary.registerOre("cropChilipepper", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.CHILI));
        OreDictionary.registerOre("cropGarlic", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.GARLIC));
        OreDictionary.registerOre("cropGinger", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.GINGER));
        OreDictionary.registerOre("cropScallion", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SCALLION));
        OreDictionary.registerOre("cropTurnip", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.TURNIP));
        OreDictionary.registerOre("cropCabbage", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.CHINESE_CABBAGE));
        OreDictionary.registerOre("cropLettuce", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.LETTUCE));
        OreDictionary.registerOre("cropCucumber", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.CUCUMBER));
        OreDictionary.registerOre("cropBellpepper", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.RED_PEPPER));
        OreDictionary.registerOre("cropBellpepper", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.GREEN_PEPPER));
        OreDictionary.registerOre("cropLeek", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.LEEK));
        OreDictionary.registerOre("cropOnion", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.ONION));
        OreDictionary.registerOre("cropEggplant", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.EGGPLANT));
        OreDictionary.registerOre("cropSpinach", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SPINACH));
        OreDictionary.registerOre("cropBambooshoot", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.BAMBOO_SHOOT));

        // SPICE
        OreDictionary.registerOre("dustSalt", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.SALT));
        OreDictionary.registerOre("listAllspice", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.SALT));
        OreDictionary.registerOre("dustCrudesalt", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.CRUDE_SALT));
        OreDictionary.registerOre("listAllspice", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.CRUDE_SALT));
        OreDictionary.registerOre("dustUnrefinedsugar", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.UNREFINED_SUGAR));
        OreDictionary.registerOre("listAllspice", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.UNREFINED_SUGAR));
        OreDictionary.registerOre("listAllsugar", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.UNREFINED_SUGAR));
        OreDictionary.registerOre("listAllsugar", Items.SUGAR);
        OreDictionary.registerOre("listAllspice", Items.SUGAR);
        OreDictionary.registerOre("cropSichuanpepper", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SICHUAN_PEPPER));
        OreDictionary.registerOre("listAllspice", CulinaeRegistry.CROPS.getItemStack(ItemCrops.Variant.SICHUAN_PEPPER));

        // MEAT
        OreDictionary.registerOre("listAllbeefraw", Items.BEEF);
        OreDictionary.registerOre("listAllmeatraw", Items.BEEF);
        OreDictionary.registerOre("listAllporkraw", Items.PORKCHOP);
        OreDictionary.registerOre("listAllmeatraw", Items.PORKCHOP);
        OreDictionary.registerOre("listAllchickenraw", Items.CHICKEN);
        OreDictionary.registerOre("listAllmeatraw", Items.CHICKEN);
        OreDictionary.registerOre("listAllrabbitraw", Items.RABBIT);
        OreDictionary.registerOre("listAllmeatraw", Items.RABBIT);
        OreDictionary.registerOre("listAllmuttonraw", Items.MUTTON);
        OreDictionary.registerOre("listAllmeatraw", Items.MUTTON);

        // FRUIT
        OreDictionary.registerOre("cropApple", Items.APPLE);
        OreDictionary.registerOre("listAllfruit", Items.APPLE);
        OreDictionary.registerOre("cropMelon", Items.MELON);
        OreDictionary.registerOre("listAllfruit", Items.MELON);
        OreDictionary.registerOre("cropChorusfruit", Items.CHORUS_FRUIT);
        OreDictionary.registerOre("listAllfruit", Items.CHORUS_FRUIT);
        OreDictionary.registerOre("cropMandarin", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.MANDARIN));
        OreDictionary.registerOre("cropCitron", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.CITRON));
        OreDictionary.registerOre("cropPomelo", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.POMELO));
        OreDictionary.registerOre("cropOrange", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.ORANGE));
        OreDictionary.registerOre("cropLemon", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.LEMON));
        OreDictionary.registerOre("cropLime", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.LIME));
        OreDictionary.registerOre("cropGrapefruit", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.GRAPEFRUIT));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.MANDARIN));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.CITRON));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.POMELO));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.ORANGE));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.LEMON));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.LIME));
        OreDictionary.registerOre("listAllcitrus", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.GRAPEFRUIT));

        // MISC
        OreDictionary.registerOre("foodFirmtofu", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.TOFU));
        OreDictionary.registerOre("listAllwater", CulinaeRegistry.ITEM_MORTAR.getItemStack(ItemMortar.Variant.WATER));
        OreDictionary.registerOre("listAllwater", Items.WATER_BUCKET);
        OreDictionary.registerOre("portionWaterLarge", CulinaeRegistry.ITEM_MORTAR.getItemStack(ItemMortar.Variant.WATER));
        OreDictionary.registerOre("foodFlour", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.FLOUR));
        OreDictionary.registerOre("foodDough", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.DOUGH));
        OreDictionary.registerOre("itemFoodCutter", CulinaeRegistry.KITCHEN_KNIFE);
        OreDictionary.registerOre("logWood", CulinaeRegistry.LOG);
        OreDictionary.registerOre("plankWood", CulinaeRegistry.PLANKS);
        OreDictionary.registerOre("stickWood", CulinaeRegistry.BAMBOO);
        OreDictionary.registerOre("doorWood", CulinaeRegistry.ITEM_DOOR);
        OreDictionary.registerOre("fenceWood", CulinaeRegistry.FENCE);
        OreDictionary.registerOre("fenceGateWood", CulinaeRegistry.FENCE_GATE);
        OreDictionary.registerOre("treeSapling", new ItemStack(CulinaeRegistry.SAPLING, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(CulinaeRegistry.SHEARED_LEAVES, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("foodDrink", CulinaeRegistry.BOTTLE);
        OreDictionary.registerOre("toolMortarandpestle", CulinaeRegistry.ITEM_MORTAR);
        OreDictionary.registerOre("toolSkillet", CulinaeRegistry.WOK);
//        OreDictionary.registerOre("toolSkillet", CulinaeRegistry.FRYING_PAN);
        OreDictionary.registerOre("toolBakeware", CulinaeRegistry.PLACED_DISH);

        // foodPickles
        OreDictionary.registerOre("foodPickles", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.PICKLED_CABBAGE));
        OreDictionary.registerOre("foodPickles", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.PICKLED_CUCUMBER));
        OreDictionary.registerOre("foodPickles", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.PICKLED_PEPPER));
        OreDictionary.registerOre("foodPickles", CulinaeRegistry.BASIC_FOOD.getItemStack(ItemBasicFood.Variant.PICKLED_TURNIP));

        // The wood handle
        OreDictionary.registerOre("handleWood", CulinaeRegistry.MATERIAL.getItemStack(culinae.Materials.WOODEN_HANDLE));

        CulinaeRegistry.BASIC_FOOD.getVariants().forEach(ActionFactory.create(CulinaeRegistry.BASIC_FOOD));
        CulinaeRegistry.CROPS.getVariants().forEach(ActionFactory.create(CulinaeRegistry.CROPS));
    }

    public static class ActionFactory
    {

        public static Consumer<IVariant> create(ItemBasicFood item)
        {
            return v -> {
                boolean loaded = Loader.isModLoaded("vanillafoodpantry");
                ItemStack stack = item.getItemStack(v);
                Ingredient ingredient = CulinaryHub.API_INSTANCE.findIngredient(stack);
                if (ingredient != null)
                {
                    for (MaterialCategory category : MaterialCategory.values())
                    {
                        if (category.getOreName() != null && ingredient.getMaterial().isUnderCategoryOf(category))
                        {
                            OreDictionary.registerOre("listAll" + category.getOreName(), stack);
                            if (loaded)
                            {
                                if (category == MaterialCategory.VEGETABLES)
                                {
                                    OreDictionary.registerOre("ingredientKebabFill", stack);
                                }
                                if (category == MaterialCategory.GRAIN)
                                {
                                    OreDictionary.registerOre("ingredientCereal", stack);
                                }
                            }
                        }
                    }
                }
            };
        }

    }
}
