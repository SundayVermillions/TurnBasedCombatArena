package tbca.engine.action;

public enum SpecialSkillType {
    SHIELD_BASH("Shield Bash", "Deal damage to one enemy, \nstun for 2 turns"),
    ARCANE_BLAST("Arcane Blast", "Deal damage to all enemies, \neach enemy defeated by it adds 10 to\nthe Wizard's Attack until the end of the level."),
    WOLF_FURY("Wolf Fury", "The wolf prepares a vicious attack, doubling its attack power for 1 turn."),
    NONE("None", "");

    private final String displayName;
    private final String description;

    SpecialSkillType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}