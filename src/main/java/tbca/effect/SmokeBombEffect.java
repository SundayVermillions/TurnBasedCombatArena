package tbca.effect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tbca.combatant.Combatant;
import tbca.engine.GameState;

public class SmokeBombEffect extends FieldEffect {

    private final boolean creatorIsPlayer;
    private final Map<Combatant, Integer> snapshotAttacks = new HashMap<>();

    public SmokeBombEffect(boolean creatorIsPlayer) {
        super("Smoke Bomb Cover", 2);
        this.creatorIsPlayer = creatorIsPlayer;
    }

    @Override
    public boolean appliesTo(Combatant combatant) {
        return isOpposingSide(combatant);
    }

    @Override
    public void applyEffect(GameState gameState) {
        List<Combatant> allCombatants = gameState.getAllCombatants();
        for (Combatant c : allCombatants) {
            if (isOpposingSide(c)) {
                snapshotAttacks.put(c, c.getAttack());
                c.setAttack(0);
            }
        }
    }

    @Override
    public void removeEffect(List<Combatant> allCombatants) {
        for (Combatant c : allCombatants) {
            if (snapshotAttacks.containsKey(c)) {
                int originalValue = snapshotAttacks.get(c);
                c.setAttack(originalValue);
            }
        }
        snapshotAttacks.clear();
    }

    private boolean isOpposingSide(Combatant c) {
        return c.isPlayer() != creatorIsPlayer;
    }
}