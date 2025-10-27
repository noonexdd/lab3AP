package ua.lpnu.droid;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import ua.lpnu.battle.BattleLogger;

public abstract class Droid implements Cloneable {
    protected String name;
    protected int currentHealth;
    protected int maxHealth;
    protected int damage;
    protected int energyShield;

    public Droid(String name, int health, int damage, int energyShield) {
        this.name = name;
        this.maxHealth = health;
        this.currentHealth = health;
        this.damage = damage;
        this.energyShield = energyShield;
    }

    public void takeDamage(int damage, Droid attacker, BattleLogger logger) {
        if(!isAlive()) return;

        if(this.energyShield > 0){
            this.energyShield -= damage;
            logger.log(String.format("The droid: %s energy shield damaged on %d ", name, damage));
            if(this.energyShield <= 0){
                this.energyShield = 0;
                logger.log(String.format("The droid: %s energy shield was destroyed.. ", name));
            }
        }
        else {
            this.currentHealth -= damage;
            logger.log(String.format("The droid: %s received %d damage points. ", name, damage));
            if (!isAlive()) deathDroid(logger);
        }
    }

    public void takeDamageShieldHealth(int damage, Droid attacker,  BattleLogger logger) {
        if(!isAlive()) return;
        int shieldDamage = damage / 2;
        int healthDamage = damage - shieldDamage;

        this.energyShield -= shieldDamage;
        logger.log(String.format("The droid: %s energy shield damaged on %d ", name, shieldDamage));
        if(this.energyShield <= 0){
            this.energyShield = 0;
            logger.log(String.format("The droid: %s energy shield was destroyed.. ", name));
        }

        this.currentHealth -= healthDamage;
        logger.log(String.format("The droid: %s received %d damage points. ", name, healthDamage));
        if (!isAlive()) deathDroid(logger);
    }

    public void attackEnemy(Droid enemy,  BattleLogger logger) {
        if(!isAlive()) return;
        if(!enemy.isAlive()) return;
        enemy.takeDamage(this.damage,this,  logger );
        logger.log(String.format("The droid: %s hit the droid: %s.", this.name, enemy.name));
    }

    public void takeHeal(int health, BattleLogger logger) {
        if(!isAlive()) return;
        int beforeHeal = this.currentHealth;

        this.currentHealth += health;
        if(this.currentHealth > maxHealth) this.currentHealth = this.maxHealth;

        int restoredHealth = this.currentHealth - beforeHeal;
        if(restoredHealth > 0)  logger.log(String.format("The droid: %s was healed by %d HP points.",this.name, restoredHealth));

    }

    public void deathDroid(BattleLogger logger) {
        this.currentHealth = 0;
        logger.log(String.format("Droid: %s was death.", this.name));
    }

    public String statusDroid(){
        return String.format("Droid: %s - %s HP:%d/%d DMG:%d ES:%d", this.name, (isAlive() ? "Alive" : "Dead"),
                this.currentHealth, this.maxHealth, this.damage, this.energyShield);
    }

    public boolean isAlive() {return currentHealth > 0;}

    public void performAction(List<Droid> allies, List<Droid> enemies,  BattleLogger logger) {
        Droid target = selectRandomDroid(enemies);
        if(target != null) this.attackEnemy(target, logger);
    }

    private final Random random = new Random();

    private Droid selectRandomDroid(List<Droid> team){
        List<Droid> aliveDroids = team.stream().filter(Droid :: isAlive).collect(Collectors.toList());
        if(aliveDroids.isEmpty()) return null;

        return aliveDroids.get(random.nextInt(aliveDroids.size()));
    }

    public int getCurrentHealth() {return currentHealth;}
    public int getMaxHealth() {return maxHealth;}

    @Override
    public Droid clone() {
        try {
            return (Droid) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

