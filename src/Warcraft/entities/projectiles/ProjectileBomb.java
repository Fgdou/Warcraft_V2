package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.screen.textures.TextureImage;

public class ProjectileBomb extends Projectile {
    public ProjectileBomb(Entity from, Entity target, double speed, int damage) {
        super(from, target, new TextureImage("assets/images/bomb.png"), speed, .4, damage);
    }
}
