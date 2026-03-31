package tbca.item;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;

public class PowerStone implements Item {

   @Override
   public void use(Combatant user, Combatant target) {
       if (user instanceof Player userPlayer) {
           userPlayer.executeSpecialSkillFree(target);
       }
   }
   @Override
   public String getName(){
   return "Power Stone";
   }

    public ItemType getType(){
        return ItemType.POWER_STONE;
    }
}
