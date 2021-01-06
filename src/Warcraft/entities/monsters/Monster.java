package Warcraft.entities.monsters;

import Warcraft.entities.Attack;
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
    private double timeStart;
    private double scale;
    private boolean fly;
    private Attack attack;
    private int coinsKilled;

    /**
     * @return the attack of the monster
     */
    public Attack getAttack() {
        return attack;
    }

    /**
     * @param texture       Texture of the monster
     * @param pv            Hearts of the monster
     * @param speed         Speed in tile per ticks
     * @param scale         Scale where 1 is the full tile
     * @param fly           If the monster is flying
     * @param coinsKilled   The coins to give to the player when killed
     */
    public Monster(Texture texture, int pv, double speed, double scale, boolean fly, int coinsKilled) {
        super(new Vec2(), texture);
        this.pv = pv;
        this.maxPv = pv;
        this.speed = speed;
        this.timeStart = 0;
        this.scale = scale;
        this.fly = fly;
        this.lastPos = getPos();
        this.coinsKilled = coinsKilled;
    }

    /**
     * Set the attack of the monster. Has to be called in the constructor
     * @param attack
     */
    public void setAttack(Attack attack){
        this.attack = attack;
    }

    @Override
    public void onTick(Level level) {
        if(attack != null)
            attack.cool();

        timeStart += speed;

        //The monster touch the castle
        if(timeStart >= level.getPath().length()-1) {
            die();
            if(getAttack() == null)
                level.hurt(1);
            else
                level.hurt(getAttack().getDamage());
        }

        lastPos = getPos();

        //Get position by the path
        Vec2 actualPos = level.getPath().get(timeStart);
        setPos(actualPos);
    }
    @Override
    public void onDraw(Screen screen){
        if(lastPos.x == 0 && lastPos.y == 0)
            return;

        //Calc the angle
        Vec2 deltaPos = getPos().sub(lastPos);
        double angle = 0;

        if(deltaPos.x > 0)
            angle = 90;
        else if(deltaPos.y > 0)
            angle = 180;
        else if(deltaPos.x < 0)
            angle = 270;

        //Draw with the angle
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
            attack.resetCoolDown();
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

    /**
     * Give damage to the monster
     * @param damage number of heart
     */
    public void hurt(int damage){
        pv -= damage;
    }

    /**
     * @return if the monster is flying
     */
    public boolean getFly(){
        return fly;
    }

    /**
     * @return the coins for the player when the monster is killed
     */
    public int getCoinsKilled(){
        return coinsKilled;
    }

    /**
     * @param name  of the monster
     * @return      A new instance of the monster
     */
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
            case "Llama":
                return new Llama();
            case "Phamtom":
                return new Phantom();
            default:
                return null;
        }
    }

    /**
     * @param e The entity to target
     * @return  The projectile if the monster has an attack
     */
    public Projectile getProjectile(Entity e){
        return null;
    }
}
