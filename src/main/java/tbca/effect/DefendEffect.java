package tbca.effect;

import tbca.combatant.Combatant;

public class DefendEffect extends StatusEffect {
    public DefendEffect() {
        super("Defend", 2); 
    }

    @Override
    public void applyEffect(Combatant target) {
        target.setDefense(target.getDefense() + 10); 
    }

    @Override
    public void removeEffect(Combatant target) {
        target.setDefense(target.getDefense() - 10); 
    }
}