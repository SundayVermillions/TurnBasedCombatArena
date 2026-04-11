package tbca.effect;

import tbca.combatant.Combatant;
import tbca.combatant.statsmodifier.StatModifier;

import java.util.UUID;

public class ArcaneBlastBuff extends StatusEffect {
    private static final int attackBoost = 10;
    private final String modifierId = "arcane-blast-buff-" + UUID.randomUUID();

    public ArcaneBlastBuff() {
        super("Arcane Power", "Each enemy defeated by Arcane Blast adds " + attackBoost + " to the Wizard's attack, lasting until end of the level.", true);
    }

    @Override
    public void applyEffect(Combatant target) {
        target.addAttackModifier(modifierId, StatModifier.additive(attackBoost));
    }

    public boolean isBuff(){
        return true;
    }

    @Override
    public void removeEffect(Combatant target) {
        target.removeAttackModifier(modifierId);
    }
}