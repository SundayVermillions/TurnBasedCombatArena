package tbca.domain.effect.statuseffect;

import tbca.domain.combatant.Combatant;

public abstract class StatusEffect {
    protected String name;
    public String description;
    protected int remainingTurns;
    protected boolean isPermanent;
    protected boolean isBuff;

    //temporary effects like stun, smoke bomb and defend
    public StatusEffect(String name, String description, int duration) {
        this.name = name;
        this.remainingTurns = duration;
        this.isPermanent = false;
        this.description = description;
        this.isBuff = false;
    }

    //permanent effects like Arcane Blast
    public StatusEffect(String name, String description,  boolean isPermanent){
        this.name = name;
        this.remainingTurns = 0;
        this.isPermanent = isPermanent;
        this.description = description;
        this.isBuff = false;
    }

    public String getDescription(){
        return description;
    }

    public void decrementTurn() {
        if (!isPermanent && remainingTurns > 0) {
            remainingTurns--;
        }
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
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
    public abstract boolean isBuff();
    public boolean isStackable(){ return false;}
}