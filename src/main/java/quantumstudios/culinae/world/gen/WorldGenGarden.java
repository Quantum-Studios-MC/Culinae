package quantumstudios.culinae.world.gen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quantumstudios.culinae.CulinaeConfig;
import quantumstudios.culinae.CulinaeRegistry;
import quantumstudios.culinae.blocks.BlockCulinaeCrops;

public class WorldGenGarden
{
    public static final List<Block> PLANT_POOL = Lists.newArrayList(CulinaeRegistry.CHINESE_CABBAGE, CulinaeRegistry.CORN, CulinaeRegistry.CUCUMBER, CulinaeRegistry.EGGPLANT, CulinaeRegistry.GINGER, CulinaeRegistry.GREEN_PEPPER, CulinaeRegistry.LEEK, CulinaeRegistry.LETTUCE, CulinaeRegistry.ONION, CulinaeRegistry.PEANUT, CulinaeRegistry.RED_PEPPER, CulinaeRegistry.SCALLION, CulinaeRegistry.SESAME, CulinaeRegistry.SICHUAN_PEPPER, CulinaeRegistry.SOYBEAN, CulinaeRegistry.SPINACH, CulinaeRegistry.TOMATO, CulinaeRegistry.TURNIP, Blocks.CARROTS, Blocks.POTATOES, Blocks.WHEAT);
    public static final boolean disablePepper;
    public static final boolean disableRice;

    static
    {
        Set<String> ids = Sets.newHashSet(CulinaeConfig.WORLD_GEN.disableCropGenIdList);
        disablePepper = ids.contains("culinae:chili_pepper");
        disableRice = ids.contains("culinae:rice");
        PLANT_POOL.removeIf($ -> ids.contains($.getRegistryName().toString()));
    }

    @SubscribeEvent
    public void decorateEvent(Decorate event)
    {
        World worldIn = event.getWorld();
        if (event.getType() == Decorate.EventType.PUMPKIN && Arrays.binarySearch(CulinaeConfig.WORLD_GEN.cropsGenDimensions, worldIn.provider.getDimension()) >= 0)
        {
            Random rand = event.getRand();
            BlockPos position = event.getChunkPos().getBlock(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8);

            Biome biome = worldIn.getBiome(position);

            int flowers = biome.decorator.flowersPerChunk;
            if (flowers == -999 && biome.getClass().getName().startsWith("biomesoplenty"))
            {
                flowers = 1;
            }
            if (!biome.canRain() || biome.isSnowyBiome() || biome.decorator.flowersPerChunk < 1 || biome instanceof BiomeOcean || rand.nextDouble() > biome.getDefaultTemperature() || rand.nextInt(200) >= CulinaeConfig.WORLD_GEN.cropsGenRate)
            {
                return;
            }

            BlockPos.MutableBlockPos pos = WorldGenHelper.findGround(worldIn, position, true, true, true);
            if (pos == null)
            {
                return;
            }
            pos.move(EnumFacing.DOWN);

            IBlockState state = worldIn.getBlockState(pos);
            if (biome.topBlock.getMaterial() == Material.GRASS && state.getBlock() == biome.topBlock.getBlock())
            {
                Block plant = PLANT_POOL.get(rand.nextInt(PLANT_POOL.size()));
                plant(worldIn, pos, plant, biome.topBlock.getBlock(), rand);
                plant(worldIn, pos.offset(EnumFacing.byHorizontalIndex(rand.nextInt(4))), plant, biome.topBlock.getBlock(), rand);
                plant(worldIn, pos.offset(EnumFacing.byHorizontalIndex(rand.nextInt(4))), plant, biome.topBlock.getBlock(), rand);
            }
            else if (!disableRice && state.getBlock() == Blocks.WATER)
            {
                state = worldIn.getBlockState(pos.down());
                if (state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS)
                {
                    pos.move(EnumFacing.UP);
                    worldIn.setBlockState(pos, CulinaeRegistry.RICE.withAge(rand.nextInt(CulinaeRegistry.RICE.getMaxAge())), 0);
                }
            }
        }
        else if (!disablePepper && worldIn.provider.getDimension() == -1)
        {
            Random rand = event.getRand();
            if (rand.nextInt(4) > 0 || rand.nextInt(100) < CulinaeConfig.WORLD_GEN.cropsGenRate)
            {
                return;
            }
            BlockPos position = event.getChunkPos().getBlock(rand.nextInt(16) + 8, rand.nextInt(50) + 33, rand.nextInt(16) + 8);
            BlockPos.MutableBlockPos pos = WorldGenHelper.findGround(worldIn, position, true, true, false, 20);
            if (pos == null)
            {
                return;
            }
            pos.move(EnumFacing.DOWN);
            if (worldIn.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
            {
                plant(worldIn, pos, CulinaeRegistry.CHILI, Blocks.SOUL_SAND, rand);
                //plant(worldIn, pos.offset(EnumFacing.byHorizontalIndex(rand.nextInt(4))), CulinaeRegistry.CHILI, Blocks.SOUL_SAND, rand);
            }
        }
    }

    private static void plant(World world, BlockPos pos, Block block, Block replacedBlock, Random rand)
    {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == replacedBlock)
        {
            if (block instanceof BlockCulinaeCrops)
            {
                BlockCulinaeCrops BlockCulinaeCrops = (BlockCulinaeCrops) block;
                if (BlockCulinaeCrops.getPlantType(world, pos) == EnumPlantType.Crop)
                {
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState(), 0);
                }
                pos = pos.up();
                if (world.getBlockState(pos).getBlock().isReplaceable(world, pos))
                {
                    world.setBlockState(pos, BlockCulinaeCrops.withAge(block == CulinaeRegistry.CORN ? 0 : rand.nextInt(BlockCulinaeCrops.getMaxAge())), 0);
                }
            }
            else if (block instanceof BlockCrops)
            {
                world.setBlockState(pos, Blocks.FARMLAND.getDefaultState(), 0);
                BlockCrops blockCrops = (BlockCrops) block;
                pos = pos.up();
                if (world.getBlockState(pos).getBlock().isReplaceable(world, pos))
                {
                    world.setBlockState(pos, blockCrops.withAge(rand.nextInt(blockCrops.getMaxAge())), 0);
                }
            }
        }
    }

}
