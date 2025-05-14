package github.tlean07.ruby_mod;

import github.tlean07.ruby_mod.LeanTools;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new Settings()));
    public static final Item RUBY_SWORD = registerItem(
            "ruby_sword",
            new SwordItem(LeanTools.INSTANCE, new Settings())
    );
    public static final Item RUBY_PICKAXE = registerItem(
            "ruby_pickaxe",
            new PickaxeItem(LeanTools.INSTANCE, new Settings())
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of("tlean07", name), item);
    }

    public static void registerModItems() {
        System.out.println("Registrando itens");
    }
}
