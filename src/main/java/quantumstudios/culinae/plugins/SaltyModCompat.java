package quantumstudios.culinae.plugins;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Effect;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.internal.effect.EffectPotions;
import quantumstudios.culinae.internal.food.Drink;
import quantumstudios.culinae.internal.food.Drink.DrinkType;
import quantumstudios.culinae.internal.material.MaterialWithEffect;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.util.definition.ItemDefinition;
import snownee.kiwi.util.definition.OreDictDefinition;

@KiwiModule(modid = culinae.MODID, name = "saltmod", dependency = "saltmod", optional = true)
public class SaltyModCompat implements IModule
{
    @Override
    public void init()
    {
        Drink.Builder.FEATURE_INPUTS.put(OreDictDefinition.of("dustSoda"), DrinkType.SODA);

        Item fermented_saltwort = ForgeRegistries.ITEMS.getValue(new ResourceLocation("saltmod", "fermented_saltwort"));
        if (fermented_saltwort != null)
        {
            fermented_saltwort.setContainerItem(Items.GLASS_BOTTLE);
            Effect effect = new EffectPotions("fermented_saltwort").addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 2));
            Material material = CulinaryHub.API_INSTANCE.register(new MaterialWithEffect("fermented_saltwort", effect, 0x6A7A2E, 0, 0, 0, 0, 0, MaterialCategory.VEGETABLES).setValidForms(Form.JUICE_ONLY));
            CulinaryHub.API_INSTANCE.registerMapping(ItemDefinition.of(fermented_saltwort), new Ingredient(material, Form.JUICE));
        }
    }
}
