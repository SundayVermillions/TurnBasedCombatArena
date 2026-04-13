package tbca.engine.action.parameters;

import tbca.domain.combatant.Combatant;
import tbca.engine.action.Action;
import tbca.engine.action.ActionType;
import tbca.engine.action.SpecialSkillAction;

public record SpecialSkillParameters(Combatant actor,
                                     int targetEnemyIndex) implements ActionParameters {
    @Override
    public ActionType actionType() {
        return ActionType.SPECIAL_SKILL;
    }

    @Override
    public Action createAction() {
        return new SpecialSkillAction(this);
    }
}
