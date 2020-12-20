package Warcraft.entities.monsters;

import Warcraft.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

import java.awt.*;

public abstract class Monster extends Entity {
    private int pv;
    private final int maxPv;
    private double speed;
    private Vec2 lastPos;
    private double pos;
    private double scale;
    private boolean fly;
    private Attack attack;
    private int coinsKilled;

    public Attack getAttack() {
        return attack;
    }

    public Monster(Texture texture, int pv, double speed, double scale, boolean fly, int coinsKilled) {
        super(new Vec2(), texture);
        this.pv = pv;
        this.maxPv = pv;
        this.speed = speed;
        this.pos = 0;
        this.scale = scale;
        this.fly = fly;
        this.lastPos = getPos();
        this.coinsKilled = coinsKilled;
    }
    public void setAttack(Attack attack){
        this.attack = attack;
    }

    @Override
    public void onTick(Level level) {
        if(attack != null)
            attack.cool();

        pos += speed;

        if(pos >= level.getPath().length()-1) {
            die();
            if(getAttack() == null)
                level.hurt(1);
            else
                level.hurt(getAttack().getDamage());
        }

        lastPos = getPos();

        Vec2 actualPos = level.getPath().get(pos);
        setPos(actualPos);

        if(actualPos.equals(new Vec2(level.getCastle())))
            die();
    }
    @Override
    public void onDraw(Screen screen){
        if(lastPos.x == 0 && lastPos.y == 0)
            return;

        Vec2 delta = getPos().sub(lastPos);
        double angle = 0;

        if(delta.x > 0)
            angle = 90;
        else if(delta.y > 0)
            angle = 180;
        else if(delta.x < 0)
            angle = 270;

        getTexture().draw(screen, getPos(), scale, angle);
        screen.drawProgressBar(getPos().add(new Vec2(0, .4)), new Vec2(.3, .04), (double)pv/maxPv, Color.GREEN);
        if(getAttack() != null)
            screen.drawProgressBar(getPos().add(new Vec2(0, .35)), new Vec2(.2, .02), (double)attack.getCooldown() / attack.getCooldownTime(), Color.RED);
    }
    @Override
    public void onInteract(Entity e, Level level){
        if(attack == null)
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
    }
    @Override
    public boolean isAlive(){
        return (pv > 0);
    }

    public void hurt(int damage){
        pv -= damage;
    }

    public boolean getFly(){
        return fly;
    }
    public int getCoinsKilled(){
        return coinsKilled;
    }

    public static Monster getByName(String name){
        switch (name){
            case "Zombie":
                return new Zombie();
            case "Bat":
                return new Bat();
            case "IronGolem":
                return new IronGolem();
            case "EnderMan":
                return new EnderMan();
            default:
                return null;
        }
    }
    public Projectile getProjectile(Entity e){
        return null;
    }
}
