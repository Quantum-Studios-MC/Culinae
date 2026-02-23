package quantumstudios.culinae.api.prefab;

import net.minecraft.potion.PotionEffect;
import quantumstudios.culinae.api.EffectType;

public class DefaultTypes
{
    public static final EffectType<PotionEffect> POTION = () -> PotionEffect.class;
    public static final EffectType<Float> USE_DURATION_MODIFIER = () -> Float.class;
    public static final EffectType<Integer> FOOD_LEVEL = () -> Integer.class;
    public static final EffectType<Integer> SERVE_AMOUNT = () -> Integer.class;

    private DefaultTypes()
    {
    }
}
