package github.tlean07.ruby_mod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.item.Items;
import github.tlean07.ruby_mod.ModBlocks;
import github.tlean07.ruby_mod.ModItems;

import java.util.concurrent.CompletableFuture;

import static net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider.hasItem;
import static net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider.conditionsFromItem;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerReversibleCompactingRecipes(
                exporter,
                RecipeCategory.MISC,
                ModItems.RUBY,
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.RUBY_BLOCK
        );

        // espada de ruby
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RUBY_SWORD)
                .pattern(" P ")
                .pattern(" P ")
                .pattern(" S ")
                .input('P', ModItems.RUBY)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .offerTo(exporter);

        // picareta de ruby
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RUBY_PICKAXE)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.RUBY)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .offerTo(exporter);
    }
}
