package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.towers.Tower;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public abstract class Projectile extends Entity {
    private Entity target;
    private Entity from;
    private Vec2 dir;
    private double scale;
    private int damage;

    public Projectile(Entity from, Entity target, Texture texture, double speed, double scale, int damage) {
        super(from.getPos(), texture);
        this.from = from;
        this.target = target;
        dir = target.getPos().sub(from.getPos()).normalize().mul(speed);
        this.scale = scale;
        this.damage = damage;
    }

    @Override
    public void onTick(Level level){
        addPos(dir);
        if(getPos().distance(target.getPos()) < .5) {
            die();
            if(target instanceof Tower)
                ((Tower)target).hurt(damage);
            else if(target instanceof Monster)
                ((Monster) target).hurt(damage);
        }
    }

    @Override
    public void onDraw(Screen screen){
        getTexture().draw(screen, getPos(), scale, dir.getAngle());
    }
}
