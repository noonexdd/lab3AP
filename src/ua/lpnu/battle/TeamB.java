package ua.lpnu.battle;
import ua.lpnu.droid.*;
import java.util.ArrayList;
import java.util.List;

public class TeamB {
    private final List<Droid> team1;
    private final List<Droid> team2;
    private final BattleLogger logger;

    public TeamB(List<Droid> team1, List<Droid> team2,  BattleLogger logger) {
        this.team1 = team1;
        this.team2 = team2;
        this.logger = logger;
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

                    curentDroid.performAction(allyTeam, enemyTeam, logger);
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
        logger.log("Round Results:");
        logger.log("Team1:");
        team1.forEach(d ->  logger.log(d.statusDroid()));
        logger.log("Team2:");
        team2.forEach(d ->  logger.log(d.statusDroid()));
    }

    private void determineWinners(){
        logger.log("The fight is over");
        if(isTeamAlive(team1))  logger.log("Winner is Team1");
        else if(isTeamAlive(team2))  logger.log("Winner is Team2");
        else  logger.log("No winner is draw");
    }
}
