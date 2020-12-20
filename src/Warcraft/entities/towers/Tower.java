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
    private boolean attackFly;

    private int cntTps;

    public Attack getAttack() {
        return attack;
    }

    public Tower(Vec2 pos, Texture texture, int pv, boolean attackFly) {
        super(pos, texture);
        this.pv = pv;
        this.maxPv = pv;
        this.attackFly = attackFly;
        this.cntTps = 0;
    }
    public void setAttack(Attack attack){
        this.attack = attack;
    }

    @Override
    public void onTick(Level level){
        cntTps++;
        if(cntTps > 300 || level.getWaves().isWatingNextWave() && cntTps >= (60*10)/maxPv){
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
        if(!attackFly && e instanceof Monster && ((Monster) e).getFly())
            return;
        if(attack.inRange(e) && attack.isReady()){
            Projectile p = getProjectile(e);
            level.addEntity(p);
            attack.reset();
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

    public abstract Tower copy();
    public abstract int costUpgrade();
    public boolean isUpgradable(){
        return costUpgrade() != 0;
    }
    public abstract Tower getUpgrade();
    public abstract int getCost();
    public abstract Projectile getProjectile(Entity target);

    public void hurt(int damage){
        pv -= damage;
    }
}
