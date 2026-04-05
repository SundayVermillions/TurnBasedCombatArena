package tbca.effect;

import tbca.combatant.Combatant;

public class AttackBuffEffect extends StatusEffect {

    private final double multiplier;

    public AttackBuffEffect(double multiplier, int turns) {
        super("Wolf Fury Buff", turns);
        this.multiplier = multiplier;
    }

    @Override
    public void applyEffect(Combatant target) {
        int newAttack = (int) (target.getAttack() * multiplier);
        target.setAttack(newAttack);
    }

    @Override
    public void removeEffect(Combatant target) {
        target.resetAttack();
    }
}