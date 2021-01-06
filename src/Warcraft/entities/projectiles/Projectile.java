package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.towers.Tower;
import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public abstract class Projectile extends Entity {
    public static final double RANGE = .5;

    private final Entity target;
    private final Vec2 dir;
    private final double scale;
    private final int damage;

    /**
     * @param from      The entity which send the projectile
     * @param target    The entity targeted
     * @param texture   The texture of the projectile
     * @param speed     The speed in tile per tick
     * @param scale     The scale where 1 is the full tile
     * @param damage    The damage to hurt the target
     */
    public Projectile(Entity from, Entity target, Texture texture, double speed, double scale, int damage) {
        super(from.getPos(), texture);
        this.target = target;
        dir = target.getPos().sub(from.getPos()).normalize().mul(speed);
        this.scale = scale;
        this.damage = damage;
    }

    @Override
    public void onTick(Level level){
        addPos(dir);

        //Hurt if the distance is small
        if(getPos().distance(target.getPos()) < RANGE) {
            die();
            if(target instanceof Tower)
                ((Tower)target).hurt(damage);
            else if(target instanceof Monster) {
                ((Monster) target).hurt(damage);
                if(!target.isAlive())
                    level.addCoins(((Monster) target).getCoinsKilled());
            }
        }
    }

    @Override
    public void onDraw(Screen screen){
        getTexture().draw(screen, getPos(), scale, dir.getAngle());
    }

    @Override
    public void onInteract(Entity e, Level l){}
}
