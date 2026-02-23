package quantumstudios.culinae.plugins.nutrition;

import java.util.HashMap;
import java.util.Map;

import ca.wescook.nutrition.capabilities.INutrientManager;
import ca.wescook.nutrition.nutrients.Nutrient;
import ca.wescook.nutrition.nutrients.NutrientList;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.events.ConsumeCompositeFoodEvent;
import quantumstudios.culinae.internal.CulinaePersistenceCenter;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;

@KiwiModule(modid = culinae.MODID, name = "nutrition", dependency = "nutrition", optional = true)
public class NutritionCompat implements IModule
{
    private static Capability<INutrientManager> NUTRITION_CAPABILITY;
    public static final Map<MaterialCategory, Nutrient> materialCategoryToNutrient = new HashMap<>();

    static void injectCap(Capability<INutrientManager> capability)
    {
        NUTRITION_CAPABILITY = capability;
    }

    @Override
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void refreshData()
    {
        if (materialCategoryToNutrient.isEmpty())
        {
            for (Nutrient nutrient : NutrientList.get())
            {
                switch (nutrient.name)
                {
                case "fruit":
                    materialCategoryToNutrient.put(MaterialCategory.FRUIT, nutrient);
                    break;
                case "grain":
                    materialCategoryToNutrient.put(MaterialCategory.GRAIN, nutrient);
                    break;
                case "vegetable":
                    materialCategoryToNutrient.put(MaterialCategory.VEGETABLES, nutrient);
                    break;
                case "protein":
                    materialCategoryToNutrient.put(MaterialCategory.FISH, nutrient);
                    materialCategoryToNutrient.put(MaterialCategory.SEAFOOD, nutrient);
                    materialCategoryToNutrient.put(MaterialCategory.MEAT, nutrient);
                    materialCategoryToNutrient.put(MaterialCategory.NUT, nutrient);
                    materialCategoryToNutrient.put(MaterialCategory.PROTEIN, nutrient);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onEatStuff(ConsumeCompositeFoodEvent.Post event)
    {
        EntityPlayer consumer = event.getConsumer();
        INutrientManager manager = consumer.getCapability(NUTRITION_CAPABILITY, null);
        if (manager == null)
        {
            culinae.logger.debug("Entity {} has no INutrientManager. Skip nutrition calculation.", consumer);
            return;
        }
        Object2DoubleMap<MaterialCategory> map = new Object2DoubleArrayMap<>();
        for (Ingredient ingredient : event.getFood().getIngredients())
        {
            for (MaterialCategory category : ingredient.getMaterial().getCategories())
            {
                map.put(category, map.getOrDefault(category, 0D) + 1F);
            }
        }
        for (Object2DoubleMap.Entry<MaterialCategory> entry : map.object2DoubleEntrySet())
        {
            if (entry.getKey() == MaterialCategory.SUPERNATURAL)
            {
                manager.add(NutrientList.get(), (float) (entry.getDoubleValue() * 0.1F));
            }
            else if (materialCategoryToNutrient.containsKey(entry.getKey()))
            {
                manager.add(materialCategoryToNutrient.get(entry.getKey()), (float) (entry.getDoubleValue() * 0.5));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onItemUseFinish(LivingEntityUseItemEvent.Finish event)
    {
        Entity entity = event.getEntity();
        if (materialCategoryToNutrient.isEmpty())
        {
            refreshData();
        }
        INutrientManager manager = entity.getCapability(NUTRITION_CAPABILITY, null);
        if (manager == null)
        {
            culinae.logger.debug("Entity {} has no INutrientManager. Skip nutrition calculation.", entity);
            return;
        }
        ItemStack item = event.getItem();
        if (item.getItem() == CulinaeRegistry.INGREDIENT)
        {
            NBTTagCompound data = item.getTagCompound();
            if (data == null)
            {
                return;
            }
            Ingredient ingredient = CulinaePersistenceCenter.deserializeIngredient(data);
            if (ingredient != null)
            {
                for (MaterialCategory category : ingredient.getMaterial().getCategories())
                {
                    if (category == MaterialCategory.SUPERNATURAL)
                    {
                        manager.add(NutrientList.get(), CulinaeConfig.COMPAT.supernaturalNutrientModifier);
                    }
                    else
                    {
                        manager.add(materialCategoryToNutrient.get(category), CulinaeConfig.COMPAT.normalNutrientModifier);
                    }
                }
            }
        }
    }
}
