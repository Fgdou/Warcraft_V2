package Warcraft.entities.towers;

import Warcraft.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileArrow;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public abstract class Tower extends Entity {
    private int pv;
    private Attack attack;

    public Attack getAttack() {
        return attack;
    }

    public Tower(Vec2 pos, Texture texture, int pv) {
        super(pos, texture);
        this.pv = pv;
    }
    public void setAttack(Attack attack){
        this.attack = attack;
    }

    @Override
    public void onTick(Level level){
        attack.cool();
    }
    @Override
    public void onDraw(Screen screen){
        getTexture().draw(screen, getPos(), 1, 0);
    }
    @Override
    public void onInteract(Entity e, Level level){
        if(attack == null)
            return;
        if(attack.inRange(e) && attack.isReady()){
            Projectile p = new ProjectileArrow(this, e, attack.getSpeed(), attack.getDamage());
            level.addEntity(p);
            attack.reset();
        }
    }
    @Override
    public void die(){
        pv = 0;
    }
    @Override
    public boolean isAlive(){
        return (pv > 0);
    }

    public abstract Tower copy();
    public abstract int costUpgrade();
    public boolean isUpgradable(){
        return costUpgrade() != 0;
    }
    public abstract Tower getUpgrade();
    public abstract int getCost();

    public void hurt(int damage){
        pv -= damage;
    }
}
