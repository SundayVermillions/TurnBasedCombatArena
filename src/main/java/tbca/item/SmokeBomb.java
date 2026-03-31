package tbca.item;

import tbca.combatant.Combatant;
import tbca.effect.SmokeBombInvulnerability;

public class SmokeBomb implements Item {

    @Override
    public void use(Combatant user, Combatant target) {
        user.addStatusEffect(new SmokeBombInvulnerability());
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    public ItemType getType() {
        return ItemType.SMOKE_BOMB;
    }
}