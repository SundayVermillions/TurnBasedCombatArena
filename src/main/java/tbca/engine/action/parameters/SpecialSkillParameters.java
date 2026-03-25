package tbca.engine.action.parameters;

import tbca.combatant.Combatant;
import tbca.engine.action.ActionType;

public record SpecialSkillParameters(Combatant actor,
                                     int targetEnemyIndex) implements ActionParameters {
    @Override
    public ActionType actionType() {
        return ActionType.SPECIAL_SKILL;
    }
}
