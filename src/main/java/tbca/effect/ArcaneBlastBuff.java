package tbca.effect;

import tbca.combatant.Combatant;

public class ArcaneBlastBuff extends StatusEffect {
    public ArcaneBlastBuff() {
        super("Arcane Blast Damage Boost", -1); // -1 means it lasts forever
    }

    @Override
    public void applyEffect(Combatant target) {
        target.setAttack(target.getAttack() + 10); 
    }

    @Override
    public void removeEffect(Combatant target) {
        target.setAttack(target.getAttack() - 10); 
    }

    @Override
    public void tick(Combatant target) {
        // This effect doesn't expire on its own
    }
}