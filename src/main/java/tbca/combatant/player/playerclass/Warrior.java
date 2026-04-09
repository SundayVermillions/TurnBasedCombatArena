package tbca.combatant.player.playerclass;

import tbca.combatant.Combatant;
import  tbca.combatant.player.Player;
import tbca.effect.StunEffect;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.logic.utility.DamageUtility;

public class Warrior extends Player {
    public Warrior() {
        super(PlayerClass.WARRIOR);
    }

    private SpecialSkillResults performShieldBash(Combatant target) {
        int damage = DamageUtility.computeBasicAttackDamage(this, target);
        int exactModifiedHp = target.modifyHp(-damage);

        StunEffect stun = new StunEffect();
        target.addStatusEffect(stun);
        
        return new SpecialSkillResults(this, target, exactModifiedHp, stun);
    }

    @Override
    public SpecialSkillResults executeSpecialSkill(tbca.engine.GameState gameState, int targetIndex) {
        if (getSpecialSkillCooldown() == 0) {
            if (targetIndex >= 0 && targetIndex < gameState.getCurrEnemies().size()) {
                Combatant target = gameState.getCurrEnemies().get(targetIndex);
                setSpecialSkillCooldown(3);
                return performShieldBash(target);
            }

        }
        return new SpecialSkillResults(this);
    }


    @Override
    public SpecialSkillResults executeSpecialSkillFree(tbca.engine.GameState gameState, int targetIndex) {

        if(targetIndex < 0 || targetIndex >= gameState.getCurrEnemies().size()){
            return new SpecialSkillResults(this);
        }

        Combatant target = gameState.getCurrEnemies().get(targetIndex);
       return performShieldBash(target);
    }

    @Override
    public tbca.engine.action.SpecialSkillType getSpecialSkillType() {
        return tbca.engine.action.SpecialSkillType.SHIELD_BASH;
    }



}