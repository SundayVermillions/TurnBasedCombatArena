package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.item.ItemType;

public record ActionParameters(ActionType actionType,
                               Combatant actor,
                               int targetEnemyIndex,
                               ItemType itemType) {
}

