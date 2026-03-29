package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.SpecialSkillParameters;
import tbca.ui.Ui;

public class SpecialSkillAction extends Action {
    public SpecialSkillAction(SpecialSkillParameters actionParameters) {

    }

    @Override
    public ActionType getType() {
        return ActionType.SPECIAL_SKILL;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {

    }
}
