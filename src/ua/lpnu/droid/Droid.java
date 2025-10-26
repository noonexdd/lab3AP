package ua.lpnu.droid;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    public void takeDamage(int damage, Droid attacker) {
        if(!isAlive()) return;

        if(this.energyShield > 0){
            this.energyShield -= damage;
            System.out.printf("The droid: %s energy shield damaged on %d\n ", name, damage);
            if(this.energyShield <= 0){
                this.energyShield = 0;
                System.out.printf("The droid: %s energy shield was destroyed..\n ", name);
            }
        }
        else {
            this.currentHealth -= damage;
            System.out.printf("The droid: %s received %d damage points.\n ", name, damage);
            if (!isAlive()) deathDroid();
        }
    }

    public void takeDamageShieldHealth(int damage, Droid attacker) {
        if(!isAlive()) return;
        int shieldDamage = damage / 2;
        int healthDamage = damage - shieldDamage;

        this.energyShield -= shieldDamage;
        System.out.printf("The droid: %s energy shield damaged on %d\n ", name, shieldDamage);
        if(this.energyShield <= 0){
            this.energyShield = 0;
            System.out.printf("The droid: %s energy shield was destroyed..\n ", name);
        }

        this.currentHealth -= healthDamage;
        System.out.printf("The droid: %s received %d damage points.\n ", name, damage);
        if (!isAlive()) deathDroid();
    }

    public void attackEnemy(Droid enemy) {
        if(!isAlive()) return;
        if(!enemy.isAlive()) return;
        enemy.takeDamage(this.damage,this );
        System.out.printf("The droid: %s hit the droid: %s.\n", this.name, enemy.name);
    }

    public void takeHeal(int health) {
        if(!isAlive()) return;
        int beforeHeal = this.currentHealth;

        this.currentHealth += health;
        if(this.currentHealth > maxHealth) this.currentHealth = this.maxHealth;

        int restoredHealth = this.currentHealth - beforeHeal;
        if(restoredHealth > 0) System.out.printf("The droid: %s was healed by %d HP points.\n",this.name, restoredHealth);

    }

    public void deathDroid() {
        this.currentHealth = 0;
        System.out.printf("Droid: %s was death.\n", this.name);
    }

    public String statusDroid(){
        return String.format("Droid: %s - %s HP:%d/%d DMG:%d ES:%d\n", this.name, (isAlive() ? "Alive" : "Dead"),
                this.currentHealth, this.maxHealth, this.damage, this.energyShield);
    }

    public boolean isAlive() {return currentHealth > 0;}

    public void performAction(List<Droid> allies, List<Droid> enemies) {
        Droid target = selectRandomDroid(enemies);
        if(target != null) this.attackEnemy(target);
    }

    private Droid selectRandomDroid(List<Droid> team){
        List<Droid> aliveDroids = team.stream().filter(Droid :: isAlive).collect(Collectors.toList());
        if(aliveDroids.isEmpty()) return null;

        return aliveDroids.get(new Random().nextInt(aliveDroids.size()));
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

