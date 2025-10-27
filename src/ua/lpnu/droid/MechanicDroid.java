package ua.lpnu.droid;
import ua.lpnu.battle.BattleLogger;

import java.util.Comparator;
import java.util.List;

public class MechanicDroid extends Droid {
    int repairPower;
    public MechanicDroid(String name) {
        super(name, 65, 10, 100);
        this.repairPower = 17;
    }

    public void repair(Droid ally, BattleLogger logger){
        if(!isAlive()) return;
        if(!ally.isAlive()) return;
        ally.takeHeal(repairPower, logger);
    }

    @Override
    public void performAction(List<Droid> allies, List<Droid> enemies, BattleLogger logger) {
        Droid mostWoundedAlly = allies.stream().filter(d -> d.isAlive() && d.getCurrentHealth() < d.getMaxHealth())
                .min(Comparator.comparingDouble(d -> (double) d.getCurrentHealth() / d.getMaxHealth() )).orElse(null);

        if(mostWoundedAlly != null) this.repair(mostWoundedAlly,  logger);
        else super.performAction(allies, enemies, logger);

    }
}


