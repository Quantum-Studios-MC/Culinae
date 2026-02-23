package quantumstudios.culinae.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.api.events.SkillPointUpdateEvent;
import quantumstudios.culinae.api.util.SkillUtil;
import quantumstudios.culinae.library.CulinaeFoodStats;
import quantumstudios.culinae.network.PacketSkillLevelIncreased;
import snownee.kiwi.network.NetworkChannel;

@EventBusSubscriber(modid = culinae.MODID)
public class PlayerHandler
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerSkillPointUpdate(SkillPointUpdateEvent event)
    {
        int oldLevel = SkillUtil.getLevel(event.getEntityPlayer(), event.getSkillPoint());
        int newLevel = SkillUtil.getLevel(event.getNewValue());
        if (newLevel > oldLevel)
        {
            NetworkChannel.INSTANCE.sendToPlayer(new PacketSkillLevelIncreased(event.getSkillPoint(), (short) oldLevel, (short) newLevel), (EntityPlayerMP) event.getEntityPlayer());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event)
    {
        if (CulinaeConfig.HARDCORE.enable && CulinaeConfig.HARDCORE.lowerFoodLevel && !Loader.isModLoaded("applecore"))
        {
            if (event.getEntity() instanceof EntityPlayer)
            {
                FoodStats oldFoodStats = ((EntityPlayer) event.getEntity()).foodStats;
                FoodStats newFoodStats = new CulinaeFoodStats();
                newFoodStats.foodExhaustionLevel = oldFoodStats.foodExhaustionLevel;
                newFoodStats.foodSaturationLevel = oldFoodStats.foodSaturationLevel;
                newFoodStats.foodLevel = oldFoodStats.foodLevel;
                newFoodStats.foodTimer = oldFoodStats.foodTimer;
                ((EntityPlayer) event.getEntity()).foodStats = newFoodStats;
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event)
    {
        if (event.getEntityPlayer().isPotionActive(CulinaeRegistry.COLD_BLOOD))
        {
            event.setDamageModifier(event.getDamageModifier() * 2);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onKnockBack(LivingKnockBackEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.onGround && entity.isSneaking() && entity.isPotionActive(CulinaeRegistry.TOUGHNESS))
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onProjectileImpact(LivingAttackEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.onGround && entity.isSneaking() && event.getSource().isProjectile() && entity.isPotionActive(CulinaeRegistry.TOUGHNESS))
        {
            event.setCanceled(true);
        }
    }
}
