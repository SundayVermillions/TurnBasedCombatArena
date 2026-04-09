package tbca.effect;

import tbca.combatant.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int remainingTurns;
    protected boolean isPermanent;

    //temporary effects like stun, smoke bomb and defend
    public StatusEffect(String name, int duration) {
        this.name = name;
        this.remainingTurns = duration;
        this.isPermanent = false;
    }

    //permanent effects like Arcane Blast
    public StatusEffect(String name, boolean isPermanent){
        this.name = name;
        this.remainingTurns = 0;
        this.isPermanent = isPermanent;
    }

    public void decrementTurn() {
        if (!isPermanent && remainingTurns > 0) {
            remainingTurns--;
        }
    }

    public boolean isExpired() {

        return !isPermanent && remainingTurns <= 0;
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