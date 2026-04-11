package tbca.effect;
import java.util.List;

import tbca.combatant.Combatant;
import tbca.engine.GameState;

public abstract class FieldEffect {

    private final String name;
    protected int turnsRemaining;

    public FieldEffect(String name, int turnsRemaining) {
        this.name = name;
        this.turnsRemaining = turnsRemaining;
    }

    public abstract boolean appliesTo(Combatant combatant);

    public abstract void applyEffect(GameState gameState);

    public abstract void removeEffect(List<Combatant> allCombatants);

    // takes in gameState for future extensibility, although not used in current implementation
    // eg. for future implemented fieldEffects that apply their effects every turn,
    //      like a sandstorm that damages enemies every round
    public void tick(GameState gameState) {
        turnsRemaining--;
    }

    public boolean isExpired() {
        return turnsRemaining <= 0;
    }

    public String getName() {
        return name;
    }
    public int getTurnsRemaining() {
        return turnsRemaining;
    }
}