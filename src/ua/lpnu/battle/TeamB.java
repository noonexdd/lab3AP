package ua.lpnu.battle;
import ua.lpnu.droid.*;
import java.util.ArrayList;
import java.util.List;

public class TeamB {
    private final List<Droid> team1;
    private final List<Droid> team2;

    public TeamB(List<Droid> team1, List<Droid> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void startFightTeam(){
        while(isTeamAlive(team1) && isTeamAlive(team2)){
            List<Droid> allDroids = new ArrayList<>();
            allDroids.addAll(team1);
            allDroids.addAll(team2);

            for(Droid curentDroid : allDroids){
                if(curentDroid.isAlive()){
                    List<Droid> enemyTeam = team1.contains(curentDroid) ? team2 : team1;
                    List<Droid> allyTeam = team1.contains(curentDroid) ? team1 : team2;

                    curentDroid.performAction(allyTeam,enemyTeam);
                    if(!isTeamAlive(enemyTeam)) break;
                }
            }
            printResults();
        }
        determineWinners();
    }

    private boolean isTeamAlive(List<Droid> team){
        for(Droid curentDroid : team) if (curentDroid.isAlive()) return true;
        return false;
    }

    private void printResults(){
        System.out.println("Round Results:");
        System.out.println("Team1:");
        team1.forEach(d -> System.out.println(d.statusDroid()));
        System.out.println("Team2:");
        team2.forEach(d -> System.out.println(d.statusDroid()));
    }

    private void determineWinners(){
        System.out.println("The fight is over");
        if(isTeamAlive(team1)) System.out.println("Winner is Team1");
        else if(isTeamAlive(team2)) System.out.println("Winner is Team2");
        else System.out.println("No winner is draw");
    }

}
