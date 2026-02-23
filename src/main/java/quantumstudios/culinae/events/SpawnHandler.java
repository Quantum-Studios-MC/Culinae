package quantumstudios.culinae.events;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.util.I18nUtil;

public class SpawnHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (CulinaeConfig.GENERAL.spawnBook)
        {
            // TODO Re-evaluate this part
            NBTTagCompound playerData = event.player.getEntityData();
            NBTTagCompound data;
            if (playerData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            {
                data = playerData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            }
            else
            {
                data = new NBTTagCompound();
            }

            String key = culinae.MODID + ":spawned_book";
            if (!data.hasKey(key) || !data.getBoolean(key))
            {
                if (!Loader.isModLoaded("patchouli"))
                {
                    event.player.sendMessage(new TextComponentTranslation(I18nUtil.getFullKey("tip.spawn0")));
                    event.player.sendMessage(new TextComponentTranslation(I18nUtil.getFullKey("tip.spawn1")));
                }
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(CulinaeRegistry.MANUAL));
                data.setBoolean(key, true);
                playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
            }
        }

        if (CulinaeConfig.GENERAL.autoRecipeUnlocking && event.player instanceof EntityPlayerMP)
        {
            event.player.unlockRecipes(StreamSupport.stream(CraftingManager.REGISTRY.spliterator(), false)
                    .filter(r -> r.getRegistryName().getNamespace().equals(culinae.MODID))
                    .collect(Collectors.toList())
            );
        }
    }
}
