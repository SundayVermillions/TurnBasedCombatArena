package tbca.domain.effect.statuseffect;

import tbca.domain.combatant.Combatant;
import tbca.domain.statsmodifier.StatModifier;

public class DefendEffect extends StatusEffect {
    private static final String MODIFIER_ID = "defend-effect";
    private final int defenseBoost = 10;

    public DefendEffect() {
        super("Defending","Increases defend by 10 for the current and the next round", 2);
    }

    @Override
    public void applyEffect(Combatant target) {
        target.addDefenseModifier(MODIFIER_ID, StatModifier.additive(defenseBoost));
    }

    @Override
    public void removeEffect(Combatant target) {
        target.removeDefenseModifier(MODIFIER_ID);
    }

    public boolean isBuff(){
        return true;
    }
}