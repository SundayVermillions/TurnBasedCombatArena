package tbca.engine.logic.damage;

import tbca.combatant.Combatant;

import static java.lang.Math.max;

public final class DamageUtility {
    private DamageUtility() {}

    public static int computeBasicAttackDamage(Combatant actor, Combatant target) {
        return max(0, actor.getAttack() - actor.getDefense());
    }
}
