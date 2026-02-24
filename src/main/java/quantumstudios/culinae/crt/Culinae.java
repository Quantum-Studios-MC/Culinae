package quantumstudios.culinae.crt;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import quantumstudios.culinae.api.process.Chopping;
import quantumstudios.culinae.api.process.Milling;
import quantumstudios.culinae.api.process.Grinding;
import quantumstudios.culinae.api.process.Processing;
import snownee.kiwi.crafting.input.RegularItemStackInput;

import java.util.List;

@ZenRegister
@ZenClass("mods.culinae.Culinae")
public class Culinae {

    @ZenMethod
    public static void addChoppingRecipe(IIngredient input, IItemStack output) {
        List<IItemStack> items = input.getItems();
        if (items.isEmpty()) return;
        net.minecraft.item.ItemStack mcInput = CraftTweakerMC.getItemStack(items.get(0));
        net.minecraft.item.ItemStack mcOutput = CraftTweakerMC.getItemStack(output);
        Chopping recipe = new Chopping("crt_" + mcInput.getItem().getRegistryName() + "_" + mcOutput.getItem().getRegistryName(), new RegularItemStackInput(mcInput), mcOutput);
        Processing.CHOPPING.add(recipe);
    }

    @ZenMethod
    public static void addMillingRecipe(IIngredient input, IItemStack output) {
        List<IItemStack> items = input.getItems();
        if (items.isEmpty()) return;
        net.minecraft.item.ItemStack mcInput = CraftTweakerMC.getItemStack(items.get(0));
        net.minecraft.item.ItemStack mcOutput = CraftTweakerMC.getItemStack(output);
        Milling recipe = new Milling("crt_" + mcInput.getItem().getRegistryName() + "_" + mcOutput.getItem().getRegistryName(), mcInput, mcOutput);
        Processing.MILLING.add(recipe);
    }

    @ZenMethod
    public static void addGrindingRecipe(IIngredient input, IItemStack output) {
        List<IItemStack> items = input.getItems();
        if (items.isEmpty()) return;
        net.minecraft.item.ItemStack mcInput = CraftTweakerMC.getItemStack(items.get(0));
        net.minecraft.item.ItemStack mcOutput = CraftTweakerMC.getItemStack(output);
        Grinding recipe = new Grinding("crt_" + mcInput.getItem().getRegistryName() + "_" + mcOutput.getItem().getRegistryName(), List.of(new RegularItemStackInput(mcInput)), mcOutput, 1);
        Processing.GRINDING.add(recipe);
    }

}
