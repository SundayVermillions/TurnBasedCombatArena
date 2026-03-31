package tbca.engine.action.results;

import tbca.combatant.Combatant;
import tbca.effect.StatusEffect;
import tbca.engine.action.ActionType;

import java.util.List;

public record SpecialSkillResults(Combatant actor,
                                  List<Integer> targets,
                                  List<Integer> dmg,
                                  List<StatusEffect> statusEffects) implements ActionResults {
    @Override
    public ActionType actionType() {
        return ActionType.SPECIAL_SKILL;
    }
}
