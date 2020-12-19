package Warcraft.entities.projectiles;

import Warcraft.entities.Entity;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureImage;
import Warcraft.level.Level;

public class ProjectileBomb extends Projectile {
    public ProjectileBomb(Entity from, Entity target, double speed, int damage) {
        super(from, target, new TextureImage("assets/images/bomb.png"), speed, .4, damage);
    }

    @Override
    public void onInteract(Entity other, Level level) {
    }
}
