package tbca.domain.item;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.Player;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.action.results.UseItemResults;

public class PowerStone implements Item {

   @Override
   public UseItemResults use(Combatant user, GameState gameState, int targetIndex) {
       SpecialSkillResults skillResults = null;
       if (user.isPlayer()) {
           Player player = (Player) user;
           skillResults = player.executeSpecialSkillFree(gameState, targetIndex);
       }
       return new UseItemResults(user, ItemType.POWER_STONE, skillResults);
   }
   @Override
   public String getName(){
   return "Power Stone";
   }

    @Override
    public ItemType getType(){
        return ItemType.POWER_STONE;
    }
}
