package github.tlean07.economy;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;

import java.util.Comparator;
import java.util.stream.Collectors;

public class BalanceState extends PersistentState {

    private final Map<UUID, Integer> balances = new HashMap<>();

    public static final Type<BalanceState> TYPE = new Type<>(
            BalanceState::new,
            BalanceState::fromNbt,
            DataFixTypes.LEVEL
    );

    public static BalanceState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        BalanceState state = new BalanceState();
        NbtCompound data = nbt.getCompound("balances");
        for (String key : data.getKeys()) {
            state.balances.put(UUID.fromString(key), data.getInt(key));
        }
        return state;
    }

    // Retorna o money do server
    public static BalanceState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE, "balance_state");
    }

    // Retorna o money de um player
    public int getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0);
    }

    // Define o saldo de um player
    public void setBalance(UUID uuid, int amount) {
        balances.put(uuid, amount);
        markDirty(); // Marca como alterado para salvar depois
    }

    // Adiciona valor ao saldo do player
    public void addBalance(UUID uuid, int amount) {
        setBalance(uuid, getBalance(uuid) + amount);
    }

    // Remove valor do saldo do player
    public boolean removeBalance(UUID uuid, int amount) {
        int current = getBalance(uuid);
        if (current >= amount) {
            setBalance(uuid, current - amount);
            return true;
        }
        return false;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound balancesTag = new NbtCompound();
        for (Map.Entry<UUID, Integer> entry : balances.entrySet()) {
            balancesTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        nbt.put("balances", balancesTag);
        return nbt;
    }

    // Retorna os top N jogadores com maior saldo
    public List<ServerPlayerEntity> getTopPlayers(int limit, MinecraftServer server) {
        return server.getPlayerManager().getPlayerList().stream()
                .filter(player -> balances.containsKey(player.getUuid()))
                .sorted(Comparator.comparingInt((ServerPlayerEntity p) -> balances.get(p.getUuid())).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Retorna os jogadores da posição 'start' até 'end'
    public List<ServerPlayerEntity> getTopPlayers(int start, int end, MinecraftServer server) {
        return server.getPlayerManager().getPlayerList().stream()
                .filter(player -> balances.containsKey(player.getUuid()))
                .sorted(Comparator.comparingInt((ServerPlayerEntity p) -> balances.get(p.getUuid())).reversed())
                .skip(start - 1L)
                .limit(end - start + 1L)
                .collect(Collectors.toList());
    }

    // Novo método para retornar os jogadores do "bal top" específico baseado no número, exemplo: bal top 1, bal top 2, etc.
    public List<ServerPlayerEntity> getBalTopForPosition(int position, int limit, MinecraftServer server) {
        int start = (position - 1) * limit + 1; // Calcula o início para o "bal top"
        int end = position * limit; // Calcula o final para o "bal top"

        return getTopPlayers(start, end, server);
    }
}
