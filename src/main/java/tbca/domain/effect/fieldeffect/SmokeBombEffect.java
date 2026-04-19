package tbca.domain.effect.fieldeffect;

import java.util.List;

import tbca.domain.combatant.Combatant;
import tbca.domain.statsmodifier.StatModifier;
import tbca.domain.gamestate.GameState;

public class SmokeBombEffect extends FieldEffect {

    private static final String MODIFIER_ID = "smoke-bomb-effect";
    private final boolean creatorIsPlayer;

    public SmokeBombEffect(boolean creatorIsPlayer) {
        super("Smoke Bomb Blindness", 2);
        this.creatorIsPlayer = creatorIsPlayer;
    }

    @Override
    public boolean appliesTo(Combatant combatant) {
        return isOpposingSide(combatant);
    }

    @Override
    public void applyEffect(GameState gameState) {
        for (Combatant c : gameState.getAllCombatants()) {
            if (isOpposingSide(c)) {
                c.addAttackModifier(MODIFIER_ID, StatModifier.finalOverride(0));
            }
        }
    }

    @Override
    public void removeEffect(List<Combatant> allCombatants) {
        for (Combatant c : allCombatants) {
            if (isOpposingSide(c)) {
                c.removeAttackModifier(MODIFIER_ID);
            }
        }
    }

    private boolean isOpposingSide(Combatant c) {
        return c.isPlayer() != creatorIsPlayer;
    }

    public String getDescription(){
        return "Enemy attacks do 0 damage in the current turn and the next turn";
    }

    public String getName(){
        return super.getName();
    }
}