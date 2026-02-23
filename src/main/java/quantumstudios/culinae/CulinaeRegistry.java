package quantumstudios.culinae;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumPlantType;
import quantumstudios.culinae.blocks.BlockBamboo;
import quantumstudios.culinae.blocks.BlockBambooPlant;
import quantumstudios.culinae.blocks.BlockBasin;
import quantumstudios.culinae.blocks.BlockBasinColored;
import quantumstudios.culinae.blocks.BlockChoppingBoard;
import quantumstudios.culinae.blocks.BlockCorn;
import quantumstudios.culinae.blocks.BlockCulinaeCrops;
import quantumstudios.culinae.blocks.BlockDoubleCrops;
import quantumstudios.culinae.blocks.BlockDrinkro;
import quantumstudios.culinae.blocks.BlockFirePit;
import quantumstudios.culinae.blocks.BlockJar;
import quantumstudios.culinae.blocks.BlockMill;
import quantumstudios.culinae.blocks.BlockModDoor;
import quantumstudios.culinae.blocks.BlockModLeaves;
import quantumstudios.culinae.blocks.BlockModLog;
import quantumstudios.culinae.blocks.BlockModSapling;
import quantumstudios.culinae.blocks.BlockModTrapdoor;
import quantumstudios.culinae.blocks.BlockMortar;
import quantumstudios.culinae.blocks.BlockPlacedDish;
import quantumstudios.culinae.blocks.BlockShearedLeaves;
import quantumstudios.culinae.blocks.BlockSqueezer;
import quantumstudios.culinae.blocks.BlockTofu;
import quantumstudios.culinae.items.ItemBasicFood;
import quantumstudios.culinae.items.ItemBottle;
import quantumstudios.culinae.items.ItemCrops;
import quantumstudios.culinae.items.ItemDish;
import quantumstudios.culinae.items.ItemDrink;
import quantumstudios.culinae.items.ItemDrinkro;
import quantumstudios.culinae.items.ItemEmptyPlate;
import quantumstudios.culinae.items.ItemFan;
import quantumstudios.culinae.items.ItemIngredient;
import quantumstudios.culinae.items.ItemIronSpatula;
import quantumstudios.culinae.items.ItemKitchenKnife;
import quantumstudios.culinae.items.ItemManual;
import quantumstudios.culinae.items.ItemModBoat;
import quantumstudios.culinae.items.ItemModDoor;
import quantumstudios.culinae.items.ItemMortar;
import quantumstudios.culinae.items.ItemSpiceBottle;
import quantumstudios.culinae.potions.PotionDispersal;
import quantumstudios.culinae.potions.PotionDrunk;
import quantumstudios.culinae.potions.PotionSustainedRelease;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.block.BlockMod;
import snownee.kiwi.block.BlockModFence;
import snownee.kiwi.block.BlockModFenceGate;
import snownee.kiwi.item.ItemMod;
import snownee.kiwi.item.ItemModVariantsNew;
import snownee.kiwi.potion.PotionMod;
import snownee.kiwi.util.definition.ItemDefinition;

@KiwiModule(modid = culinae.MODID)
public class CulinaeRegistry implements IModule
{
    public static final BlockTofu TOFU_BLOCK = new BlockTofu("tofu_block");

    public static final BlockBamboo BAMBOO = new BlockBamboo("bamboo");

    public static final BlockBambooPlant BAMBOO_PLANT = new BlockBambooPlant("bamboo_plant");

    public static final ItemCrops CROPS = new ItemCrops("crops");

