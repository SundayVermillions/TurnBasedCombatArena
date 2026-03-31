package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.engine.GameState;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.engine.action.results.ActionResults;
import tbca.engine.action.results.BasicAttackResults;
import tbca.engine.logic.utility.DamageUtility;


public class BasicAttackAction extends Action {
    private final Combatant actor;
    private final int targetEnemyIndex;


    public BasicAttackAction(BasicAttackParameters actionParameters) {
        this.actor = actionParameters.actor();
        this.targetEnemyIndex = actionParameters.targetEnemyIndex();
    }
    @Override
    public ActionType getType() {
        return ActionType.BASIC_ATTACK;
    }

    @Override
    public ActionResults execute(GameState gameState) {
        Combatant target = (actor.isPlayer()) ?
                                    gameState.getCurrEnemies().get(targetEnemyIndex)
                                    : gameState.getPlayer();
        int dmg = DamageUtility.computeBasicAttackDamage(actor, target);
        target.takeDamage(dmg);
        return new BasicAttackResults(actor, targetEnemyIndex, dmg);
    }
}
