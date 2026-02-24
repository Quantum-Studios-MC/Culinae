import mods.culinae.AxeChopping

// Test adding a recipe
mods.culinae.AxeChopping.recipeBuilder()
    .input(item('minecraft:log'))
    .output(item('minecraft:planks', 6))
    .register()

// Test removal
mods.culinae.AxeChopping.removeAll()
