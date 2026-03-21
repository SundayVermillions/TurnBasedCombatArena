package tbca.engine.turnorder;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;

import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(GameStateReadOnly state);
}
