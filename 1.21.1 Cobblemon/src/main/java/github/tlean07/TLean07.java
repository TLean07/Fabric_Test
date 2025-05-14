package github.tlean07;

import github.tlean07.ruby_mod.ModBlocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import github.tlean07.economy.EconomyCommands;

public class TLean07 implements ModInitializer {
	public static final String MOD_ID = "tlean07";
	@Override
	public void onInitialize() {
		System.out.println("Inicializando mod...");
		ModBlocks.registerModBlocks();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			System.out.println("Registrando comandos...");
			EconomyCommands.register(dispatcher);
		});
	}
}
