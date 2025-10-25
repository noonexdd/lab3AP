package ua.lpnu.droid;

public class mechanicDroid extends Droid {
    int repairPower;
    public mechanicDroid(String name) {
        super(name, 65, 10, 100);
        this.repairPower = 23;
    }

    public void repair(Droid ally){
        if(!isAlive()) return;
        if(!ally.isAlive()) return;
        ally.takeHeal(repairPower);
    }
}


