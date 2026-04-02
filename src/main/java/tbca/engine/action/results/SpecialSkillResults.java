package tbca.engine.action.results;

import tbca.combatant.Combatant;
import tbca.effect.StatusEffect;
import tbca.engine.action.ActionType;

import java.util.List;

public record SpecialSkillResults(Combatant actor,
                                  List<Integer> targets,
                                  List<Integer> dmg,
                                  List<StatusEffect> statusEffects) implements ActionResults {
    //If here are no targets/damages/effects at all
    public SpecialSkillResults(Combatant actor){
        this(actor, List.of(), List.of(), List.of());
    }
    public SpecialSkillResults(Combatant actor, int singleTargetIndex, int singleDmg, StatusEffect effect){
        this(actor,
                List.of(singleTargetIndex),
                List.of(singleDmg),
                effect != null ? List.of(effect) : List.of());
    }
    @Override
    public ActionType actionType() {
        return ActionType.SPECIAL_SKILL;
    }
}
