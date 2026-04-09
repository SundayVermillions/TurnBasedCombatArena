package tbca.effect;



import java.util.List;

import tbca.combatant.Combatant;



public class SmokeBombEffect extends FieldEffect {

private final boolean creatorIsPlayer;



public SmokeBombEffect(boolean creatorIsPlayer) {

super("Smoke Bomb Cover", 2);

this.creatorIsPlayer = creatorIsPlayer;

}



@Override

public void applyEffect(List<Combatant> allCombatants) {

for (Combatant c : allCombatants) {

if (isOpposingSide(c)) {

c.setAttack(0);

}

}

}



@Override

public void removeEffect(List<Combatant> allCombatants) {

for (Combatant c : allCombatants) {

if (isOpposingSide(c)) {

c.resetAttack();

}

}

}

private boolean isOpposingSide(Combatant c) {

return c.isPlayer() != creatorIsPlayer;

}

}