package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.SpecialSkillParameters;
import tbca.engine.action.results.ActionResults;

public class SpecialSkillAction extends Action {
    public SpecialSkillAction(SpecialSkillParameters actionParameters) {

    }

    @Override
    public ActionType getType() {
        return ActionType.SPECIAL_SKILL;
    }

    @Override
    public ActionResults execute(GameState gameState) {

        return null;
    }
}
