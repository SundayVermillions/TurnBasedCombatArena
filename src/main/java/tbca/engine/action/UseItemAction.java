package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.engine.action.results.ActionResults;
import tbca.item.Item;

import java.util.List;

public class UseItemAction extends Action {
    private final UseItemParameters param;
    public UseItemAction(UseItemParameters actionParameters) {
        this.param = actionParameters;
    }
    @Override
    public ActionType getType() {
        return ActionType.USE_ITEM;
    }

    @Override
    public ActionResults execute(GameState gameState) {

        //This is to check if it is a player
        tbca.combatant.player.Player player = (tbca.combatant.player.Player) param.actor();
        tbca.combatant.Combatant target = null;
        if(param.targetEnemyIndex() != -1){
            target = gameState.getCurrEnemies().get(param.targetEnemyIndex());
        }

        tbca.item.Item itemUse = null;
        int inventoryIndex = -1;
        List<Item> inventory = player.getInventory();

        for(int i = 0; i < inventory.size(); i++ ){
            if(inventory.get(i).getType() == param.itemType()){
                itemUse = inventory.get(i);
                inventoryIndex = i;
                break;
            }
        }
        if(itemUse != null){
            itemUse.use(player,target);
            player.useItem(inventoryIndex);
        }
        return new tbca.engine.action.results.UseItemResults(player, param.itemType());

    }
}
