package tbca.engine.action;

public final class ActionFactory {
    private ActionFactory() {
    }

    // TODO
    public static Action create(ActionParameters actionParameters) {
        return switch (actionParameters.actionType()) {
            case BASIC_ATTACK -> new BasicAttackAction(actionParameters);
            //case DEFEND -> new DefendAction();
            //case USE_ITEM -> new UseItemAction();
            //case SPECIAL_SKILL -> new SpecialSkillAction();
        };
    }
}
