package poc.infrastructure;

import poc.domain.game.Game;
import poc.domain.game.GameRepository;
import poc.utils.Assert;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultGameRepository implements GameRepository {

    private final ConcurrentMap<UUID, Game> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        Assert.requireNotNull(game, "Game cannot not be null");
        storage.put(game.id(), game);
    }

    @Override
    public Game findOne(UUID id) {
        Assert.requireNotNull(id, "Game id cannot be null");
        return storage.get(id);
    }

    @Override
    public void delete(UUID id) {
        Assert.requireNotNull(id, "Game id cannot be null");
        storage.remove(id);
    }
}
