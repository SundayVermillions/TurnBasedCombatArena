package tbca.effect;

import tbca.combatant.Combatant;

public class AttackBuffEffect extends StatusEffect {

    private final double multiplier;

    public AttackBuffEffect(double multiplier, int turns) {
        super("Wolf Fury Buff", "User Attack is doubled for the current turn and the next turn.", turns);
        this.multiplier = multiplier;
    }

    @Override
    public void applyEffect(Combatant target) {
        int newAttack = (int) (target.getAttack() * multiplier);
        target.setAttack(newAttack);
    }

    public boolean isBuff(){
        return true;
    }

    @Override
    public void removeEffect(Combatant target) {
        target.resetAttack();
    }
}