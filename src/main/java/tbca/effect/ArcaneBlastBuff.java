package tbca.effect;

import tbca.combatant.Combatant;

public class ArcaneBlastBuff extends StatusEffect {
    private final int attackBoost = 10;

    public ArcaneBlastBuff() {

        super("Arcane Power","Each enemy defeated by Arcane Blast", true);
    }

    @Override
    public void applyEffect(Combatant target) {
        target.setAttack(target.getAttack() + attackBoost);

    }

    public boolean isBuff(){
        return true;
    }

    @Override
    public void removeEffect(Combatant target) {

        target.setAttack(target.getAttack() - attackBoost);
    }
}