package quantumstudios.culinae.plugins;

import quantumstudios.culinae.Culinae;
import quantumstudios.culinae.api.CulinaryHub;
import quantumstudios.culinae.api.Form;
import quantumstudios.culinae.api.Material;
import quantumstudios.culinae.api.MaterialCategory;
import quantumstudios.culinae.api.prefab.SimpleMaterialImpl;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;

@KiwiModule(modid = culinae.MODID, name = "pizzacraft", dependency = "pizzacraft", optional = true)
public class PizzaCraftCompat implements IModule
{
    @Override
    public void init()
    {
        Material pineapple = CulinaryHub.API_INSTANCE.register(new SimpleMaterialImpl("pineapple", 0xa9861c, 0, 0, 0, 0, 0, MaterialCategory.FRUIT).setValidForms(Form.JUICE_ONLY));
        CulinaryHub.API_INSTANCE.registerMapping("cropPineapple", pineapple);
        Material broccoli = CulinaryHub.API_INSTANCE.register(new SimpleMaterialImpl("broccoli", 0x71a141, 0, 0, 0, 0, 0, MaterialCategory.VEGETABLES).setValidForms(Form.ALL_FORMS_INCLUDING_JUICE));
        CulinaryHub.API_INSTANCE.registerMapping("cropBroccoli", broccoli);
    }
}
