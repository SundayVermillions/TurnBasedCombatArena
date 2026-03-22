package tbca.effect;

import tbca.combatant.Combatant;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super("Stun", 2); 
    }

    
    public void applyEffect(Combatant target) {
        target.setCanAct(false); 
    }


    public void removeEffect(Combatant target) {
        target.setCanAct(true);
    }
}