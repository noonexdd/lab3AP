package ua.lpnu.battle;
import ua.lpnu.droid.Droid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

public class TeamVSTeam {
    private final List<Droid> team1;
    private final List<Droid> team2;
    private Random random = new Random();

    public TeamVSTeam(List<Droid> team1, List<Droid> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void startFightTVST(){
        while(isTeamAlive(team1) && isTeamAlive(team2)){
            List<Droid> allDroids = new ArrayList<>();
            allDroids.addAll(team1);
            allDroids.addAll(team2);

            for(Droid curentDroid : allDroids){
                if(curentDroid.isAlive()){
                    List<Droid> enemyTeam = team1.contains(curentDroid) ? team1 : team2;
                    Droid target = selectRandomDroid(enemyTeam);
                    if(target != null) curentDroid.attackEnemy(target);

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

    private Droid selectRandomDroid(List<Droid> enemyTeam){
        List<Droid> aliveEnemy = enemyTeam.stream().filter(Droid :: isAlive).collect(Collectors.toList());
        if(aliveEnemy.isEmpty()) return null;

        int index = random.nextInt(aliveEnemy.size());
        return aliveEnemy.get(index);
    }

    private void printResults(){
        System.out.println("Round Results:");
        System.out.println("Team1:");
        team1.forEach(System.out::println);
        System.out.println("Team2:");
        team2.forEach(System.out::println);
    }

    private void determineWinners(){
        System.out.println("The fight is over");
        if(isTeamAlive(team1)) System.out.println("Winner is Team1");
        else if(isTeamAlive(team2)) System.out.println("Winner is Team2");
        else System.out.println("No winner is draw");
    }

}