    public static final BlockCulinaeCrops PEANUT = new BlockCulinaeCrops("peanut", ItemDefinition.of(CROPS, ItemCrops.Variant.PEANUT.getMeta()));
    public static final BlockCulinaeCrops SESAME = new BlockCulinaeCrops("sesame", ItemDefinition.of(CROPS, ItemCrops.Variant.SESAME.getMeta()));
    public static final BlockCulinaeCrops SOYBEAN = new BlockCulinaeCrops("soybean", ItemDefinition.of(CROPS, ItemCrops.Variant.SOYBEAN.getMeta()));
    public static final BlockCulinaeCrops RICE = new BlockCulinaeCrops("rice", EnumPlantType.Water, ItemDefinition.of(CROPS, ItemCrops.Variant.RICE.getMeta()));
    public static final BlockCulinaeCrops TOMATO = new BlockCulinaeCrops("tomato", ItemDefinition.of(CROPS, ItemCrops.Variant.TOMATO.getMeta()));
    public static final BlockCulinaeCrops CHILI = new BlockCulinaeCrops("chili", EnumPlantType.Nether, ItemDefinition.of(CROPS, ItemCrops.Variant.CHILI.getMeta()));
    public static final BlockCulinaeCrops GARLIC = new BlockCulinaeCrops("garlic", ItemDefinition.of(CROPS, ItemCrops.Variant.GARLIC.getMeta()));
    public static final BlockCulinaeCrops GINGER = new BlockCulinaeCrops("ginger", ItemDefinition.of(CROPS, ItemCrops.Variant.GINGER.getMeta()));
    public static final BlockCulinaeCrops SICHUAN_PEPPER = new BlockCulinaeCrops("sichuan_pepper", ItemDefinition.of(CROPS, ItemCrops.Variant.SICHUAN_PEPPER.getMeta()));
    public static final BlockCulinaeCrops SCALLION = new BlockCulinaeCrops("scallion", ItemDefinition.of(CROPS, ItemCrops.Variant.SCALLION.getMeta()));
    public static final BlockCulinaeCrops TURNIP = new BlockCulinaeCrops("turnip", ItemDefinition.of(CROPS, ItemCrops.Variant.TURNIP.getMeta()));
    public static final BlockCulinaeCrops CHINESE_CABBAGE = new BlockCulinaeCrops("chinese_cabbage", ItemDefinition.of(CROPS, ItemCrops.Variant.CHINESE_CABBAGE.getMeta()));
    public static final BlockCulinaeCrops LETTUCE = new BlockCulinaeCrops("lettuce", ItemDefinition.of(CROPS, ItemCrops.Variant.LETTUCE.getMeta()));
    public static final BlockCulinaeCrops CORN = new BlockCorn("corn");
    public static final BlockDoubleCrops CUCUMBER = new BlockDoubleCrops("cucumber", ItemDefinition.of(CROPS, ItemCrops.Variant.CUCUMBER.getMeta()));
    public static final BlockCulinaeCrops GREEN_PEPPER = new BlockCulinaeCrops("green_pepper", ItemDefinition.of(CROPS, ItemCrops.Variant.GREEN_PEPPER.getMeta()));
    public static final BlockCulinaeCrops RED_PEPPER = new BlockCulinaeCrops("red_pepper", ItemDefinition.of(CROPS, ItemCrops.Variant.RED_PEPPER.getMeta()));
    public static final BlockCulinaeCrops LEEK = new BlockCulinaeCrops("leek", ItemDefinition.of(CROPS, ItemCrops.Variant.LEEK.getMeta()));
    public static final BlockCulinaeCrops ONION = new BlockCulinaeCrops("onion", ItemDefinition.of(CROPS, ItemCrops.Variant.ONION.getMeta()));
    public static final BlockCulinaeCrops EGGPLANT = new BlockCulinaeCrops("eggplant", ItemDefinition.of(CROPS, ItemCrops.Variant.EGGPLANT.getMeta()));
    public static final BlockCulinaeCrops SPINACH = new BlockCulinaeCrops("spinach", ItemDefinition.of(CROPS, ItemCrops.Variant.SPINACH.getMeta()));

    public static final BlockMortar MORTAR = new BlockMortar("mortar");

    public static final BlockJar JAR = new BlockJar("jar");

    public static final BlockMill MILL = new BlockMill("mill");

    public static final BlockPlacedDish PLACED_DISH = new BlockPlacedDish("placed_dish");
    public static final ItemEmptyPlate EMPTY_PLATE = new ItemEmptyPlate(PLACED_DISH);

    public static final BlockFirePit FIRE_PIT = new BlockFirePit("fire_pit");

    //    public static final BlockUtensil FRYING_PAN = new BlockUtensil("frying_pan", TileFryingPan::new, new AxisAlignedBB(0d, 0d, 0d, 1d, 0.225d, 1d));

    public static final BlockChoppingBoard CHOPPING_BOARD = new BlockChoppingBoard("chopping_board");

    public static final BlockBasin WOODEN_BASIN = new BlockBasin("wooden_basin", Material.WOOD);

    public static final BlockBasin EARTHEN_BASIN = new BlockBasin("earthen_basin", Material.ROCK);

    public static final BlockBasinColored EARTHEN_BASIN_COLORED = new BlockBasinColored("earthen_basin_colored", Material.ROCK);

    public static final BlockSqueezer SQUEEZER = new BlockSqueezer("squeezer");

    public static final BlockDrinkro DRINKRO = new BlockDrinkro("drinkro");
    public static final ItemDrinkro ITEM_DRINKRO = new ItemDrinkro(DRINKRO);

    public static final ItemBasicFood<Void, ItemBasicFood.Variant> BASIC_FOOD = new ItemBasicFood<Void, ItemBasicFood.Variant>("food", ItemBasicFood.Variant.values());

