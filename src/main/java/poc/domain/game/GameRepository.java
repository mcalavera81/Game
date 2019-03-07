package poc.domain.game;

import java.util.UUID;

public interface GameRepository {

    void save(Game game);

    Game findOne(UUID id);

    void delete(UUID id);
}

