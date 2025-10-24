package ua.lpnu.Droid;

public class swordsmanDroid extends Droid{
    private boolean isCounterAttackReady = false;
    private Droid lastAttacker = null;
    public swordsmanDroid(String name){super(name, 100, 20, 110);}

    @Override
    public void takeDamage(int damage, Droid attacker){
        if(!isAlive()) return;
        int parryingChance = (int)(Math.random()*100);

        if(parryingChance > 70){
            System.out.printf("Droid: %s parried the attack.\n", this.name);
            isCounterAttackReady = true;
            lastAttacker = attacker;
        } else{super.takeDamage(damage,  attacker);}
    }

    @Override
    public void attackEnemy(Droid enemy){
        if(!isAlive()) return;

        if(this.isCounterAttackReady){
            int counterDamage =this.damage * 2;
            if(!lastAttacker.isAlive()){
                super.attackEnemy(enemy);
            } else {
                System.out.printf("Droid: %s launches a counterattack on droid: %s.\n", this.name, this.lastAttacker.name);
                this.lastAttacker.takeDamage(counterDamage, this);
            }
            isCounterAttackReady = false;
            lastAttacker = null;
        } else{super.attackEnemy(enemy);}
    }
}
