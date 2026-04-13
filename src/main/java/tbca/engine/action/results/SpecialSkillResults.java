package tbca.engine.action.results;

import tbca.domain.combatant.Combatant;
import tbca.domain.effect.StatusEffect;
import tbca.engine.action.ActionType;

import java.util.List;

public record SpecialSkillResults(Combatant actor,
                                  List<Combatant> targets,
                                  List<Integer> hpChanges,
                                  List<StatusEffect> statusEffects) implements ActionResults {
    //If here are no targets/damages/effects at all
    public SpecialSkillResults(Combatant actor){
        this(actor, List.of(), List.of(), List.of());
    }
    public SpecialSkillResults(Combatant actor, Combatant singleTarget, int singleHpChange, StatusEffect effect){
        this(actor,
                List.of(singleTarget),
                List.of(singleHpChange),
                effect != null ? List.of(effect) : List.of());
    }
    @Override
    public ActionType actionType() {
        return ActionType.SPECIAL_SKILL;
    }
}
