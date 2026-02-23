package quantumstudios.culinae.items;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.util.CulinaeFakePlayer;

public class BehaviorArmDispense extends BehaviorDefaultDispenseItem
{

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
    {
        if (stack.getMetadata() != culinae.Materials.WOODEN_ARM.getMeta())
        {
            return super.dispenseStack(source, stack);
        }
        EnumFacing facing = source.getBlockState().getValue(BlockDirectional.FACING);
        BlockPos destination = source.getBlockPos().offset(facing);

        World currentWorld = source.getWorld();

        if (!(currentWorld instanceof WorldServer))
        {
            return stack;
        }

        FakePlayer player = CulinaeFakePlayer.getInstance((WorldServer) source.getWorld());
        player.setPosition(source.getX(), source.getY(), source.getZ());
        IBlockState target = currentWorld.getBlockState(destination);
        if (currentWorld.getTileEntity(destination) instanceof ILootContainer)
        {
            return stack;
        }
        PlayerInteractEvent.RightClickBlock evt = new PlayerInteractEvent.RightClickBlock(player, EnumHand.MAIN_HAND, destination, facing.getOpposite(), new Vec3d(destination.getX() + 0.5F, destination.getY() + 0.5F, destination.getZ() + 0.5F));
        if (!MinecraftForge.EVENT_BUS.post(evt))
            target.getBlock().onBlockActivated(source.getWorld(), destination, target, player, EnumHand.MAIN_HAND, facing, 0.5F, 0.5F, 0F);

        player.closeContainer();
        //player.closeScreen();
        return stack;
    }
}
