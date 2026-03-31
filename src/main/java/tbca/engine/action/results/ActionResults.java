package tbca.engine.action.results;

import tbca.engine.action.ActionType;

public sealed interface ActionResults permits BasicAttackResults,
                                                DefendResults,
                                                SpecialSkillResults,
                                                UseItemResults {
    ActionType actionType();
}
