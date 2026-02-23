package quantumstudios.culinae.fluids;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.CulinaeRegistry;

public class BlockFluidSoyMilk extends BlockFluid
{
    public BlockFluidSoyMilk(final Fluid fluid)
    {
        super(fluid, "soy_milk", new MaterialLiquid(MapColor.SNOW));
        setTickRandomly(true);
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighbourPos)
    {
        super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
        checkMixing(state, world, pos, neighbourPos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(world, pos, state);
        for (EnumFacing side : EnumFacing.VALUES)
        {
            BlockPos neighbourPos = pos.offset(side);
            checkMixing(state, world, pos, neighbourPos);
        }
    }

    public void checkMixing(@Nonnull IBlockState state, World world, BlockPos pos, @Nonnull BlockPos neighbourPos)
    {
        Block block = world.getBlockState(neighbourPos).getBlock();
        if (isSourceBlock(world, pos) && (block == CulinaeFluidBlocks.FRUIT_VINEGAR || block == CulinaeFluidBlocks.RICE_VINEGAR))
        {
            world.setBlockState(pos, CulinaeRegistry.TOFU_BLOCK.getDefaultState());
        }
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote)
        {
            if (entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase) entityIn).curePotionEffects(new ItemStack(Items.MILK_BUCKET));
            }
            else if (state.getValue(LEVEL) == 0 && entityIn instanceof EntityItem)
            {
                EntityItem entityItem = (EntityItem) entityIn;
                if (entityItem.isDead || entityItem.cannotPickup())
                {
                    return;
                }
                ItemStack stack = entityItem.getItem();
                if (stack.getItem() == CulinaeRegistry.MATERIAL && stack.getMetadata() == culinae.Materials.CRUDE_SALT.getMeta())
                {
                    stack.shrink(1);
                    worldIn.setBlockState(pos, CulinaeRegistry.TOFU_BLOCK.getDefaultState());
                }
            }
        }
    }
}
