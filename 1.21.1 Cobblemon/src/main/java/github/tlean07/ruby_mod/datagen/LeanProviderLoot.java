package github.tlean07.ruby_mod.datagen;

import github.tlean07.ruby_mod.ModBlocks;
import github.tlean07.ruby_mod.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LeanProviderLoot extends FabricBlockLootTableProvider {

    public LeanProviderLoot(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RUBY_BLOCK);

        addDrop(ModBlocks.RUBY_ORE,
                oreDrops(ModBlocks.RUBY_ORE, ModItems.RUBY)
        );
    }
}
