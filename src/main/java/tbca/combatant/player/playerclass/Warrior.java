package tbca.combatant.player.playerclass;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.StunEffect;

public class Warrior extends Player {
    public Warrior() {
        super(PlayerClass.WARRIOR);
    }

    public void executeSpecialSkill(Combatant target) {
        int damage = Math.max(0, getAttack() - target.getDefense());
        target.takeDamage(damage);
        
        // Use the effect from your effect.zip
        target.addStatusEffect(new StunEffect());
        
        System.out.printf("%s uses Shield Bash! %s is STUNNED.%n", getName(), target.getName());
        setSpecialSkillCooldown(3);
    }

    @Override
    public void executeSpecialSkill() {
        System.out.println("Warrior needs a target for Shield Bash.");
    }
}
