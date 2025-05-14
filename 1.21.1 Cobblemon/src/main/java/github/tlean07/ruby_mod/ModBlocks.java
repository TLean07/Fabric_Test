package github.tlean07.ruby_mod;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static github.tlean07.TLean07.MOD_ID;

public class ModBlocks {
    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK))
    );

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK))
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of("tlean07", name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of("tlean07", name),
                new BlockItem(block, new Item.Settings())
        );
    }

    public static void registerModBlocks() {
        System.out.println("Mod " + MOD_ID + " inicializado");
        System.out.println("Registrando blocos de tlean07...");
    }
}
