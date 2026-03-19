package tbca.combatant;

public abstract class Combatant {
    private String name;
    private int maxHp, maxMp;
    private int currHp, currMp;
    private int speed, defense;

    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getCurrHp() {
        return currHp;
    }

    public int getCurrMp() {
        return currMp;
    }

    public abstract boolean isPlayer();
    public abstract void takeDamage();
    public abstract void isDead();
    public abstract void heal(int healAmt);

    @Override
    public abstract String toString();

}
