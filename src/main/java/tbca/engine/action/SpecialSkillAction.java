package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.engine.GameState;
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
