package tbca.item;

import tbca.combatant.Combatant;
import tbca.effect.SmokeBombInvulnerability;
import tbca.engine.GameState;
import tbca.engine.action.results.UseItemResults;

public class SmokeBomb implements Item {

    @Override
    public UseItemResults use(Combatant user, GameState gameState, int targetIndex) {
        user.addStatusEffect(new SmokeBombInvulnerability());
        return new UseItemResults(user, ItemType.SMOKE_BOMB);
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    public ItemType getType() {
        return ItemType.SMOKE_BOMB;
    }
}