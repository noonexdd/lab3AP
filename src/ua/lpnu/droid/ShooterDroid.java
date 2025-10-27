package ua.lpnu.droid;

import ua.lpnu.battle.BattleLogger;

public class ShooterDroid extends Droid{
    public ShooterDroid(String name){super(name, 60, 30, 100);}

    public void penetratingShot(Droid target, BattleLogger logger){
        logger.log(String.format("Droid: %s shoots with a penetrating shot", this.name));
        target.takeDamageShieldHealth(this.damage,this, logger);
        countShot = 0;
    }

    int countShot = 0;

    @Override
    public void attackEnemy(Droid enemy, BattleLogger logger){
        if(!isAlive()) return;
        if(!enemy.isAlive()) return;

        if(countShot == 2) penetratingShot(enemy, logger);
        else{
            countShot++;
            super.attackEnemy(enemy, logger);
        }
    }
}
