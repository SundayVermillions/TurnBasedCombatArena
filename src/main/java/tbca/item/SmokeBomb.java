package tbca.item;

import tbca.combatant.Combatant;
import tbca.effect.SmokeBombEffect;
import tbca.engine.GameState;
import tbca.engine.action.results.UseItemResults;

public class SmokeBomb implements Item {

    @Override
    public UseItemResults use(Combatant user, GameState gameState, int targetIndex) {
        SmokeBombEffect effect = new SmokeBombEffect(user.isPlayer());
        gameState.addFieldEffect(effect);
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