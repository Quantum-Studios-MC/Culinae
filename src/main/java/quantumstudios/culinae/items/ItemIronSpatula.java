package quantumstudios.culinae.items;

import java.util.Collections;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CookingStrategy;
import quantumstudios.culinae.api.CookingStrategyProvider;
import quantumstudios.culinae.api.CookingVessel;
import quantumstudios.culinae.api.Ingredient;
import quantumstudios.culinae.api.IngredientTrait;
import quantumstudios.culinae.api.Seasoning;
import quantumstudios.culinae.internal.food.Dish;
import snownee.kiwi.item.ItemMod;

public class ItemIronSpatula extends ItemMod implements CookingStrategyProvider
{

    // Adapt CookingStrategyProvider to Capability system is somehow tricky, thus
    // it's not something we should consider right now

    static final class StirFrying implements CookingStrategy<Dish.Builder>
    {

        int oilAmount = 0;
        int currentSize = 0;

        @Override
        public void beginCook(Dish.Builder dish)
        {
            this.currentSize = dish.getIngredients().size();
        }

        @Override
        public void preCook(Seasoning seasoning, CookingVessel vessel)
        {
            if (seasoning.hasKeyword("oil"))
            {
                this.oilAmount += seasoning.getSize();
            }
            // TODO (3TUSK): Add other characteristics according to seasoning list
        }

        @Override
        public void cook(Ingredient ingredient, CookingVessel vessel)
        {
            if (oilAmount > 0)
            {
                // Smells good, man!
                ingredient.addTrait(IngredientTrait.AROMATIC);

                // Oily is actually bad, man!
                if (this.currentSize > 0 && this.oilAmount / (float) this.currentSize > 0.25)
                {
                    ingredient.addTrait(IngredientTrait.OILY);
                }
                else
                {
                    ingredient.removeTrait(IngredientTrait.OILY);
                }
            }
        }

        @Override
        public void postCook(Dish.Builder dish, CookingVessel vessel)
        {
            Collections.shuffle(dish.getIngredients(), Item.itemRand);
        }

        @Override
        public void endCook()
        {

        }

    }

    public ItemIronSpatula(String name)
    {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(culinae.CREATIVE_TAB);
    }

    @Nonnull
    @Override
    public CookingStrategy getCookingStrategy(ItemStack stack)
    {
        return new StirFrying();
    }
}
