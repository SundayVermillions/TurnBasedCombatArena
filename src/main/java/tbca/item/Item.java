package tbca.item;

import tbca.combatant.Combatant;

public interface Item {
    void use(Combatant user, Combatant target);
    String getName();
    ItemType getType();
}