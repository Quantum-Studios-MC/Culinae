package quantumstudios.culinae.internal;

import java.util.EnumSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Effect;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.api.Seasoning;
import quantumstudios.culinae.api.Spice;

public interface CulinaePersistenceCenter
{

    static NBTTagCompound serialize(Ingredient ingredient)
    {
        NBTTagCompound data = new NBTTagCompound();
        if (ingredient.getMaterial() == null)
        {
            return data;
        }
        data.setString(CulinaeSharedSecrets.KEY_MATERIAL, ingredient.getMaterial().getID());
        data.setString(CulinaeSharedSecrets.KEY_FORM, ingredient.getForm().name());
        data.setInteger(CulinaeSharedSecrets.KEY_DONENESS, ingredient.getDoneness());
        data.setIntArray(CulinaeSharedSecrets.KEY_TRAITS, ingredient.getAllTraits().stream().mapToInt(Enum::ordinal).toArray());
        NBTTagList effectList = new NBTTagList();
        for (Effect effect : ingredient.getEffects())
        {
            effectList.appendTag(new NBTTagString(effect.getID()));
        }
        data.setTag(CulinaeSharedSecrets.KEY_EFFECT_LIST, effectList);
        return data;
    }

    static NBTTagCompound serialize(Seasoning seasoning)
    {
        NBTTagCompound data = new NBTTagCompound();
        data.setString(CulinaeSharedSecrets.KEY_SPICE, seasoning.getSpice().getID());
        data.setInteger(CulinaeSharedSecrets.KEY_QUANTITY, seasoning.getSize());
        return data;
    }

    static @Nullable Ingredient deserializeIngredient(@Nonnull NBTTagCompound data)
    {
        if (!data.hasKey(CulinaeSharedSecrets.KEY_MATERIAL, Constants.NBT.TAG_STRING))
        {
            return null;
        }
        final String materialKey = data.getString(CulinaeSharedSecrets.KEY_MATERIAL);
        Material material = CulinaryHub.API_INSTANCE.findMaterial(materialKey);
        if (material == null)
        {
            // TODO (3TUSK): how about only throwing exceptions in dev environment
            //throw new NullPointerException(String.format("Unknown material '%s'", materialKey));
            return null;
        }
        Form form = Form.valueOf(data.getString(CulinaeSharedSecrets.KEY_FORM));
        EnumSet<IngredientTrait> traits = EnumSet.noneOf(IngredientTrait.class);
        for (int id : data.getIntArray(CulinaeSharedSecrets.KEY_TRAITS))
        {
            traits.add(IngredientTrait.VALUES[id]);
        }
        Ingredient result = new Ingredient(material, form, traits);
        if (data.hasKey(CulinaeSharedSecrets.KEY_DONENESS, Constants.NBT.TAG_INT))
        {
            result.setDoneness(data.getInteger(CulinaeSharedSecrets.KEY_DONENESS));
        }
        for (NBTBase baseTag : data.getTagList(CulinaeSharedSecrets.KEY_EFFECT_LIST, Constants.NBT.TAG_STRING))
        {
            if (baseTag instanceof NBTTagString)
            {
                Effect e = CulinaryHub.API_INSTANCE.findEffect(((NBTTagString) baseTag).getString());
                if (e != null)
                {
                    result.addEffect(e);
                }
            }
        }
        return result;
    }

    static Seasoning deserializeSeasoning(@Nonnull NBTTagCompound data)
    {
        Spice spice = CulinaryHub.API_INSTANCE.findSpice(data.getString(CulinaeSharedSecrets.KEY_SPICE));
        if (spice == null)
        {
            throw new IllegalArgumentException();
        }
        int quantity = data.getInteger(CulinaeSharedSecrets.KEY_QUANTITY);
        return new Seasoning(spice, quantity);
    }

}
