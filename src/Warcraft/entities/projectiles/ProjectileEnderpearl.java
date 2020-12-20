package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.level.Level;
import Warcraft.screen.textures.Texture;
import Warcraft.screen.textures.TextureImage;

public class ProjectileEnderpearl extends Projectile {

    public ProjectileEnderpearl(Entity from, Entity target, double speed, int damage) {
        super(from, target, new TextureImage("assets/images/enderpearl.png"), speed, .3, damage);
    }
}
