package github.tlean07.ruby_mod.datagen;

import github.tlean07.TLean07;
import github.tlean07.TLean07DataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import github.tlean07.ruby_mod.ModBlocks;
import github.tlean07.ruby_mod.ModItems;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModLootTablesProvider extends FabricTagProvider.BlockTagProvider {

    public ModLootTablesProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.RUBY_BLOCK);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.RUBY_BLOCK);

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(TLean07.MOD_ID)))
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.RUBY_BLOCK);
    }
}

