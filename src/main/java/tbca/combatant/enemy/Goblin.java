package tbca.combatant.enemy;

import tbca.engine.GameState;
import tbca.engine.action.SpecialSkillType;
import tbca.engine.action.results.SpecialSkillResults;

public class Goblin extends Enemy {
    public Goblin(String suffix) {
        super(EnemyType.GOBLIN, suffix); 
    }

    @Override
    public SpecialSkillType getSpecialSkillType(){
        return SpecialSkillType.NONE;
    }

    @Override
    public SpecialSkillResults executeSpecialSkill(GameState gameState, int targetIndex){
        return new SpecialSkillResults(this);
    }
}