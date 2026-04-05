package tbca.combatant;

import java.util.ArrayList;
import java.util.List;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.GameState;
import tbca.engine.action.SpecialSkillType;

import tbca.effect.StatusEffect;
import tbca.item.Item;

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
    private final List<Item> inventory = new ArrayList<>();

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


    public void addItem(Item item) {
        if (inventory.size() < 2) {
            inventory.add(item);
        }
    }

    public List<Item> getInventory() {
        return java.util.Collections.unmodifiableList(inventory);
    }

    public boolean hasItems() { return !inventory.isEmpty(); }

    public Item consumeItem(tbca.item.ItemType itemType){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getType() == itemType){
                return inventory.remove(i);
            }
        }
        return null;
    }

    public void addStatusEffect(StatusEffect effect) {
        this.effects.add(effect);
        effect.applyEffect(this);
    }

    private void tickEffects() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            StatusEffect e = effects.get(i);
            e.decrementTurn();
            if (e.isExpired()) {
                e.removeEffect(this);
                effects.remove(i);
            }
        }
    }

    public void tickAll() {
        tickEffects();
        decrementCooldown();
    }


    public List<StatusEffect> getEffects() {
        return new ArrayList<>(this.effects);
    }

    public abstract SpecialSkillType getSpecialSkillType();
    public abstract SpecialSkillResults executeSpecialSkill(GameState gameState, int targetIndex);

    public boolean takeDamage(int damage) {
        if (invulnerable) {
            return false;
        }
        setCurrHp(getCurrHp() - damage);
        return true;
    }

    private void decrementCooldown() {
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