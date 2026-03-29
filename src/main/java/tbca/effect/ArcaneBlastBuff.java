package tbca.effect;

import tbca.combatant.Combatant;

public class ArcaneBlastBuff extends StatusEffect {
    private final int attackBoost = 10;

    public ArcaneBlastBuff() {
        super("Arcane Power", 999); // 999 acts as the end of the level
    }

    
    public void applyEffect(Combatant target) {
        target.setAttack(target.getAttack() + attackBoost);
        System.out.println(target.getName() + " absorbs arcane energy! Attack increased by 10.");
    }

    
    public void removeEffect(Combatant target) {
        target.setAttack(target.getAttack() - attackBoost);
    }
}