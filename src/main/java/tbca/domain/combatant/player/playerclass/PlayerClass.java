package tbca.domain.combatant.player.playerclass;

public enum PlayerClass {

    WARRIOR("Warrior", 260, 40, 20, 30),
    WIZARD ("Wizard",  200, 50, 10, 20);
    private final String label;
    private final int maxHp;
    private final int attack;
    private final int defense;
    private final int speed;

    PlayerClass(String label, int maxHp, int attack, int defense, int speed) {
        this.label   = label;
        this.maxHp   = maxHp;
        this.attack  = attack;
        this.defense = defense;
        this.speed   = speed;
    }

    public String getLabel()   { return label; }
    public int getMaxHp()      { return maxHp; }
    public int getAttack()     { return attack; }
    public int getDefense()    { return defense; }
    public int getSpeed()      { return speed; }

    @Override
    public String toString() { return label; }

    public static PlayerClass fromLabel(String label) {
        for (PlayerClass pc : PlayerClass.values()) {
            if (pc.label.equalsIgnoreCase(label)) return pc;
        }
        throw new IllegalArgumentException("Unknown PlayerClass: " + label);
    }
}