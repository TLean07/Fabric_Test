package github.tlean07.economy;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.List;

public class EconomyCommands {

    // Registrar todos os comandos da economia
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        System.out.println("📜 Registrando comandos da economia...");

        // Comando: /eco add <quantia>
        dispatcher.register(CommandManager.literal("eco")
                .requires(source -> source.hasPermissionLevel(2)) // Somente operadores
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                    BalanceState state = BalanceState.get(player.getServerWorld());

                                    state.addBalance(player.getUuid(), amount);

                                    context.getSource().sendFeedback(() ->
                                            Text.literal("💸 Você recebeu ").formatted(Formatting.GREEN)
                                                    .append(Text.literal("$" + amount).formatted(Formatting.GOLD))
                                                    .append(Text.literal(" no seu saldo.").formatted(Formatting.GREEN)), false);
                                    return 1;
                                }))));

// Comando: /pay <jogador> <quantia>
        dispatcher.register(CommandManager.literal("pay")
                .then(CommandManager.argument("player", StringArgumentType.string())
                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                .executes(context -> {
                                    ServerPlayerEntity sender = context.getSource().getPlayer();
                                    String targetName = StringArgumentType.getString(context, "player");
                                    int amount = IntegerArgumentType.getInteger(context, "amount");

                                    ServerPlayerEntity recipient = sender.getServer().getPlayerManager().getPlayer(targetName);
                                    BalanceState state = BalanceState.get(sender.getServerWorld());

                                    // Remover a verificação que impede pagamento para si mesmo
                                    if (recipient == null) {
                                        context.getSource().sendFeedback(() ->
                                                Text.literal("❌ Jogador inválido.").formatted(Formatting.RED), false);
                                        return 0;
                                    }

                                    if (state.getBalance(sender.getUuid()) < amount) {
                                        context.getSource().sendFeedback(() ->
                                                Text.literal("⚠ Saldo insuficiente!").formatted(Formatting.RED), false);
                                        return 0;
                                    }

                                    state.addBalance(sender.getUuid(), -amount);
                                    state.addBalance(recipient.getUuid(), amount);

                                    sender.sendMessage(Text.literal("✅ Você pagou ")
                                            .append(Text.literal("$" + amount).formatted(Formatting.GOLD))
                                            .append(" para ")
                                            .append(Text.literal(recipient.getName().getString()).formatted(Formatting.AQUA)), false);

                                    recipient.sendMessage(Text.literal("💰 Você recebeu ")
                                            .append(Text.literal("$" + amount).formatted(Formatting.GOLD))
                                            .append(" de ")
                                            .append(Text.literal(sender.getName().getString()).formatted(Formatting.AQUA)), false);

                                    return 1;
                                }))));

        // Comando: /bal
        dispatcher.register(CommandManager.literal("bal")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    BalanceState state = BalanceState.get(player.getServerWorld());
                    int balance = state.getBalance(player.getUuid());

                    context.getSource().sendFeedback(() ->
                            Text.literal("💼 Seu saldo atual: ")
                                    .append(Text.literal("$" + balance).formatted(Formatting.GOLD)), false);
                    return 1;
                }));

        // Comando: /baltop (Top 10)
        dispatcher.register(CommandManager.literal("baltop")
                .executes(context -> {
                    BalanceState state = BalanceState.get(context.getSource().getWorld());
                    List<ServerPlayerEntity> topPlayers = state.getTopPlayers(10, context.getSource().getServer());

                    StringBuilder topList = new StringBuilder("🏆 Top 10 jogadores mais ricos:\n");
                    for (int i = 0; i < topPlayers.size(); i++) {
                        ServerPlayerEntity player = topPlayers.get(i);
                        int balance = state.getBalance(player.getUuid());
                        topList.append("§6").append(i + 1).append(". §b")
                                .append(player.getName().getString())
                                .append(" §7- §a$").append(balance).append("\n");
                    }

                    context.getSource().sendFeedback(() -> Text.literal(topList.toString()), false);
                    return 1;
                }));

        // Comando: /baltop2 (11º ao 20º)
        dispatcher.register(CommandManager.literal("baltop2")
                .executes(context -> {
                    BalanceState state = BalanceState.get(context.getSource().getWorld());
                    List<ServerPlayerEntity> topPlayers = state.getTopPlayers(11, 20, context.getSource().getServer());

                    StringBuilder topList = new StringBuilder("🔻 Posições 11º ao 20º:\n");
                    for (int i = 0; i < topPlayers.size(); i++) {
                        ServerPlayerEntity player = topPlayers.get(i);
                        int balance = state.getBalance(player.getUuid());
                        topList.append("§e").append(i + 11).append(". §b")
                                .append(player.getName().getString())
                                .append(" §7- §a$").append(balance).append("\n");
                    }

                    context.getSource().sendFeedback(() -> Text.literal(topList.toString()), false);
                    return 1;
                }));
    }
}
