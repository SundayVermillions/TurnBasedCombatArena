package tbca.combatant.player.playerclass;

import java.util.List;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.ArcaneBlastBuff;

public class Wizard extends Player {
    public Wizard() {
        super(PlayerClass.WIZARD);
    }
    private void performArcaneBlast(List<? extends Combatant> enemies) {
        int kills = 0;
        for (Combatant enemy : enemies) {
            if (!enemy.isAlive()) continue;
            int damage = Math.max(0, getAttack() - enemy.getDefense());
            enemy.takeDamage(damage);
            
            if (!enemy.isAlive()) {
                kills++;
            }
        }
        for (int i = 0; i < kills; i++) {
            this.addStatusEffect(new ArcaneBlastBuff());
        }

        System.out.printf("%s unleashed Arcane Blast! Kills: %d%n", getName(), kills);
    }

    public void executeSpecialSkill(List<? extends Combatant> enemies) {
        if (getSpecialSkillCooldown() == 0) {
            performArcaneBlast(enemies);
            setSpecialSkillCooldown(3);
        } else {
            System.out.println("Arcane Blast is still recharging!");
        }
    }

    public void executeSpecialSkillFree(List<? extends Combatant> enemies) {
        performArcaneBlast(enemies);
        System.out.println("(Power Stone) " + getName() + " triggered a bonus Arcane Blast!");
    }

    @Override
    public void executeSpecialSkill() {
        System.out.println("Wizard needs an enemy list for Arcane Blast.");
    }
}