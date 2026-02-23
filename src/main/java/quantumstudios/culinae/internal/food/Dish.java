package quantumstudios.culinae.internal.food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.CompositeFood;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Effect;
import quantumstudios.culinae.api.EffectCollector;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.Seasoning;
import quantumstudios.culinae.api.Spice;
import quantumstudios.culinae.api.prefab.DefaultCookedCollector;
import quantumstudios.culinae.api.util.SkillUtil;
import quantumstudios.culinae.internal.CulinaePersistenceCenter;
import quantumstudios.culinae.internal.CulinaeSharedSecrets;
import snownee.kiwi.util.NBTHelper;

/**
 * Dish represents a specific food preparation which are made from combination of
 * various ingredients and seasonings. Typically, a cookware like wok is capable
 * to produce an instance of this.
 */
public class Dish extends CompositeFood
{
    public static final ResourceLocation DISH_ID = new ResourceLocation(culinae.MODID, "dish");

    private String modelType;

    public Dish(List<Ingredient> ingredients, List<Seasoning> seasonings, List<Effect> effects, int hungerHeal, float saturation)
    {
        super(ingredients, seasonings, effects, hungerHeal, saturation);
    }

    @Override
    public ResourceLocation getIdentifier()
    {
        return DISH_ID;
    }

    @Override
    public Collection<String> getKeywords()
    {
        // TODO Anything... else?
        return Arrays.asList("east-asian", "wok");
    }

    @Override
    public String getOrComputeModelType()
    {
        if (this.modelType != null)
        {
            return this.modelType;
        }

        if (ingredients.stream().anyMatch(i -> i.getMaterial().isUnderCategoryOf(MaterialCategory.FISH)))
        {
            this.modelType = "fish0";
        }
        else if (ingredients.stream().anyMatch(i -> i.getMaterial() == CulinaryHub.CommonMaterials.RICE))
        {
            this.modelType = "rice0";
        }
        else if (ingredients.stream().allMatch(i -> i.getMaterial().isUnderCategoryOf(MaterialCategory.MEAT)))
        {
            this.modelType = Math.random() >= 0.5 ? "meat1" : "meat0";
        }
        else if (ingredients.stream().allMatch(i -> i.getMaterial().isUnderCategoryOf(MaterialCategory.VEGETABLES)))
        {
            this.modelType = Math.random() >= 0.5 ? "veges0" : "veges1";
        }
        else
        {
            this.modelType = Math.random() >= 0.5 ? "mixed0" : "mixed1";
        }

        return this.modelType;
    }

    @Override
    public void setModelType(String type)
    {
        this.modelType = type;
    }

    @Override
    public ItemStack getBaseItem()
    {
        return new ItemStack(CulinaeRegistry.DISH);
    }

    @Override
    public void onEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        super.onEaten(stack, worldIn, player);

