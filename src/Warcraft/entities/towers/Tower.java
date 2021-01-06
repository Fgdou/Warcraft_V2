package Warcraft.entities.towers;

import Warcraft.entities.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

import java.awt.*;

public abstract class Tower extends Entity {
    private int pv;
    private final int maxPv;
    private Attack attack;
    private boolean flyProjectile;

    private int cntTps;


    /**
     * @param pos           The position of the tower
     * @param texture       The texture of the tower
     * @param pv            The hearts
     * @param flyProjectile If the tower can attack flying monster
     */
    public Tower(Vec2 pos, Texture texture, int pv, boolean flyProjectile) {
        super(pos, texture);
        this.pv = pv;
        this.maxPv = pv;
        this.flyProjectile = flyProjectile;
        this.cntTps = 0;
    }

    /**
     * @return the attack of the tower
     */
    public Attack getAttack() {
        return attack;
    }
    /**
     * Set the attack of the tower. Has to be called in the constructor
     * @param attack
     */
    public void setAttack(Attack attack){
        this.attack = attack;
    }

    @Override
    public void onTick(Level level){
        //Shoot on interval
        cntTps++;
        if(cntTps > 300 || level.getWaves().isWaitingNextWave() && cntTps >= (60*10)/maxPv){
            if(pv < maxPv)
                pv += 1;
            cntTps = 0;
        }
        attack.cool();
    }
    @Override
    public void onDraw(Screen screen){
        getTexture().draw(screen, getPos(), 1, 0);
        screen.drawProgressBar(getPos().add(new Vec2(0, -.4)), new Vec2(.4, .04), (double)pv/maxPv, Color.GREEN);
        screen.drawProgressBar(getPos().add(new Vec2(0, .4)), new Vec2(.2, .02), (double)attack.getCooldown() / attack.getCooldownTime(), Color.RED);
    }
    @Override
    public void onInteract(Entity e, Level level){
        if(attack == null)
            return;
        if(!flyProjectile && e instanceof Monster && ((Monster) e).getFly())
            return;
        if(attack.inRange(e) && attack.isReady()){
            Projectile p = getProjectile(e);
            level.addEntity(p);
            attack.resetCoolDown();
        }
    }
    @Override
    public void die(){
        pv = 0;
        super.die();
    }
    @Override
    public boolean isAlive(){
        return (pv > 0) && super.isAlive();
    }

    /**
     * @return A copy of this instance
     */
    public abstract Tower copy();

    /**
     * @return the cost to upgrade the tower
     * @note 0 mean the tower cannot be upgrade
     */
    public abstract int costUpgrade();

    /**
     * @return if the tower can be upgrade
     */
    public boolean isUpgradable(){
        return costUpgrade() != 0;
    }

    /**
     * @return the instance of the upgraded tower
     */
    public abstract Tower getUpgrade();

    /**
     * @return the cost of this tower
     */
    public abstract int getCost();

    /**
     * @param target  The entity to target
     * @return        The projectile to shoot the target
     */
    public abstract Projectile getProjectile(Entity target);

    /**
     * Decrease the life of the tower
     * @param damage
     */
    public void hurt(int damage){
        pv -= damage;
    }
}
