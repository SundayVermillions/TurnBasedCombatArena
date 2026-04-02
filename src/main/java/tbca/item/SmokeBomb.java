package tbca.item;

import tbca.combatant.Combatant;
import tbca.effect.SmokeBombInvulnerability;
import tbca.engine.GameState;
import tbca.engine.action.results.SpecialSkillResults;

public class SmokeBomb implements Item {

    @Override
    public SpecialSkillResults use(Combatant user, GameState gameState, int targetIndex) {
        user.addStatusEffect(new SmokeBombInvulnerability());
        return null;
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    public ItemType getType() {
        return ItemType.SMOKE_BOMB;
    }
}