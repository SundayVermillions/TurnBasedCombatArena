package tbca.engine.action;

import tbca.engine.action.parameters.*;

public final class ActionFactory {
    private ActionFactory() {
    }

    public static Action create(ActionParameters actionParameters) {
        return switch (actionParameters.actionType()) {
            case BASIC_ATTACK -> new BasicAttackAction(actionParameters);
            case DEFEND -> new DefendAction(actionParameters);
            case USE_ITEM -> new UseItemAction(actionParameters);
            case SPECIAL_SKILL -> new SpecialSkillAction(actionParameters);
        };
    }
}
