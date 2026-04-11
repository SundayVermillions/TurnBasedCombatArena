package tbca.effect;

import tbca.combatant.Combatant;
import tbca.combatant.statsmodifier.StatModifier;

public class AttackBuffEffect extends StatusEffect {

    private static final String MODIFIER_ID = "attack-buff-effect";
    private final double multiplier;

    public AttackBuffEffect(double multiplier, int turns) {
        super("Wolf Fury Buff", "User Attack is doubled for the current turn and the next turn.", turns);
        this.multiplier = multiplier;
    }

    @Override
    public void applyEffect(Combatant target) {
        target.addAttackModifier(MODIFIER_ID, StatModifier.multiplicative(multiplier));
    }

    public boolean isBuff(){
        return true;
    }

    @Override
    public void removeEffect(Combatant target) {
        target.removeAttackModifier(MODIFIER_ID);
    }
}