package tbca.ui.Menu;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.ui.Input.InputValidator;

import java.util.Scanner;

public class ClassSelection {
    InputValidator inputValidator;
    public ClassSelection() {
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }
    public PlayerClass classChoice()
    {
        displayClassOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, 2);
        return switch (choice) {
            case 1 -> PlayerClass.WARRIOR;
            case 2 -> PlayerClass.WIZARD;
            default -> null;
        };
    }


    private void displayClassOptions() {
        System.out.println("Select your class:");
        System.out.println("1: Warrior");
        System.out.println("HP: 260");
        System.out.println("Attack: 40");
        System.out.println("Defense: 20");
        System.out.println("Speed: 30");
        System.out.println("Special Skill: Shield Bash");
        System.out.println("Effect: Deal BasicAttack damage to selected enemy.");
        System.out.println("Selected enemy is unable to take actions for the current turn and the next turn.\n");

        System.out.println("2: Wizard");
        System.out.println("HP: 200");
        System.out.println("Attack: 50");
        System.out.println("Defense: 10");
        System.out.println("Speed: 20");
        System.out.println("Special Skill: Arcane Blast");
        System.out.println("Effect: Deal BasicAttack damage to all enemies currently in combat.");
        System.out.println("Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until end of the level.");
        System.out.print("Enter 1 or 2: ");
    }
}
