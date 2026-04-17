package tbca.domain.combatant.player.playerclass;

import java.util.ArrayList;
import java.util.List;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.SpecialSkillType;
import tbca.domain.combatant.player.Player;
import tbca.domain.effect.ArcaneBlastBuff;
import tbca.domain.effect.StatusEffect;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.logic.utility.DamageUtility;

public class Wizard extends Player {
    public Wizard() {
        super(PlayerClass.WIZARD);
    }
    private SpecialSkillResults performArcaneBlast(List<Combatant> enemies) {
        List<Combatant> targetsHit = new ArrayList<>();
        List<Integer> hpChanges = new ArrayList<>();
        List<StatusEffect> appliedEffects = new ArrayList<>();
        int kills = 0;
        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            if (!enemy.isAlive()) continue;
            int damage = DamageUtility.computeBasicAttackDamage(this, enemy);
            int exactModifiedHp = enemy.modifyHp(-damage);

            targetsHit.add(enemy);
            hpChanges.add(exactModifiedHp);
            appliedEffects.add(null);

            if (!enemy.isAlive()) {
                kills++;
            }
        }

        for(int i = 0; i < kills; i++){
            ArcaneBlastBuff buff = new ArcaneBlastBuff();
            this.addStatusEffect(buff);

            targetsHit.add(this);
            hpChanges.add(0);
            appliedEffects.add(buff);

        }
        return new SpecialSkillResults(this, targetsHit, hpChanges, appliedEffects);
    }
    @Override
    public SpecialSkillResults executeSpecialSkill(GameState gameState, int targetIndex) {
        if (getSpecialSkillCooldown() == 0) {
            List<Combatant> enemies = gameState.getCurrEnemies();

            setSpecialSkillCooldown(3);

            return performArcaneBlast(enemies);
        }
        return new SpecialSkillResults(this);
    }
    @Override
    public SpecialSkillResults executeSpecialSkillFree(GameState gameState, int targetIndex) {
        List<Combatant> enemies = gameState.getCurrEnemies();
        return performArcaneBlast(enemies);

    }
    @Override
    public boolean specialSkillNeedsTarget(){
        return false;
    }

    @Override
    public SpecialSkillType getSpecialSkillType() {
        return SpecialSkillType.ARCANE_BLAST;
    }

}