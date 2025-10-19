package ua.lpnu.Droid;

abstract class Droid {
    protected String name;
    protected int currentHealth;
    protected int maxHealth;
    protected int damage;
    protected int energyShield;
    protected boolean isAlive = true;

    public Droid(String name, int health, int damage, int energyShield) {
        this.name = name;
        this.maxHealth = health;
        this.currentHealth = health;
        this.damage = damage;
        this.energyShield = energyShield;
    }

    public void takeDamage(int damage) {
        if(!isAlive) return;

        if(this.energyShield >= 0){
            this.energyShield -= damage;
            System.out.printf("The droid: %s energy shield damaged..\n ", name);
            if(this.energyShield <= 0){
                this.energyShield = 0;
                System.out.printf("The droid: %s energy shield was destroyed..\n ", name);
            }
        }
        else {
            this.currentHealth -= damage;
            if (currentHealth <= 0) deathDroid();
            System.out.printf("The droid: %s received %d damage points.\n ", name, damage);
        }
    }

    public void attackEnemy(Droid otherDroid) {
        if(!isAlive) return;

        System.out.printf("The droid: %s hit the droid: %s.\n", this.name, otherDroid.name);
        otherDroid.takeDamage(this.damage);
    }

    public void takeHeal(int health) {
        if(!isAlive) return;
        if (maxHealth - this.currentHealth <= health )
        {this.currentHealth += health;}
        System.out.printf("The droid: %s was healed by %d HP points.\n",this.name, health);
    }

    public void deathDroid() {
        isAlive = false;
        this.currentHealth = 0;
        System.out.printf("Droid: %s was death.\n", this.name);
    }

    public String statusDroid(){
        return String.format("Droid: %s - %s HP:%d/%d DMG:%d\n", this.name, (this.isAlive ? "Alive" : "Dead"),this.currentHealth, this.maxHealth, this.damage);
    }
}

