package quantumstudios.culinae.entities;

import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import quantumstudios.culinae.CulinaeRegistry;

public class EntityModBoat extends EntityBoat
{
    public EntityModBoat(World worldIn)
    {
        super(worldIn);
    }

    public EntityModBoat(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    public Item getItemBoat()
    {
        return CulinaeRegistry.BOAT;
    }
}