    public static final BlockModLog LOG = new BlockModLog("log");
    public static final BlockMod PLANKS = new BlockMod("planks", Material.WOOD);
    public static final BlockModSapling SAPLING = new BlockModSapling("sapling");
    public static final BlockModLeaves LEAVES_CITRON = new BlockModLeaves("leaves_citron", ItemBasicFood.Variant.CITRON);
    public static final BlockModLeaves LEAVES_GRAPEFRUIT = new BlockModLeaves("leaves_grapefruit", ItemBasicFood.Variant.GRAPEFRUIT);
    public static final BlockModLeaves LEAVES_LEMON = new BlockModLeaves("leaves_lemon", ItemBasicFood.Variant.LEMON);
    public static final BlockModLeaves LEAVES_LIME = new BlockModLeaves("leaves_lime", ItemBasicFood.Variant.LIME);
    public static final BlockModLeaves LEAVES_MANDARIN = new BlockModLeaves("leaves_mandarin", ItemBasicFood.Variant.MANDARIN);
    public static final BlockModLeaves LEAVES_ORANGE = new BlockModLeaves("leaves_orange", ItemBasicFood.Variant.ORANGE);
    public static final BlockModLeaves LEAVES_POMELO = new BlockModLeaves("leaves_pomelo", ItemBasicFood.Variant.POMELO);

    public static final BlockShearedLeaves SHEARED_LEAVES = new BlockShearedLeaves("sheared_leaves");

    public static final BlockModTrapdoor TRAPDOOR = new BlockModTrapdoor("trapdoor", Material.WOOD);

    public static final BlockModDoor DOOR = new BlockModDoor("door", Material.WOOD);
    public static final ItemModDoor ITEM_DOOR = new ItemModDoor(DOOR);

    public static final Block FENCE = new BlockModFence("fence", PLANKS.getDefaultState()).setHardness(2).setResistance(5);
    public static final Block FENCE_GATE = new BlockModFenceGate("fence_gate", PLANKS.getDefaultState()).setHardness(2).setResistance(5);

    public static final ItemIronSpatula IRON_SPATULA = new ItemIronSpatula("iron_spatula");

    public static final ItemFan FAN = new ItemFan("fan");

    public static final ItemDish DISH = new ItemDish("dish");

    public static final ItemDrink DRINK = new ItemDrink("drink");

    public static final ItemBottle BOTTLE = new ItemBottle("glass_bottle");

    public static final ItemMortar ITEM_MORTAR = new ItemMortar(MORTAR.getName(), MORTAR);

    public static final ItemIngredient INGREDIENT = new ItemIngredient();

    public static final ItemKitchenKnife KITCHEN_KNIFE = new ItemKitchenKnife("kitchen_knife");

    public static final ItemSpiceBottle SPICE_BOTTLE = new ItemSpiceBottle("spice_bottle");

    public static final ItemMod WOK = new ItemMod("wok");

    //    public static final ItemMod FRYING_PAN = new ItemMod("frying_pan");

    public static final ItemManual MANUAL = new ItemManual("manual");

    public static final ItemModBoat BOAT = new ItemModBoat("boat");

    public static final ItemModVariantsNew MATERIAL = new ItemModVariantsNew("material", culinae.Materials.values());

    public static final PotionDispersal DISPERSAL = new PotionDispersal("dispersal", 0);

    public static final PotionMod HOT = new PotionMod("hot", true, 1, false, 0xff4500, -1, true);

    public static final PotionMod EFFECT_RESISTANCE = new PotionMod("effect_resistance", true, 2, true, 0xccccff, -1, false, false);

    public static final PotionMod COLD_BLOOD = new PotionMod("cold_blood", true, 3, false, 0x9F79EE, -1, true);

    public static final PotionMod TOUGHNESS = new PotionMod("toughness", true, 4, false, 0x943943, -1, true);

    public static final PotionSustainedRelease SUSTAINED_RELEASE = new PotionSustainedRelease("sustained_release");

    public static final PotionDrunk DRUNK = new PotionDrunk("drunk", 5);

    @Override
    public void init()
    {
        Item.getItemFromBlock(PLACED_DISH).setContainerItem(Item.getItemFromBlock(PLACED_DISH));
        WOK.setCreativeTab(culinae.CREATIVE_TAB).setContainerItem(WOK);
        PLANKS.setCreativeTab(culinae.CREATIVE_TAB);
        TRAPDOOR.setCreativeTab(culinae.CREATIVE_TAB).setHardness(3.0F);
        FENCE.setCreativeTab(culinae.CREATIVE_TAB);
        FENCE_GATE.setCreativeTab(culinae.CREATIVE_TAB);
        MATERIAL.setCreativeTab(culinae.CREATIVE_TAB);
    }
}
