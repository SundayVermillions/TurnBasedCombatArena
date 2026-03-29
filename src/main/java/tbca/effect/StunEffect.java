package tbca.effect;

import tbca.combatant.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect() {
        super("Stunned", 2); 
    }

    
    public void applyEffect(Combatant target) {
        System.out.println(target.getName() + " is stunned and cannot move!");
    }

   
    public void removeEffect(Combatant target) {
        System.out.println(target.getName() + " recovered from the stun.");
    }
}