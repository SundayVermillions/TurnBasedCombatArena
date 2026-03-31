package tbca.effect;

import tbca.combatant.Combatant;

public class ArcaneBlastBuff extends StatusEffect {
    private final int attackBoost = 10;

    public ArcaneBlastBuff() {

        super("Arcane Power", true);
    }

    @Override
    public void applyEffect(Combatant target) {
        target.setAttack(target.getAttack() + attackBoost);

    }

    @Override
    public void removeEffect(Combatant target) {

        target.setAttack(target.getAttack() - attackBoost);
    }
}