package tbca.domain.combatant.enemy;

import tbca.domain.effect.statuseffect.AttackBuffEffect;
import tbca.domain.gamestate.GameState;
import tbca.domain.combatant.SpecialSkillType;
import tbca.engine.action.results.SpecialSkillResults;

public class Wolf extends Enemy {
    public Wolf(String suffix) {
        super(EnemyType.WOLF, suffix);
    }
    @Override
    public SpecialSkillType getSpecialSkillType(){
        return SpecialSkillType.WOLF_FURY;
    }

    @Override
    public SpecialSkillResults executeSpecialSkill(GameState gameState, int targetIndex){
        if (getSpecialSkillCooldown() == 0) {
            AttackBuffEffect buff = new AttackBuffEffect(2.0, 2);
            this.addStatusEffect(buff);

            this.setSpecialSkillCooldown(3);

            return new SpecialSkillResults(
                    this,
                    this,
                    0,
                    buff
            );
        }
        return new SpecialSkillResults(this);
    }
}