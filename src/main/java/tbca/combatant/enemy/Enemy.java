package tbca.combatant.enemy;

import tbca.combatant.Combatant;

public abstract class Enemy extends Combatant {

    private final EnemyType type;

    protected Enemy(EnemyType type, String suffix) {
        super(
            type.getDisplayName() + " " + suffix,
            type.getMaxHp(),
            type.getAttack(),
            type.getDefense(),
            type.getSpeed()
        );
        this.type = type;
    }

    public EnemyType getType() { return type; }

    @Override
    public boolean isPlayer() { return false; }

    @Override
    public void takeTurn() {
        System.out.println(getName() + " prepares to attack!");
    }

    @Override
    public boolean isAlive() { return getCurrHp() > 0; }

    @Override
    public String toString() {
        return String.format("%s [HP: %d/%d | ATK: %d | DEF: %d | SPD: %d]",
            getName(), getCurrHp(), getMaxHp(),
            getAttack(), getDefense(), getSpeed());
    }
}