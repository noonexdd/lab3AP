package ua.lpnu.menu;
import ua.lpnu.droid.*;
import ua.lpnu.battle.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {
    private final List<Droid> team1 = new ArrayList<>();
    private final List<Droid> team2 = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);

    public void start(){
        while(true){
            showMainMenu();
            int choice = getUserChoice();

            switch(choice){
                case 1: createDroidMenu(); break;
                case 2: startTeamBattle(); break;
                case 3: startDuel(); break;
                case 4: viewTeams(); break;
                case 5: destroyDroid(); break;
                case 6: replayBattle(); break;
                case 0: System.out.println("End of the game"); return;
                default: System.out.println("You entered an incorrect number. Please try again.");
            }
        }
    }

    public void showMainMenu(){
        System.out.println("Main menu");
        System.out.println("1. Create droid");
        System.out.println("2. Start team battle");
        System.out.println("3. Start Duel");
        System.out.println("4. View teams");
        System.out.println("5. Destroy the droid");
        System.out.println("6. Replay a battle from a file");
        System.out.println("0. Quit");
    }

    public int getUserChoice(){
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException ex){
            return -1;
        }
    }

    public void createDroidMenu(){
        System.out.println("Droid creation");
        System.out.println("1. Create swordsman droid");
        System.out.println("2. Create armored droid");
        System.out.println("3. Create shooter droid");
        System.out.println("4. Create mechanic droid");
        int type = getUserChoice();

        System.out.print("Enter name: ");
        String name = input.nextLine();
        Droid newDroid = null;

        switch(type){
            case 1: newDroid = new SwordsmanDroid(name); break;
            case 2: newDroid = new ArmoredDroid(name); break;
            case 3: newDroid = new ShooterDroid(name); break;
            case 4: newDroid = new MechanicDroid(name); break;
            default: System.out.println("You entered an incorrect number. Please try again."); return;
        }

        System.out.print("Which team to add the droid.(1 - team 1, 2 - team 2): ");
        int teamChoice = getUserChoice();
        if(teamChoice == 1){
            team1.add(newDroid);
            System.out.printf("Droid %s added to team 1\n", name);
        } else if(teamChoice == 2){
            team2.add(newDroid);
            System.out.printf("Droid %s added to team 2\n", name);
        } else System.out.println("Such a team does not exist");
    }

    public void startTeamBattle(){
        if(team1.isEmpty() || team2.isEmpty()){
            System.out.println("No droids for battle");
            return;
        }

        BattleLogger logger = new BattleLogger();
        List<Droid> team1Clone = team1.stream().map(Droid::clone).collect(Collectors.toList());
        List<Droid> team2Clone = team2.stream().map(Droid::clone).collect(Collectors.toList());

        TeamB teamBattle = new TeamB(team1Clone, team2Clone, logger);
        teamBattle.startFightTeam();
        saveBattle(logger);
    }

    public void startDuel(){
        if(team1.isEmpty() || team2.isEmpty()){
            System.out.println("No droids for battle");
            return;
        }
        System.out.println("Select a droid from Team 1 for the duel");
        for (int i = 0; i < team1.size(); i++) System.out.printf("%d : %s\n", i + 1, team1.get(i).statusDroid());
        System.out.print("Your choice: ");
        int choice1 = getUserChoice();
        if (choice1 < 1 || choice1 > team1.size()) {
            System.out.println("Incorrect droid number. Duel canceled.");
            return;
        }

        System.out.println("Select a droid from Team 2 for the duel");
        for (int i = 0; i < team2.size(); i++) System.out.printf("%d : %s\n", i + 1, team2.get(i).statusDroid());
        System.out.print("Your choice: ");
        int choice2 = getUserChoice();
        if (choice2 < 1 || choice2 > team2.size()) {
            System.out.println("Incorrect droid number. Duel canceled.");
            return;
        }

        Droid droid1 = team1.get(choice1 - 1).clone();
        Droid droid2 = team2.get(choice2 - 1).clone();
        BattleLogger logger = new BattleLogger();

        Duel duel = new Duel(droid1, droid2, logger);
        duel.startFightDuel();
        saveBattle(logger);
    }

    public void viewTeams(){
        System.out.println("Teams 1: ");
        if(team1.isEmpty()) System.out.println("Team 1 is empty");
        else team1.forEach(d -> System.out.println(d.statusDroid()));

        System.out.println("Team 2: ");
        if(team2.isEmpty()) System.out.println("Team 2 is empty");
        else team2.forEach(d -> System.out.println(d.statusDroid()));
    }

    public void destroyDroid(){
        System.out.println("Droid destruction");
        System.out.print("Which team droid to destroy.(1 - team 1, 2 - team 2): ");
        int teamChoice = getUserChoice();
        List<Droid> targetTeam = null;

        if(teamChoice == 1) targetTeam = team1;
        else if(teamChoice == 2) targetTeam = team2;
        else {System.out.println("Such a team does not exist"); return;}

        if (targetTeam.isEmpty()) {
            System.out.println("the team is empty");
            return;
        }
        for (int i = 0; i < targetTeam.size(); i++) System.out.printf("%d : %s", i + 1, targetTeam.get(i).statusDroid());
        System.out.print("Your choice: ");
        int choice = getUserChoice();
        if (choice < 1 || choice > targetTeam.size()) {
            System.out.println("Incorrect droid number.");
            return;
        }

        targetTeam.remove(choice - 1);
        System.out.println("The droid was destroyed.");
    }

    public void replayBattle() {
        System.out.print("Enter the name of the file to play: ");
        String filename = input.nextLine();

        System.out.printf("Playing back a battle from a file: %s\n", filename );
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) System.out.println(line); // Виводимо рядок у консоль
            System.out.println("Playback complete");
        } catch (IOException e) {
            System.err.printf("File read error: %s", e.getMessage());
        }
    }

    public void saveBattle(BattleLogger logger){
        System.out.print("Would you like to save the fight record? (yes/no): ");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the file name: ");
            String filename = input.nextLine();
            logger.saveToFile(filename);
        }
    }
}
