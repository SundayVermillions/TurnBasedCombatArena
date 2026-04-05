package tbca.combatant.enemy;

import tbca.engine.GameState;
import tbca.engine.action.SpecialSkillType;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.effect.AttackBuffEffect;

import java.util.List;

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
        AttackBuffEffect buff = new AttackBuffEffect(2.0, 1);
        this.addStatusEffect(buff);

        this.setSpecialSkillCooldown(3);

        return new SpecialSkillResults(
                this,
                List.of(),
                List.of(),
                List.of(buff)
        );
    }

}