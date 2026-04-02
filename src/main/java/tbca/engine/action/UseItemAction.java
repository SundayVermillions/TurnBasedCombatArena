package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.engine.GameState;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.engine.action.results.ActionResults;
import tbca.engine.action.results.UseItemResults;
import tbca.item.Item;


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
        Combatant actor = param.actor();

        if(!actor.isPlayer()){
            System.out.println(actor.getName() + " cannot use items!");
            //return the failed result
            return new UseItemResults(actor, param.itemType());
        }
        Player player = (Player) actor;

        //To encapsulate the item
        Item itemToApply = player.consumeItem(param.itemType());

        if(itemToApply != null){
            itemToApply.use(player, gameState, param.targetEnemyIndex());
        }else{
            System.out.println(player.getName() + " does not have that item in their inventory!");
        }
        return new UseItemResults(player,param.itemType());


    }
}
