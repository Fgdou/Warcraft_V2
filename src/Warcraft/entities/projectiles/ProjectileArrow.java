package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureImage;
import Warcraft.level.Level;

public class ProjectileArrow extends Projectile {
    public ProjectileArrow(Entity from, Entity target, double speed, int damage) {
        super(from, target, new TextureImage("assets/images/arrow.png"), speed, .3, damage);
    }

    @Override
    public void onInteract(Entity other, Level level) {}
}
