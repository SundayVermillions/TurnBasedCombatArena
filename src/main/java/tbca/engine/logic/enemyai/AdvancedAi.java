package tbca.engine.logic.enemyai;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.engine.action.parameters.SpecialSkillParameters;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.item.ItemType;
import java.util.Random;

public class AdvancedAi implements AiController {
    private static final Random RAND = new Random();

    @Override
    public ActionParameters decide(Combatant npc, GameStateReadOnly gameState) {
        ActionParameters action;

        action = considerItem(npc, gameState);
        if (action != null) return action;

        action = considerSpecialSkill(npc);
        if (action != null) return action;

        return new BasicAttackParameters(npc);
    }

    private ActionParameters considerItem(Combatant npc, GameStateReadOnly gameState) {
        if (npc.hasItem(ItemType.POTION) && considerHeal(npc, gameState)) {
            return new UseItemParameters(npc, ItemType.POTION);
        }

        if (npc.hasItem(ItemType.SMOKE_BOMB) && considerSmokeBomb(gameState)) {
            return new UseItemParameters(npc, ItemType.SMOKE_BOMB);
        }

        return null;
    }

    private ActionParameters considerSpecialSkill(Combatant npc) {
        if (npc.hasSpecialSkill() && npc.getSpecialSkillCooldown() == 0) {
            if (RAND.nextDouble() < 0.8) {
                return new SpecialSkillParameters(npc, 0);
            }
        }
        return null;
    }


    private boolean considerHeal(Combatant npc, GameStateReadOnly gameState) {
        int predictedDamage = gameState.getPlayer().getAttack() - npc.getDefense();

        if (predictedDamage >= npc.getCurrHp()) {
            boolean isFaster = npc.getSpeed() > gameState.getPlayer().getSpeed();
            return isFaster || RAND.nextDouble() < 0.6;
        }

        return false;
    }

    private boolean considerSmokeBomb(GameStateReadOnly gameState) {
        return gameState.getPlayer().getSpecialSkillCooldown() == 0 && RAND.nextDouble() < 0.4;
    }
}
