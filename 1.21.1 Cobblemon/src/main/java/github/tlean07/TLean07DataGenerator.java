package github.tlean07;

import github.tlean07.ruby_mod.datagen.LeanProviderLoot;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import github.tlean07.ruby_mod.datagen.ModRecipesProvider;
import github.tlean07.ruby_mod.datagen.ModLootTablesProvider;

public class TLean07DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ModRecipesProvider::new);
        pack.addProvider(ModLootTablesProvider::new);
        pack.addProvider(LeanProviderLoot::new);
    }
}
