package Warcraft;

import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;

public class Attack {
    private int damage;
    private double speed;
    private double range;
    private Entity from;

    private final int coolDownTime;
    private int coolDown;

    public Attack(Entity from, int damage, double speed, double range, int coolDownTime){
        this.from = from;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.coolDownTime = coolDownTime;
        this.coolDown = 0;
    }

    public boolean isReady(){
        return coolDown == coolDownTime;
    }
    public boolean inRange(Entity e){
        return from.getPos().distance(e.getPos()) < range;
    }

    public int getDamage() {
        return damage;
    }
    public double getSpeed() {
        return speed;
    }
    public double getRange() {
        return range;
    }
    public Entity getFrom() {
        return from;
    }

    public void cool() {
        if(coolDown >= coolDownTime)
            return;
        coolDown++;
    }
    public void reset(){
        coolDown = 0;
    }

    public int getCooldown() {
        return coolDown;
    }
    public int getCooldownTime() {
        return coolDownTime;
    }
}
