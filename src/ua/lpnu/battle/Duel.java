package ua.lpnu.battle;
import ua.lpnu.droid.*;

public class Duel {
    private Droid droid1;
    private Droid droid2;

    public Duel(Droid droid1, Droid droid2){
        this.droid1 = droid1;
        this.droid2 = droid2;
    }

    public void startFightDuel(){
        if(Math.random() < 0.5){}
        else{
            Droid temp = droid1;
            droid1 = droid2;
            droid2 = temp;
        }
        System.out.println("Duel started");
        System.out.printf("%s vs %s\n", droid1.statusDroid(), droid2.statusDroid());
        int countRound = 0;

        while(droid1.isAlive() && droid2.isAlive()){
            droid1.attackEnemy(droid2);
            if(!droid2.isAlive()) break;
            droid2.attackEnemy(droid1);

            System.out.printf("Round %d Results:\n", ++countRound);
            System.out.println(droid1.statusDroid());
            System.out.println(droid2.statusDroid());
        }
        determineWinner();
    }

    private void determineWinner(){
        System.out.println("The fight is over");
        if(droid1.isAlive()) System.out.printf("Winner is %s\n", droid1.statusDroid());
        else if (droid2.isAlive()) System.out.printf("Winner is %s\n", droid2.statusDroid());
        else System.out.println("No winner is draw");
    }
}
