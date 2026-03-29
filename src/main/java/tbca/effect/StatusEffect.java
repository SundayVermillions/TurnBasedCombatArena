package tbca.effect;

import tbca.combatant.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int remainingTurns;

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.remainingTurns = duration;
    }

    public void decrementTurn() {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }

    public boolean isExpired() {
        return remainingTurns <= 0;
    }

    public String getName() {
        return name;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public abstract void applyEffect(Combatant target);
    public abstract void removeEffect(Combatant target);
}