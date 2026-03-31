package tbca.combatant.player;

import java.util.ArrayList;
import java.util.List;

import tbca.combatant.Combatant;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.item.Item;

public abstract class Player extends Combatant {

    private final PlayerClass playerClass;
    private final List<Item> inventory = new ArrayList<>();

    protected Player(PlayerClass playerClass) {
        super(
            playerClass.getLabel(),
            playerClass.getMaxHp(),
            playerClass.getAttack(),
            playerClass.getDefense(),
            playerClass.getSpeed()
        );
        this.playerClass = playerClass;
    }

    @Override
    public void setHp(int hp) {
        this.setCurrHp(hp);
    }

    public void executeSpecialSkillFree(Combatant target){
    }

    public void addItem(Item item) {
        if (inventory.size() < 2) {
            inventory.add(item);
        }
    }

    public Item useItem(int index) {
        if (index < 0 || index >= inventory.size()) return null;
        return inventory.remove(index);
    }

    public List<Item> getInventory() {
        return java.util.Collections.unmodifiableList(inventory);
    }

    public boolean hasItems() { return !inventory.isEmpty(); }

    @Override
    public boolean isPlayer() { return true; }

    @Override
    public boolean isAlive() { return getCurrHp() > 0; }

    public abstract void executeSpecialSkill();

    @Override
    public void takeTurn() {
        // To be handled by game engine
    }

    public PlayerClass getPlayerClass() { return playerClass; }

    @Override
    public String toString() {
        return String.format("%s (%s) [HP: %d/%d | ATK: %d | DEF: %d | SPD: %d | Cooldown: %d]",
            getName(), playerClass.getLabel(),
            getCurrHp(), getMaxHp(),
            getAttack(), getDefense(), getSpeed(),
            getSpecialSkillCooldown());
    }
}