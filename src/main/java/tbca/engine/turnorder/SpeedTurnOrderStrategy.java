package tbca.engine.turnorder;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedTurnOrderStrategy implements TurnOrderStrategy {
    @Override
    public List<Combatant> determineTurnOrder(GameStateReadOnly state) {
        List<Combatant> liveCombatants = new ArrayList<>();

        // add players
        if (state.isPlayerAlive())
            liveCombatants.add(state.getPlayer());

        // add live enemies only
        for (Combatant enemy : state.getCurrEnemies()) {
            if (enemy.isAlive())
                liveCombatants.add(enemy);
        }

        // sort in descending speed stat
        liveCombatants.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());

        return liveCombatants;
    }
}
