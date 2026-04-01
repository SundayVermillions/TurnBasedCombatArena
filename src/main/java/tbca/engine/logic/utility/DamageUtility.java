package tbca.engine.logic.utility;

import tbca.combatant.Combatant;

import static java.lang.Math.max;

public final class DamageUtility {
    private DamageUtility() {}

    public static int computeBasicAttackDamage(Combatant actor, Combatant target) {
        return max(0, actor.getAttack() - target.getDefense());
    }
}
