package tbca.engine.action;

import tbca.domain.combatant.Combatant;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.engine.action.results.ActionResults;
import tbca.engine.action.results.UseItemResults;
import tbca.domain.item.Item;


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

        Item itemToApply = actor.consumeItem(param.itemType());


        if(itemToApply != null){
            return itemToApply.use(actor, gameState, param.targetEnemyIndex());
        }else{
            return new UseItemResults(actor, param.itemType());
            //return exception in this area
        }

    }
}
