package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.screen.textures.TextureImage;

public class ProjectileArrow extends Projectile {
    public ProjectileArrow(Entity from, Entity target, double speed, int damage) {
        super(from, target, new TextureImage("assets/images/arrow.png"), speed, .3, damage);
    }
}
