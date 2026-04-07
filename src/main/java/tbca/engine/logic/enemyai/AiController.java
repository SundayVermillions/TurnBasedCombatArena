package tbca.engine.logic.enemyai;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;

import java.util.Map;

public interface AiController {
    Map<AiType, AiController> REGISTRY = Map.of(
            AiType.SIMPLE, new SimpleAi(),
            AiType.ADVANCED, new AdvancedAi()
    );

    static AiController get(AiType type) {
        return REGISTRY.get(type);
    }

    ActionParameters decide(Combatant npc, GameStateReadOnly gameState);
}