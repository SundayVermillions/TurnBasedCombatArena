package tbca.item;

import tbca.combatant.Combatant;

public interface Item {
    void use(Combatant user);
    String getName(); 
}