package tbca.item;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.engine.GameState;
import tbca.engine.action.results.SpecialSkillResults;

public class PowerStone implements Item {

   @Override
   public SpecialSkillResults use(Combatant user, GameState gameState, int targetIndex) {
       if (user.isPlayer()) {
           Player player = (Player) user;
           return player.executeSpecialSkillFree(gameState, targetIndex);
       }
       return null;
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
