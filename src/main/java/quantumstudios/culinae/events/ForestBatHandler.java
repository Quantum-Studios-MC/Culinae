package quantumstudios.culinae.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.items.ItemBasicFood;
import quantumstudios.culinae.items.ItemBasicFood.Variant;

@EventBusSubscriber(modid = culinae.MODID)
public class ForestBatHandler
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemPickup(EntityItemPickupEvent event)
    {
        ItemStack stack = event.getItem().getItem();
        EntityPlayer player = event.getEntityPlayer();
        if (stack.getItem().getCreatorModId(stack).equals("torcherino"))
        {
            InventoryPlayer inventory = player.inventory;
            for (int i = 0; i < inventory.getSizeInventory(); ++i)
            {
                ItemStack stack2 = inventory.getStackInSlot(i);
                if (stack2.getItem() == CulinaeRegistry.BASIC_FOOD && stack2.getMetadata() == Variant.EMPOWERED_CITRON.getMeta())
                {
                    ItemBasicFood.citronSays(player, "torch");
                    event.setCanceled(true);
                    return;
                }
            }
        }
        else if (stack.getItem() == CulinaeRegistry.BASIC_FOOD && stack.getMetadata() == Variant.EMPOWERED_CITRON.getMeta())
        {
            ItemBasicFood.citronSays(player, "pickup" + player.world.rand.nextInt(4));
        }
    }
}
