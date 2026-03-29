package tbca.item;

import tbca.combatant.Combatant;

public class Potion implements Item {

   
    public void use(Combatant user) {
        int healAmount = 100;
        int newHp = Math.min(user.getCurrHp() + healAmount, user.getMaxHp());
        user.setHp(newHp);
        System.out.println(user.getName() + " used a Potion. HP restored to " + newHp + ".");
    }

  
    public String getName() {
        return "Potion";
    }
}