package tbca.combatant;

import java.util.ArrayList;
import java.util.List;
import tbca.effect.StatusEffect;

public abstract class Combatant {
    private String name;
    private int maxHp, maxMp;
    private int currHp, currMp;
    private int speed, defense;
    private int attack = 10; // Added for Buffs
    private boolean canAct = true; // Added for Stun
    private boolean invulnerable = false; // Added for Smoke Bomb
    private List<StatusEffect> effects = new ArrayList<>(); // To track effects

    
    public int getMaxHp() { return maxHp; }
    public int getMaxMp() { return maxMp; }
    public int getCurrHp() { return currHp; }
    public int getCurrMp() { return currMp; }
    public int getSpeed() { return speed; }
    public String getName() { return name; } // Added so items can print name

    public abstract boolean isPlayer();
    public abstract void takeDamage();
    public abstract boolean isDead();
    public abstract void heal(int healAmt);
    @Override
    public abstract String toString();
    public abstract boolean isAlive();

    // --- New Methods added for Items/Effects ---
    public void setHp(int hp) { this.currHp = hp; }
    public int getHp() { return currHp; }
    
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public void setCanAct(boolean canAct) { this.canAct = canAct; }
    public void setInvulnerable(boolean invulnerable) { this.invulnerable = invulnerable; }
    
    public void addStatusEffect(StatusEffect effect) { 
        effects.add(effect);
        effect.applyEffect(this);
    }

    public void executeSpecialSkillFree() {
        System.out.println(name + " uses a special skill for free!");
    }
}