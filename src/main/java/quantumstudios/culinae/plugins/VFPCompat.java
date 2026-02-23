package quantumstudios.culinae.plugins;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.internal.food.Drink;
import quantumstudios.culinae.internal.food.Drink.DrinkType;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.util.definition.ItemDefinition;

@KiwiModule(modid = culinae.MODID, name = "vanillafoodpantry", dependency = "vanillafoodpantry", optional = true)
public class VFPCompat implements IModule
{

    private static final ResourceLocation VFP_JUICE_ID = new ResourceLocation("vanillafoodpantry", "juice");

    @Override
    public void init()
    {
        Item juice = ForgeRegistries.ITEMS.getValue(VFP_JUICE_ID);
        if (juice != null)
        {
            juice.setContainerItem(Items.GLASS_BOTTLE);
            // Oh no! look at these magic numbers! and VFP is not open source!
            CulinaryHub.API_INSTANCE.registerMapping(ItemDefinition.of(juice, 201), new Ingredient(CulinaryHub.CommonMaterials.APPLE, Form.JUICE));
            CulinaryHub.API_INSTANCE.registerMapping(ItemDefinition.of(juice, 202), new Ingredient(CulinaryHub.CommonMaterials.CARROT, Form.JUICE));
            CulinaryHub.API_INSTANCE.registerMapping(ItemDefinition.of(juice, 204), new Ingredient(CulinaryHub.CommonMaterials.CACTUS, Form.JUICE));
        }
    }

    @Override
    public void postInit()
    {
        Item juice = ForgeRegistries.ITEMS.getValue(VFP_JUICE_ID);
        if (juice != null)
        {
            // Sad truth: we cannot ensure grape material are registered before VFPCompat#init
            Material grape = CulinaryHub.API_INSTANCE.findMaterial("grape");
            if (grape != null)
            {
                CulinaryHub.API_INSTANCE.registerMapping(ItemDefinition.of(juice, 212), new Ingredient(CulinaryHub.CommonMaterials.APPLE, Form.JUICE));
            }
        }
        Item soda = ForgeRegistries.ITEMS.getValue(new ResourceLocation("vanillafoodpantry", "baking_soda"));
        if (soda != null)
        {
            Drink.Builder.FEATURE_INPUTS.put(ItemDefinition.of(soda), DrinkType.SODA);
        }
    }
}
