package tbca.engine.logic.enemyai;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;

public class AdvancedAi implements AiController {
    @Override
    public ActionParameters decide(Combatant npc, GameStateReadOnly gameState) {
        return new SimpleAi().decide(npc, gameState); // TODO
    }
}

// TODO: implement this after darshan/smyan/ui guys finish their parts
// general purpose ai.
//
//
// item usage
//first, check if npc has a potion. then check if player's attack - current enemy's def is higher than current hp. if so, if current enemy is faster, use potion, otherwise, high chance of attack and low chance of using potion
//check if enemy have a smoke bomb. if so, check if player has specialskill active. if so, slightly higher chance of using smoke bomb or defending
//
//specialskill usage
//check if enemy has a specialskill. if so, and not on cooldown, high chance of using specialskill
//
//basicattack usage
//if enemy has no items left and no specialskills,