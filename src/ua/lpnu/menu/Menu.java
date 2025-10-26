package ua.lpnu.menu;
import ua.lpnu.droid.*;
import ua.lpnu.battle.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                case 6: System.out.println("End of the game"); return;
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
        System.out.println("6. Quit");
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

        System.out.println("Enter name");
        String name = input.nextLine();
        Droid newDroid = null;

        switch(type){
            case 1: newDroid = new swordsmanDroid(name); break;
            case 2: newDroid = new armoredDroid(name); break;
            case 3: newDroid = new shooterDroid(name); break;
            case 4: newDroid = new mechanicDroid(name); break;
            default: System.out.println("You entered an incorrect number. Please try again."); return;
        }

        System.out.println("Which team to add the droid.(1 - team 1, 2 - team 2)");
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

        List<Droid> team1Clone = team1.stream().map(Droid::clone).collect(Collectors.toList());
        List<Droid> team2Clone = team2.stream().map(Droid::clone).collect(Collectors.toList());

        TeamB teamBattle = new TeamB(team1Clone, team2Clone);
        teamBattle.startFightTeam();
    }

    public void startDuel(){
        if(team1.isEmpty() || team2.isEmpty()){
            System.out.println("No droids for battle");
            return;
        }
        System.out.println("Select a droid from Team 1 for the duel");
        for (int i = 0; i < team1.size(); i++) System.out.printf("%d : %s", i + 1, team1.get(i).statusDroid());
        System.out.println("Your choice:");
        int choice1 = getUserChoice();
        if (choice1 < 1 || choice1 > team1.size()) {
            System.out.println("Incorrect droid number. Duel canceled.");
            return;
        }

        System.out.println("Select a droid from Team 2 for the duel");
        for (int i = 0; i < team2.size(); i++) System.out.printf("%d : %s", i + 1, team2.get(i).statusDroid());
        System.out.println("Your choice:");
        int choice2 = getUserChoice();
        if (choice2 < 1 || choice2 > team2.size()) {
            System.out.println("Incorrect droid number. Duel canceled.");
            return;
        }

        Droid droid1 = team1.get(choice1 - 1).clone();
        Droid droid2 = team2.get(choice2 - 1).clone();

        Duel duel = new Duel(droid1, droid2);
        duel.startFightDuel();
    }

    public void viewTeams(){
        System.out.println("Teams 1:");
        if(team1.isEmpty()) System.out.println("Team 1 is empty");
        else team1.forEach(d -> System.out.println(d.statusDroid()));

        System.out.println("Team 2:");
        if(team2.isEmpty()) System.out.println("Team 2 is empty");
        else team2.forEach(d -> System.out.println(d.statusDroid()));
    }

    public void destroyDroid(){
        System.out.println("Droid destruction");
        System.out.println("Which team droid to destroy.(1 - team 1, 2 - team 2)");

        int teamChoice = getUserChoice();
        List<Droid> targetTeam = null;
        if(teamChoice == 1) targetTeam = team1;
        else if(teamChoice == 2) targetTeam = team2;
        else System.out.println("Such a team does not exist");

        if (targetTeam.isEmpty()) {
            System.out.println("the team is empty");
            return;
        }
        for (int i = 0; i < targetTeam.size(); i++) System.out.printf("%d : %s", i + 1, targetTeam.get(i).statusDroid());
        System.out.println("Your choice:");
        int choice = getUserChoice();
        if (choice < 1 || choice > targetTeam.size()) {
            System.out.println("Incorrect droid number.");
            return;
        }

        targetTeam.remove(choice - 1);
        System.out.println("The droid was destroyed.");
    }
}
