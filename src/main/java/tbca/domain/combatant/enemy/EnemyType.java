package tbca.domain.combatant.enemy;

public enum EnemyType {

    GOBLIN("Goblin", 55, 35, 15, 25),
    WOLF  ("Wolf",   40, 45,  5, 35);

    private final String displayName;
    private final int maxHp;
    private final int attack;
    private final int defense;
    private final int speed;

    EnemyType(String displayName, int maxHp, int attack, int defense, int speed) {
        this.displayName = displayName;
        this.maxHp       = maxHp;
        this.attack      = attack;
        this.defense     = defense;
        this.speed       = speed;
    }

    public String getDisplayName() { return displayName; }
    public int getMaxHp()          { return maxHp; }
    public int getAttack()         { return attack; }
    public int getDefense()        { return defense; }
    public int getSpeed()          { return speed; }

    @Override
    public String toString()       { return displayName; }
}