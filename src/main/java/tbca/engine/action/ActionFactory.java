package tbca.engine.action;

import tbca.engine.action.parameters.*;

public final class ActionFactory {
    private ActionFactory() {
    }

    public static Action create(ActionParameters actionParameters) {
        return switch (actionParameters.actionType()) {
            case BASIC_ATTACK -> new BasicAttackAction((BasicAttackParameters) actionParameters);
            case DEFEND -> new DefendAction((DefendParameters) actionParameters);
            case USE_ITEM -> new UseItemAction((UseItemParameters) actionParameters);
            case SPECIAL_SKILL -> new SpecialSkillAction((SpecialSkillParameters) actionParameters);
        };
    }
}
