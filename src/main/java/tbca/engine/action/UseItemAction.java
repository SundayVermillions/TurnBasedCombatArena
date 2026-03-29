package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.ui.Ui;

public class UseItemAction extends Action {
    public UseItemAction(UseItemParameters actionParameters) {

    }
    @Override
    public ActionType getType() {
        return ActionType.USE_ITEM;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {

    }
}
