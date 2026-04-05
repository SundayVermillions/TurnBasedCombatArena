package tbca.combatant.player.playerclass;

import java.util.ArrayList;
import java.util.List;
import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.ArcaneBlastBuff;
import tbca.effect.StatusEffect;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.logic.utility.DamageUtility;

public class Wizard extends Player {
    public Wizard() {
        super(PlayerClass.WIZARD);
    }
    private SpecialSkillResults performArcaneBlast(List<? extends Combatant> enemies) {
        List<Integer> targetIndice = new ArrayList<>();
        List<Integer> damageDone = new ArrayList<>();
        List<StatusEffect> appliedEffects = new ArrayList<>();
        int kills = 0;
        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            if (!enemy.isAlive()) continue;
            int damage = DamageUtility.computeBasicAttackDamage(this, enemy);
            enemy.takeDamage(damage);

            targetIndice.add(i);
            damageDone.add(damage);
            appliedEffects.add(null);
            
            if (!enemy.isAlive()) {
                kills++;
            }
        }

        for(int i = 0; i < kills; i++){
            ArcaneBlastBuff buff = new ArcaneBlastBuff();
            this.addStatusEffect(buff);

        }
        return new SpecialSkillResults(this, targetIndice, damageDone, appliedEffects);
    }
    @Override
    public SpecialSkillResults executeSpecialSkill(tbca.engine.GameState gameState, int targetIndex) {
        if (getSpecialSkillCooldown() == 0) {
            List<Combatant> enemies = gameState.getCurrEnemies();

            setSpecialSkillCooldown(3);

            return performArcaneBlast(enemies);
        }
        return new SpecialSkillResults(this);
    }
    @Override
    public SpecialSkillResults executeSpecialSkillFree(tbca.engine.GameState gameState, int targetIndex) {
        List<Combatant> enemies = gameState.getCurrEnemies();
        return performArcaneBlast(enemies);

    }
    @Override
    public boolean specialSkillNeedsTarget(){
        return false;
    }

}