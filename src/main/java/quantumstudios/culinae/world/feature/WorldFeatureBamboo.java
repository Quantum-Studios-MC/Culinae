package quantumstudios.culinae.world.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.blocks.BlockBambooPlant;
import quantumstudios.culinae.blocks.BlockBambooPlant.Type;

public class WorldFeatureBamboo extends WorldGenAbstractTree
{
    private final boolean notify;

    public WorldFeatureBamboo(boolean notify)
    {
        super(notify);
        this.notify = notify;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
        BlockPos down = pos.down();
        IBlockState stateSoil = worldIn.getBlockState(down);
        boolean isSoil = stateSoil.getBlock().canSustainPlant(stateSoil, worldIn, down, EnumFacing.UP, CulinaeRegistry.BAMBOO_PLANT);
        if (!isSoil)
        {
            return false;
        }

        int height = 7 + rand.nextInt(4);
        for (int i = height; i-- > 1;)
        {
            BlockPos pos2 = pos.up(i);
            if (worldIn.isOutsideBuildHeight(pos2) || !worldIn.isAirBlock(pos2))
            {
                return false;
            }
            for (EnumFacing facing : EnumFacing.HORIZONTALS)
            {
                if (!worldIn.isAirBlock(pos2.offset(facing)))
                {
                    return false;
                }
            }
        }
        IBlockState newState = CulinaeRegistry.BAMBOO_PLANT.getDefaultState().withProperty(BlockBambooPlant.TYPE, notify ? Type.A_2 : Type.A_6);
        if (!CulinaeConfig.WORLD_GEN.legacyBambooGen)
        {
            for (int i = 0; i < 4; i++)
            {
                setBlockAndNotifyAdequately(worldIn, pos.up(height - 1 - i % 2).offset(EnumFacing.byHorizontalIndex(i)), newState.withProperty(BlockBambooPlant.TYPE, Type.values()[7 + i]));
                setBlockAndNotifyAdequately(worldIn, pos.up(height - 3 - i % 2).offset(EnumFacing.byHorizontalIndex(i)), newState.withProperty(BlockBambooPlant.TYPE, Type.values()[7 + i]));
            }
        }
        for (int i = height; i-- > 0;)
        {
            setBlockAndNotifyAdequately(worldIn, pos.up(i), newState);
        }
        return true;
    }
}
