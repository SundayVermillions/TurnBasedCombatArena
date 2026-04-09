package tbca.combatant.enemy;

import java.util.List;

import tbca.effect.AttackBuffEffect;
import tbca.engine.GameState;
import tbca.engine.action.SpecialSkillType;
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
            AttackBuffEffect buff = new AttackBuffEffect(2.0, 1);
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