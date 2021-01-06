package Warcraft.entities;

/**
 * This class is used to set infos on monsters or towers. It gives the details of an attack.
 */
public class Attack {
    private final int damage;
    private final double speed;
    private final double range;
    private final Entity parent;

    private final int coolDownTime;
    private int coolDownActual;

    /**
     * @param parent          The instance of the parent
     * @param damage        The damage that the parent will give to a target
     * @param speed         The speed of the projectile
     * @param range         The range which the parent can detect enemy
     * @param coolDownTime  The time before shooting an other projectile
     */
    public Attack(Entity parent, int damage, double speed, double range, int coolDownTime){
        this.parent = parent;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.coolDownTime = coolDownTime;
        this.coolDownActual = (int)(Math.random()*coolDownTime);
    }

    /**
     * @return if can shoot an other projectile
     */
    public boolean isReady(){
        return coolDownActual == coolDownTime;
    }

    /**
     * @param e  The target
     * @return   If the target is in range
     */
    public boolean inRange(Entity e){
        return parent.getPos().distance(e.getPos()) < range;
    }

    /**
     * @return the damage of the attack
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return the speed of the projectile
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return the range of the parent to detect enemy
     */
    public double getRange() {
        return range;
    }

    /**
     * @return the range of the parent
     */
    public Entity getParent() {
        return parent;
    }

    /**
     * Equivalent to tick, update the cooldown
     */
    public void cool() {
        if(coolDownActual >= coolDownTime)
            return;
        coolDownActual++;
    }

    /**
     * Reset the cooldown to 0. Then the parent cannot shoot and will wait the cool down time.
     */
    public void resetCoolDown(){
        coolDownActual = 0;
    }

    /**
     * @return the actual cool down
     */
    public int getCooldown() {
        return coolDownActual;
    }

    /**
     * @return the time when the cool down is ready
     */
    public int getCooldownTime() {
        return coolDownTime;
    }
}
