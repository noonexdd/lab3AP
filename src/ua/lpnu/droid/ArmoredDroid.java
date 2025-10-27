package ua.lpnu.droid;

import ua.lpnu.battle.BattleLogger;

public class ArmoredDroid extends Droid{
    double absorptionDamage;
    public ArmoredDroid(String name){
        super(name, 120, 13, 100);
        this.absorptionDamage = 0.3;
    }

    @Override
    public void takeDamage(int damage, Droid attacker, BattleLogger logger){
        if(!isAlive()) return;
        int absorbedDamage = (int)(damage * this.absorptionDamage);
        int finalDamage = damage - absorbedDamage;

        super.takeDamage(finalDamage, attacker, logger);
    }
}