        if (!worldIn.isRemote && CulinaeConfig.HARDCORE.enable && CulinaeConfig.HARDCORE.badSkillPunishment)
        {
            int countPlain = (int) getIngredients().stream().filter(i -> i.getAllTraits().contains(IngredientTrait.PLAIN) || i.getAllTraits().contains(IngredientTrait.UNDERCOOKED)).count();
            int countOvercooked = (int) getIngredients().stream().filter(i -> i.getAllTraits().contains(IngredientTrait.OVERCOOKED)).count();

            if (countPlain / (float) getIngredients().size() > 0.8F)
            {
                Potion potion = worldIn.rand.nextBoolean() ? MobEffects.MINING_FATIGUE : MobEffects.WEAKNESS;
                player.addPotionEffect(new PotionEffect(potion, 300 * getIngredients().size()));
            }
            if (countOvercooked / (float) getIngredients().size() > 0.8F)
            {
                Potion potion = worldIn.rand.nextBoolean() ? MobEffects.POISON : MobEffects.NAUSEA;
                player.addPotionEffect(new PotionEffect(potion, 100 * getIngredients().size()));
            }
        }
    }

    public static NBTTagCompound serialize(Dish dish)
    {
        NBTTagCompound data = new NBTTagCompound();
        NBTTagList ingredientList = new NBTTagList();

        for (Ingredient ingredient : dish.ingredients)
        {
            ingredientList.appendTag(CulinaePersistenceCenter.serialize(ingredient));
        }
        data.setTag(CulinaeSharedSecrets.KEY_INGREDIENT_LIST, ingredientList);

        NBTTagList seasoningList = new NBTTagList();
        for (Seasoning seasoning : dish.seasonings)
        {
            seasoningList.appendTag(CulinaePersistenceCenter.serialize(seasoning));
        }
        data.setTag(CulinaeSharedSecrets.KEY_SEASONING_LIST, seasoningList);

        NBTTagList effectList = new NBTTagList();
        for (Effect effect : dish.effects)
        {
            effectList.appendTag(new NBTTagString(effect.getID()));
        }
        data.setTag(CulinaeSharedSecrets.KEY_EFFECT_LIST, effectList);

        String modelType = dish.getOrComputeModelType();
        if (modelType != null)
        {
            data.setString("type", modelType);
        }

        data.setInteger(CulinaeSharedSecrets.KEY_FOOD_LEVEL, dish.getFoodLevel());
        data.setFloat(CulinaeSharedSecrets.KEY_SATURATION_MODIFIER, dish.getSaturationModifier());
        data.setInteger(CulinaeSharedSecrets.KEY_SERVES, dish.getServes());
        data.setInteger(CulinaeSharedSecrets.KEY_MAX_SERVES, dish.getMaxServes());
        data.setFloat(CulinaeSharedSecrets.KEY_USE_DURATION, dish.getUseDurationModifier());
        return data;
    }

    public static Dish deserialize(NBTTagCompound data)
    {
        NBTHelper helper = NBTHelper.of(data);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<Seasoning> seasonings = new ArrayList<>();
        ArrayList<Effect> effects = new ArrayList<>();
        NBTTagList ingredientList = data.getTagList(CulinaeSharedSecrets.KEY_INGREDIENT_LIST, Constants.NBT.TAG_COMPOUND);
        for (NBTBase baseTag : ingredientList)
        {
            if (baseTag.getId() == Constants.NBT.TAG_COMPOUND)
            {
                ingredients.add(CulinaePersistenceCenter.deserializeIngredient((NBTTagCompound) baseTag));
            }
        }

        NBTTagList seasoningList = data.getTagList(CulinaeSharedSecrets.KEY_SEASONING_LIST, Constants.NBT.TAG_COMPOUND);
        for (NBTBase baseTag : seasoningList)
        {
            if (baseTag.getId() == Constants.NBT.TAG_COMPOUND)
            {
                seasonings.add(CulinaePersistenceCenter.deserializeSeasoning((NBTTagCompound) baseTag));
            }
        }

        NBTTagList effectList = data.getTagList(CulinaeSharedSecrets.KEY_EFFECT_LIST, Constants.NBT.TAG_STRING);
        for (NBTBase baseTag : effectList)
        {
            if (baseTag.getId() == Constants.NBT.TAG_STRING)
            {
                effects.add(CulinaryHub.API_INSTANCE.findEffect(((NBTTagString) baseTag).getString()));
            }
        }

        int serves = helper.getInt(CulinaeSharedSecrets.KEY_SERVES);
        int maxServes = helper.getInt(CulinaeSharedSecrets.KEY_MAX_SERVES);
        float duration = helper.getFloat(CulinaeSharedSecrets.KEY_USE_DURATION, 1);
        int foodLevel = helper.getInt(CulinaeSharedSecrets.KEY_FOOD_LEVEL);
        float saturation = helper.getFloat(CulinaeSharedSecrets.KEY_SATURATION_MODIFIER);

        Dish dish = new Dish(ingredients, seasonings, effects, foodLevel, saturation);
        dish.setMaxServes(maxServes);
        dish.setServes(serves);
        dish.setUseDurationModifier(duration);

        dish.setModelType(helper.getString("type"));

        return dish;
    }

    public static final class Builder extends CompositeFood.Builder<Dish>
    {
        private Dish completed;
        private int water, oil, temperature;
        private Random rand = new Random();

        private Builder()
        {
            this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        Builder(List<Ingredient> ingredients, List<Seasoning> seasonings, List<Effect> effects)
        {
            super(ingredients, seasonings, effects);
        }

        @Override
        public Class<Dish> getType()
        {
            return Dish.class;
        }

        @Override
        public boolean canAddIntoThis(EntityPlayer cook, Ingredient ingredient, CookingVessel vessel)
        {
            if (ingredient.getForm() == Form.JUICE)
            {
                return false;
            }
            if (SkillUtil.hasPlayerLearnedSkill(cook, CulinaryHub.CommonSkills.BIGGER_SIZE))
            {
                return getIngredients().size() < getMaxIngredientLimit() && ingredient.getMaterial().canAddInto(this, ingredient);
            }
            else
            {
                return getIngredients().size() < getMaxIngredientLimit() * 0.75 && ingredient.getMaterial().canAddInto(this, ingredient);
            }
        }

        @Override
        public boolean addSeasoning(EntityPlayer cook, Seasoning seasoning, CookingVessel vessel)
        {
            boolean result = super.addSeasoning(cook, seasoning, vessel);
            if (result)
            {
                if (seasoning.hasKeyword("water"))
                {
                    water += seasoning.getSize() * 100;
                }
                if (seasoning.hasKeyword("oil"))
                {
                    oil += seasoning.getSize() * 100;
                }
            }
            return result;
        }

        public int getWaterAmount()
        {
            return this.water;
        }

        public int getOilAmount()
        {
            return this.oil;
        }

        public int getTemperature()
        {
            return temperature;
        }

        public void setWaterAmount(int water)
        {
            this.water = water;
        }

        public void setOilAmount(int oil)
        {
            this.oil = oil;
        }

        public void setTemperature(int temperature)
        {
            this.temperature = temperature;
        }

        @Override
        public Optional<Dish> build(final CookingVessel vessel, EntityPlayer cook)
        {
            if (this.getIngredients().isEmpty())
            {
                return Optional.empty();
            }
            else if (this.completed != null)
            {
                return Optional.of(this.completed);
            }
            else
            {
                // Calculate hunger regeneration and saturation modifier
                FoodValueCounter counter = new FoodValueCounter(0, 0.4F);
                this.apply(counter, vessel);
                float saturationModifier = counter.getSaturation();
                int foodLevel = counter.getHungerRegen();

                // Grant player cook skill bonus

                // CulinarySkillPointContainer skill = playerIn.getCapability(CulinaryCapabilities.CULINARY_SKILL, null);
                // double modifier = 1.0;
                // if (skill != null)
                // {
                // modifier *= SkillUtil.getPlayerSkillLevel((EntityPlayerMP) playerIn, CulinaeSharedSecrets.KEY_SKILL_WOK);
                // SkillUtil.increaseSkillPoint((EntityPlayerMP) playerIn, 1);
                // }

                // Compute side effects

                int countMat = getIngredients().stream().map(Ingredient::getMaterial).collect(Collectors.toSet()).size();
                int serves = DEFAULT_SERVE_AMOUNT;
                if (countMat < 3)
                {
                    serves -= (2 - countMat) * 3 + rand.nextInt(3);
                }
                EffectCollector collector = new DefaultCookedCollector(serves);

                int seasoningSize = 0;
                int waterSize = 0;
                for (Seasoning seasoning : this.getSeasonings())
                {
                    Spice spice = seasoning.getSpice();
                    spice.onMade(this, seasoning, vessel, collector);
                    if (seasoning.hasKeyword("water"))
                    {
                        waterSize += seasoning.getSize();
                    }
                    else if (!seasoning.hasKeyword("oil"))
                    {
                        seasoningSize += seasoning.getSize();
                    }
                }
                boolean isPlain = seasoningSize == 0 || (getIngredients().size() / seasoningSize) / (1 + waterSize / 3) > 3;

                for (Ingredient ingredient : this.getIngredients())
                {
                    Material material = ingredient.getMaterial();
                    Set<MaterialCategory> categories = material.getCategories();
                    if (!categories.contains(MaterialCategory.SEAFOOD) && !categories.contains(MaterialCategory.FRUIT))
                    {
                        if (isPlain)
                        {
                            ingredient.addTrait(IngredientTrait.PLAIN);
                        }
                        EnumSet<Form> validForms = ingredient.getMaterial().getValidForms();
                        int count = 0;
                        for (Form form : new Form[] { Form.SLICED, Form.DICED, Form.MINCED, Form.SHREDDED, Form.PASTE })
                        {
                            if (validForms.contains(form))
                            {
                                ++count;
                            }
                        }
                        if (count > 0 && !ingredient.hasTrait(IngredientTrait.OVERCOOKED) && rand.nextFloat() > 0.35F * (ingredient.getForm().ordinal() + 1))
                        {
                            ingredient.addTrait(IngredientTrait.UNDERCOOKED);
                        }
                    }
                }
                for (Ingredient ingredient : this.getIngredients())
                {
                    ingredient.getMaterial().onMade(this, ingredient, vessel, collector);
                }

                this.completed = new Dish(this.getIngredients(), this.getSeasonings(), this.getEffects(), foodLevel, saturationModifier);
                collector.apply(this.completed, cook); // TODO (3TUSK): See, this is why I say this couples too many responsibilities
                // this.completed.setQualityBonus(modifier);
                this.completed.getOrComputeModelType();
                return Optional.of(completed);
            }
        }

        public static Dish.Builder create()
        {
            return new Dish.Builder();
        }

        public static NBTTagCompound toNBT(Dish.Builder builder)
        {
            NBTTagCompound data = new NBTTagCompound();
            NBTTagList ingredientList = new NBTTagList();
            for (Ingredient ingredient : builder.getIngredients())
            {
                ingredientList.appendTag(CulinaePersistenceCenter.serialize(ingredient));
            }
            data.setTag(CulinaeSharedSecrets.KEY_INGREDIENT_LIST, ingredientList);

            NBTTagList seasoningList = new NBTTagList();
            for (Seasoning seasoning : builder.getSeasonings())
            {
                seasoningList.appendTag(CulinaePersistenceCenter.serialize(seasoning));
            }
            data.setTag(CulinaeSharedSecrets.KEY_SEASONING_LIST, seasoningList);

            NBTTagList effectList = new NBTTagList();
            for (Effect effect : builder.getEffects())
            {
                effectList.appendTag(new NBTTagString(effect.getID()));
            }
            data.setTag(CulinaeSharedSecrets.KEY_EFFECT_LIST, effectList);
            data.setInteger(CulinaeSharedSecrets.KEY_WATER, builder.getWaterAmount());
            data.setInteger(CulinaeSharedSecrets.KEY_OIL, builder.getOilAmount());
            data.setInteger("temperature", builder.getTemperature());

            return data;
        }

        public static Dish.Builder fromNBT(NBTTagCompound data)
        {
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ArrayList<Seasoning> seasonings = new ArrayList<>();
            ArrayList<Effect> effects = new ArrayList<>();
            NBTTagList ingredientList = data.getTagList(CulinaeSharedSecrets.KEY_INGREDIENT_LIST, Constants.NBT.TAG_COMPOUND);
            for (NBTBase baseTag : ingredientList)
            {
                if (baseTag.getId() == Constants.NBT.TAG_COMPOUND)
                {
                    Ingredient ingredient = CulinaePersistenceCenter.deserializeIngredient((NBTTagCompound) baseTag);
                    if (ingredient != null)
                    {
                        ingredients.add(ingredient);
                    }
                }
            }

            NBTTagList seasoningList = data.getTagList(CulinaeSharedSecrets.KEY_SEASONING_LIST, Constants.NBT.TAG_COMPOUND);
            for (NBTBase baseTag : seasoningList)
            {
                if (baseTag.getId() == Constants.NBT.TAG_COMPOUND)
                {
                    seasonings.add(CulinaePersistenceCenter.deserializeSeasoning((NBTTagCompound) baseTag));
                }
            }

            NBTTagList effectList = data.getTagList(CulinaeSharedSecrets.KEY_EFFECT_LIST, Constants.NBT.TAG_STRING);
            for (NBTBase baseTag : effectList)
            {
                if (baseTag.getId() == Constants.NBT.TAG_STRING)
                {
                    effects.add(CulinaryHub.API_INSTANCE.findEffect(((NBTTagString) baseTag).getString()));
                }
            }

            Dish.Builder builder = new Dish.Builder(ingredients, seasonings, effects);

            if (data.hasKey(CulinaeSharedSecrets.KEY_WATER, Constants.NBT.TAG_INT))
            {
                builder.setWaterAmount(data.getInteger(CulinaeSharedSecrets.KEY_WATER));
            }
            if (data.hasKey(CulinaeSharedSecrets.KEY_OIL, Constants.NBT.TAG_INT))
            {
                builder.setOilAmount(data.getInteger(CulinaeSharedSecrets.KEY_OIL));
            }
            if (data.hasKey("temperature", Constants.NBT.TAG_INT))
            {
                builder.setTemperature(data.getInteger("temperature"));
            }

            return builder;
        }
    }
}
