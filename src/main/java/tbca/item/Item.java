package tbca.item;

import tbca.combatant.Combatant;
import tbca.engine.GameState;

import tbca.engine.action.results.UseItemResults;

public interface Item {
    UseItemResults use(Combatant user, GameState gameState, int targetIndex);
    String getName();
    ItemType getType();
}