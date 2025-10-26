package ua.lpnu.droid;

public class shooterDroid extends Droid{
    public shooterDroid(String name){super(name, 60, 30, 100);}

    public void penetratingShot(Droid target){
        System.out.printf("Droid: %s shoots with a penetrating shot\n", this.name);
        target.takeDamageShieldHealth(this.damage,this);
        countShot = 0;
    }

    int countShot = 0;

    @Override
    public void attackEnemy(Droid enemy){
        if(!isAlive()) return;
        if(!enemy.isAlive()) return;

        if(countShot == 2) penetratingShot(enemy);
        else{
            countShot++;
            super.attackEnemy(enemy);
        }
    }
}
