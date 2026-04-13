package tbca.engine.action;

import tbca.domain.combatant.Combatant;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.parameters.SpecialSkillParameters;
import tbca.engine.action.results.ActionResults;
import tbca.engine.action.results.SpecialSkillResults;

public class SpecialSkillAction extends Action {
    private final Combatant actor;
    private final int targetEnemyIndex;
    public SpecialSkillAction(SpecialSkillParameters actionParameters) {
        this.actor = actionParameters.actor();
        this.targetEnemyIndex = actionParameters.targetEnemyIndex();


    }

    @Override
    public ActionType getType() {
        return ActionType.SPECIAL_SKILL;
    }

    @Override
    public ActionResults execute(GameState gameState) {
            SpecialSkillResults results = actor.executeSpecialSkill(gameState, targetEnemyIndex);

            return results;
        }

}
