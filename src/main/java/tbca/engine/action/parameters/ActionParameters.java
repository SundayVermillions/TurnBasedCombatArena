package tbca.engine.action.parameters;

import tbca.engine.action.ActionType;

public sealed interface ActionParameters permits BasicAttackParameters,
                                                    DefendParameters,
                                                    SpecialSkillParameters,
                                                    UseItemParameters {
    ActionType actionType();

}

