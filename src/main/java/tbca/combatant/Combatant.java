package tbca.combatant;

import java.util.ArrayList;
import java.util.List;

import tbca.effect.StatusEffect;
import tbca.item.Item;
import tbca.item.ItemType;

public abstract class Combatant {
    private final String name;
    private final int maxHp;
    private int currHp;
    private final int baseAttack;
    private int attack;
    private final int baseDefense;
    private int defense;
    private final int speed;

    private boolean canAct = true;
    private boolean invulnerable = false;
    private int specialSkillCooldown = 0;
    private final List<StatusEffect> effects = new ArrayList<>();

    protected Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.currHp = maxHp;
        this.baseAttack = attack;
        this.attack = attack;
        this.baseDefense = defense;
        this.defense = defense;
        this.speed = speed;
    }

    public void setHp(int hp) {
        this.setCurrHp(hp);
    }

    public Item consumeItem(ItemType type){
        return null;
    }



    public void addStatusEffect(StatusEffect effect) {
        this.effects.add(effect);
        effect.applyEffect(this);
    }

    public void tickEffects() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            StatusEffect e = effects.get(i);
            e.decrementTurn();
            if (e.isExpired()) {
                e.removeEffect(this);
                effects.remove(i);
            }
        }
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    public int getRemainingEffectTurn() {
        for (StatusEffect e : effects) {
            if (e instanceof tbca.effect.StunEffect) {
                return e.getRemainingTurns();
            }
        }
        return 0;
    }

    public void takeDamage(int damage) {
        if (invulnerable) {
            System.out.println(name + " is invulnerable! No damage taken.");
            return;
        }
        setCurrHp(getCurrHp() - damage);
    }

    public void decrementCooldown() {
        if (specialSkillCooldown > 0) specialSkillCooldown--;
    }
    
    public void resetAttack() { this.attack = baseAttack; }
    public void resetDefense() { this.defense = baseDefense; }

    public String getName() { return name; }
    public int getCurrHp() { return currHp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public boolean canAct() { return canAct; }
    public int getSpecialSkillCooldown() { return specialSkillCooldown; }
    
    public void setCurrHp(int hp) { this.currHp = Math.max(0, Math.min(maxHp, hp)); }
    public void setAttack(int attack) { this.attack = attack; }
    public void setDefense(int defense) { this.defense = defense; }
    public void setCanAct(boolean canAct) { this.canAct = canAct; }
    public void setInvulnerable(boolean inv) { this.invulnerable = inv; }
    public void setSpecialSkillCooldown(int cd) { this.specialSkillCooldown = cd; }
    
    public abstract boolean isPlayer();
    public abstract void takeTurn();
    public boolean isAlive() { return currHp > 0; }
    public boolean isDead() { return !isAlive() ; }
}