package tbca.engine.logic.turnorder;

import java.util.List;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;

public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(GameStateReadOnly state);
}
