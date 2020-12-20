package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.screen.textures.Texture;
import Warcraft.screen.textures.TextureImage;

public class ProjectileLlama extends Projectile {
    public ProjectileLlama(Entity from, Entity target, double speed, int damage) {
        super(from, target, new TextureImage("assets/images/flake.png"), speed, .3, damage);
    }
}
