package quantumstudios.culinae.network;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.client.gui.CulinaeGUI;
import quantumstudios.culinae.client.gui.GuiManual;
import quantumstudios.culinae.client.gui.GuiNameFood;
import quantumstudios.culinae.inventory.ContainerNameFood;

public class CulinaeGuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case CulinaeGUI.NAME_FOOD:
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            if (tile instanceof CookingVessel)
            {
                return new ContainerNameFood((CookingVessel) tile, world);
            }
            return null;
        default:
            return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case CulinaeGUI.MANUAL:
            return new GuiManual();
        case CulinaeGUI.NAME_FOOD:
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            if (tile instanceof CookingVessel)
            {
                return new GuiNameFood((CookingVessel) tile, world);
            }
            return null;
        default:
            return null;
        }
    }
}
