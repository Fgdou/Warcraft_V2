package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public abstract class Projectile extends Entity {
    private Entity target;
    private Entity from;
    private Vec2 dir;
    private double scale;

    public Projectile(Entity from, Entity target, Texture texture, double speed, double scale) {
        super(from.getPos(), texture);
        this.from = from;
        this.target = target;
        dir = target.getPos().sub(from.getPos()).normalize().mul(speed);
        this.scale = scale;
    }

    @Override
    public void onTick(Level level){
        addPos(dir);
        if(getPos().equals(target.getPos()))
            die();
    }

    @Override
    public void onDraw(Screen screen){
        getTexture().draw(screen, getPos(), scale, dir.getAngle());
    }
}
