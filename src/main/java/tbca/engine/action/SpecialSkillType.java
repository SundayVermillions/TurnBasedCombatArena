package tbca.engine.action;

public enum SpecialSkillType {
    SHIELD_BASH("Shield Bash"),
    ARCANE_BLAST("Arcane Blast"),
    WOLF_FURY("Wolf Fury"),
    NONE("None");

    private final String displayName;

    SpecialSkillType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}