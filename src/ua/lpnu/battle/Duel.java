package ua.lpnu.battle;
import ua.lpnu.droid.*;

public class Duel {
    private Droid droid1;
    private Droid droid2;
    private final BattleLogger logger;

    public Duel(Droid droid1, Droid droid2, BattleLogger logger){
        this.droid1 = droid1;
        this.droid2 = droid2;
        this.logger = logger;
    }

    public void startFightDuel(){
        if(Math.random() < 0.5){}
        else{
            Droid temp = droid1;
            droid1 = droid2;
            droid2 = temp;
        }
        logger.log("Duel started");
        logger.log(String.format("%s vs %s", droid1.statusDroid(), droid2.statusDroid()));
        int countRound = 0;

        while(droid1.isAlive() && droid2.isAlive()){
            droid1.attackEnemy(droid2, logger);
            if(!droid2.isAlive()) break;
            droid2.attackEnemy(droid1, logger);

            logger.log(String.format("Round %d Results:", ++countRound));
            logger.log(droid1.statusDroid());
            logger.log(droid2.statusDroid());
        }
        determineWinner();
    }

    private void determineWinner(){
        logger.log("The fight is over");
        if(droid1.isAlive())  logger.log(String.format("Winner is %s", droid1.statusDroid()));
        else if (droid2.isAlive())  logger.log(String.format("Winner is %s", droid2.statusDroid()));
        else  logger.log("No winner is draw");
    }
}
