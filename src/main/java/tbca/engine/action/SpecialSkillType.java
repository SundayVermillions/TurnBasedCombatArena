package tbca.engine.action;

public enum SpecialSkillType {
    SHIELD_BASH("Shield Bash", "Deal BasicAttack damage to selected enemy. Selected enemy is unable to take actions for the current turn and the next turn."),
    ARCANE_BLAST("Arcane Blast", "Deal BasicAttack damage to all enemies currently in combat. Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until end of the level."),
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