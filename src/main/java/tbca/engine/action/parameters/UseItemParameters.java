package tbca.engine.action.parameters;

import tbca.combatant.Combatant;
import tbca.engine.action.Action;
import tbca.engine.action.ActionType;
import tbca.engine.action.UseItemAction;
import tbca.item.ItemType;

public record UseItemParameters(Combatant actor,
                                ItemType itemType,
                                int targetEnemyIndex) implements ActionParameters {

    // overloaded constructor for when there are no targets for item action
    public UseItemParameters(Combatant actor, ItemType itemType) {
        this(actor, itemType, -1);
    }


    @Override
    public ActionType actionType() {
        return ActionType.USE_ITEM;
    }

    @Override
    public Action createAction() {
        return new UseItemAction(this);
    }
}
