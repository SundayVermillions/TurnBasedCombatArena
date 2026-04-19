package tbca.domain.combatant.player.playerclass;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.SpecialSkillType;
import  tbca.domain.combatant.player.Player;
import tbca.domain.effect.statuseffect.StunEffect;
import tbca.domain.gamestate.GameState;
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
    public SpecialSkillResults executeSpecialSkill(GameState gameState, int targetIndex) {
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
    public SpecialSkillResults executeSpecialSkillFree(GameState gameState, int targetIndex) {

        if(targetIndex < 0 || targetIndex >= gameState.getCurrEnemies().size()){
            return new SpecialSkillResults(this);
        }

        Combatant target = gameState.getCurrEnemies().get(targetIndex);
       return performShieldBash(target);
    }

    @Override
    public SpecialSkillType getSpecialSkillType() {
        return SpecialSkillType.SHIELD_BASH;
    }



}