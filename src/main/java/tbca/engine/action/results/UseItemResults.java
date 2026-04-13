package tbca.engine.action.results;

import tbca.domain.combatant.Combatant;
import tbca.engine.action.ActionType;
import tbca.domain.item.ItemType;

public record UseItemResults(Combatant actor,
                             ItemType item,
                             int hpChange,
                             int target,
                             SpecialSkillResults specialSkillResults) implements ActionResults {

    // overloaded constructors for different items
    // potion
    public UseItemResults(Combatant actor, ItemType item, int hpChange) {
        this(actor, item, hpChange, 0, null);
    }

    // smokebomb
    public UseItemResults(Combatant actor, ItemType item) {
        this (actor, item, 0, 0, null);
    }

    // power stone
    public UseItemResults(Combatant actor, ItemType item, SpecialSkillResults specialSkillResults) {
        this(actor, item, 0, 0, specialSkillResults);
    }

    @Override
    public ActionType actionType() {
        return ActionType.USE_ITEM;
    }
}
