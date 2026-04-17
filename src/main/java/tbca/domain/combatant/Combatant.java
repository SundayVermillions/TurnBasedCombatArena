package tbca.domain.combatant;

import java.util.ArrayList;
import java.util.List;

import tbca.domain.statsmodifier.StatModifier;
import tbca.domain.statsmodifier.StatModifiersList;
import tbca.domain.effect.StatusEffect;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.logic.enemyai.AiType;
import tbca.domain.item.Item;

public abstract class Combatant {
    private final String name;
    private final int maxHp;
    private int currHp;
    private final int baseAttack;
    private final int baseDefense;
    private final int speed;
    private final StatModifiersList attackModifiers = new StatModifiersList();
    private final StatModifiersList defenseModifiers = new StatModifiersList();

    private boolean canAct = true;
    private boolean invulnerable = false;
    private int specialSkillCooldown = 0;
    private final List<StatusEffect> effects = new ArrayList<>();
    private final List<Item> inventory = new ArrayList<>();
    private AiType aiType;

    protected Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.currHp = maxHp;
        this.baseAttack = attack;
        this.baseDefense = defense;
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

    public Item consumeItem(tbca.domain.item.ItemType itemType){
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
        int actualDamageTaken = modifyHp(-damage);
        return actualDamageTaken < 0;
    }

    public boolean hasItem(tbca.domain.item.ItemType type) {
        for (Item item : inventory) {
            if (item.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public int modifyHp(int amount){
        if(amount < 0 && invulnerable){
            return 0;
        }
        int oldHp = this.currHp;
        this.setCurrHp(this.currHp + amount);
        return this.currHp - oldHp;
    }

    private void decrementCooldown() {
        if (specialSkillCooldown > 0) specialSkillCooldown--;
    }

    public boolean hasSpecialSkill() {
        return this.getSpecialSkillType() != SpecialSkillType.NONE;
    }

    public String getName() { return name; }
    public int getCurrHp() { return currHp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attackModifiers.applyAllModifiers(baseAttack); }
    public int getDefense() { return defenseModifiers.applyAllModifiers(baseDefense); }
    public int getSpeed() { return speed; }
    public boolean canAct() { return canAct; }
    public int getSpecialSkillCooldown() { return specialSkillCooldown; }
    public AiType getAiType(){ return this.aiType; }

    public void addAttackModifier(String id, StatModifier modifier) { attackModifiers.add(id, modifier); }
    public void removeAttackModifier(String id) { attackModifiers.remove(id); }
    public void addDefenseModifier(String id, StatModifier modifier) { defenseModifiers.add(id, modifier); }
    public void removeDefenseModifier(String id) { defenseModifiers.remove(id); }

    public void setCurrHp(int hp) { this.currHp = Math.max(0, Math.min(maxHp, hp)); }
    public void setCanAct(boolean canAct) { this.canAct = canAct; }
    public void setInvulnerable(boolean inv) { this.invulnerable = inv; }
    public void setSpecialSkillCooldown(int cd) { this.specialSkillCooldown = cd; }
    public void setAiType(AiType aiType) {this.aiType = aiType; }


    public abstract boolean isPlayer();
    public abstract void takeTurn();
    public boolean isAlive() { return currHp > 0; }
    public boolean isDead() { return !isAlive() ; }
}