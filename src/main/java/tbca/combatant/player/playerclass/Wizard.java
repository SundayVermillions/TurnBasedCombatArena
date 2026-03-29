package tbca.combatant.player.playerclass;

import java.util.List;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.ArcaneBlastBuff;

public class Wizard extends Player {
    public Wizard() {
        super(PlayerClass.WIZARD);
    }

    public void executeSpecialSkill(List<? extends Combatant> enemies) {
        int kills = 0;
        for (Combatant enemy : enemies) {
            if (!enemy.isAlive()) continue;
            int damage = Math.max(0, getAttack() - enemy.getDefense());
            enemy.takeDamage(damage);
            if (!enemy.isAlive()) kills++;
        }

        // Apply one buff for every kill
        for (int i = 0; i < kills; i++) {
            this.addStatusEffect(new ArcaneBlastBuff());
        }

        System.out.printf("%s unleashed Arcane Blast! Kills: %d%n", getName(), kills);
        setSpecialSkillCooldown(3);
    }

    @Override
    public void executeSpecialSkill() {
        System.out.println("Wizard needs an enemy list for Arcane Blast.");
    }
}