package tbca.item;

public enum ItemType {
    POTION("Potion", "Restores HP to the user"),
    POWER_STONE("Power Stone", "Immediate triggers your special skill effect once."),
    SMOKE_BOMB("Smoke Bomb", "Enemy attacks do 0 damage in the current turn and the next turn");

    private final String displayName;
    private final String description;

    ItemType(String displayName, String description) {

        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription(){
        return description;
    }
}