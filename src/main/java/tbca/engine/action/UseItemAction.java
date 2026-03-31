package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.engine.action.results.ActionResults;

public class UseItemAction extends Action {
    public UseItemAction(UseItemParameters actionParameters) {

    }
    @Override
    public ActionType getType() {
        return ActionType.USE_ITEM;
    }

    @Override
    public ActionResults execute(GameState gameState) {
        return null;
    }
}
