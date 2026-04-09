package tbca.effect;

import tbca.combatant.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect() {
        super("Stunned", "Affected entity is unable to take actions for the current turn and the next turn", 2);
    }

    
    public void applyEffect(Combatant target) {
        target.setCanAct(false);
    }
    public void removeEffect(Combatant target) {
        target.setCanAct(true);
    }

    public boolean isBuff(){
        return false;
    }
   
}