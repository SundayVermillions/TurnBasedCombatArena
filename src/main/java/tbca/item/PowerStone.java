package tbca.item;

import tbca.combatant.Combatant;

public class PowerStone implements Item {

   
    public void use(Combatant user) {
        System.out.println(user.getName() + " crushed a Power Stone!");
        System.out.println("The stone glows, preparing to trigger a free special skill!");
        
        // TODO: uncomment this once you have implemented the method in Combatant
        // user.executeSpecialSkillFree(); 
    }

    
    public String getName() {
        return "Power Stone";
    }
}