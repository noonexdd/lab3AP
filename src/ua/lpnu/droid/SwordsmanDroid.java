package ua.lpnu.droid;

import ua.lpnu.battle.BattleLogger;

public class SwordsmanDroid extends Droid{
    private boolean isCounterAttackReady = false;
    private Droid lastAttacker = null;
    public SwordsmanDroid(String name){super(name, 100, 20, 110);}

    @Override
    public void takeDamage(int damage, Droid attacker, BattleLogger logger) {
        if(!isAlive()) return;
        int parryingChance = (int)(Math.random()*100);

        if(parryingChance > 70){
            logger.log(String.format("Droid: %s parried the attack.", this.name));
            isCounterAttackReady = true;
            lastAttacker = attacker;
        } else super.takeDamage(damage,  attacker, logger);
    }

    @Override
    public void attackEnemy(Droid enemy,  BattleLogger logger) {
        if(!isAlive()) return;

        if(this.isCounterAttackReady){
            int counterDamage =this.damage * 2;
            if(!lastAttacker.isAlive()){
                super.attackEnemy(enemy,  logger);
            } else {
                logger.log(String.format("Droid: %s launches a counterattack on droid: %s.", this.name, this.lastAttacker.name));
                this.lastAttacker.takeDamage(counterDamage, this, logger);
            }
            isCounterAttackReady = false;
            lastAttacker = null;
        } else super.attackEnemy(enemy,  logger);
    }
}
