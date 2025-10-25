package ua.lpnu.droid;

public class armoredDroid extends Droid{
    double absorptionDamage;
    public armoredDroid(String name){
        super(name, 120, 13, 100);
        this.absorptionDamage = 0.3;
    }

    @Override
    public void takeDamage(int damage, Droid attacker){
        if(!isAlive()) return;
        int absorbedDamage = (int)(damage * this.absorptionDamage);
        int finalDamage = damage - absorbedDamage;

        super.takeDamage(finalDamage, attacker);
    }
}
