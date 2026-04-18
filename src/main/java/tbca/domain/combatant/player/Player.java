package tbca.domain.combatant.player;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.results.SpecialSkillResults;

public abstract class Player extends Combatant {

    private final PlayerClass playerClass;

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

    public abstract SpecialSkillResults executeSpecialSkillFree(GameState gameState, int targetIndex);

    public PlayerClass getPlayerClass() { return playerClass; }

    @Override
    public boolean isPlayer() { return true; }

    @Override
    public boolean isAlive() { return getCurrHp() > 0; }

    @Override
    public String toString() {
        return String.format("%s (%s) [HP: %d/%d | ATK: %d | DEF: %d | SPD: %d | Cooldown: %d]",
            getName(), playerClass.getLabel(),
            getCurrHp(), getMaxHp(),
            getAttack(), getDefense(), getSpeed(),
            getSpecialSkillCooldown());
    }

    public boolean specialSkillNeedsTarget(){
        return true;
    }
}