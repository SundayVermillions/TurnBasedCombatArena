package tbca.combatant.player.playerclass;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.StunEffect;

public class Warrior extends Player {
    public Warrior() {
        super(PlayerClass.WARRIOR);
    }

    private void performShieldBash(Combatant target) {
        int damage = Math.max(0, getAttack() - target.getDefense());
        target.takeDamage(damage);
        
        target.addStatusEffect(new StunEffect()); 
        
        System.out.printf("%s uses Shield Bash! %s is STUNNED.%n", getName(), target.getName());
    }

    public void executeSpecialSkill(Combatant target) {
        if (getSpecialSkillCooldown() == 0) {
            performShieldBash(target);
            setSpecialSkillCooldown(3);
        } else {
            System.out.println("Skill is still on cooldown!");
        }
    }

    @Override
    public void executeSpecialSkillFree(Combatant target) {
        performShieldBash(target);
        System.out.println("(Power Stone) " + getName() + " activated a bonus Shield Bash!");
    }

    @Override
    public void executeSpecialSkill() {
        System.out.println("Warrior needs a target for Shield Bash.");
    }
}