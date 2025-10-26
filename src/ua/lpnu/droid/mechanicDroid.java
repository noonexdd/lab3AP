package ua.lpnu.droid;
import java.util.Comparator;
import java.util.List;

public class mechanicDroid extends Droid {
    int repairPower;
    public mechanicDroid(String name) {
        super(name, 65, 10, 100);
        this.repairPower = 17;
    }

    public void repair(Droid ally){
        if(!isAlive()) return;
        if(!ally.isAlive()) return;
        ally.takeHeal(repairPower);
    }

    @Override
    public void performAction(List<Droid> allies, List<Droid> enemies) {
        Droid mostWoundedAlly = allies.stream().filter(d -> d.isAlive() && d.getCurrentHealth() < d.getMaxHealth())
                .min(Comparator.comparingDouble(d -> (double) d.getCurrentHealth() / d.getMaxHealth() )).orElse(null);

        if(mostWoundedAlly != null) this.repair(mostWoundedAlly);
        else super.performAction(allies, enemies);

    }
}


