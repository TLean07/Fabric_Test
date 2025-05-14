package github.tlean07.ruby_mod;

import github.tlean07.ruby_mod.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class LeanTools implements ToolMaterial {
    public static final LeanTools INSTANCE = new LeanTools();

    @Override
    public int getDurability() {
        return 2200;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 9.0F;
    }

    @Override
    public float getAttackDamage() {
        return 12.0F;
    }

    @Override
    public int getEnchantability() {
        return 22;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.RUBY);
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("tlean07", "ruby"));
    }
}

