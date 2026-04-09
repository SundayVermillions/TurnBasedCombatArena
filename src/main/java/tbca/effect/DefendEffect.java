package tbca.effect;

import tbca.combatant.Combatant;

public class DefendEffect extends StatusEffect {
    private final int defenseBoost = 10;

    public DefendEffect() {
        super("Defending","Increases defend by 10 for the current and the next round", 2);
    }

    
    public void applyEffect(Combatant target) {
        target.setDefense(target.getDefense() + defenseBoost);

    }

  
    public void removeEffect(Combatant target) {
        target.setDefense(target.getDefense() - defenseBoost);

    }

    public boolean isBuff(){
        return true;
    }
}