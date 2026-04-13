package tbca.engine.logic.turnorder;

import java.util.List;

import tbca.domain.combatant.Combatant;
import tbca.domain.gamestate.GameStateReadOnly;

public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(GameStateReadOnly state);
}
