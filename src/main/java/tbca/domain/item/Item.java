package tbca.domain.item;

import tbca.domain.combatant.Combatant;
import tbca.domain.gamestate.GameState;

import tbca.engine.action.results.UseItemResults;

public interface Item {
    UseItemResults use(Combatant user, GameState gameState, int targetIndex);
    String getName();
    ItemType getType();
}