package Warcraft.entities.towers;

import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileBomb;
import Warcraft.screen.textures.Texture;
import Warcraft.tools.Vec2;

public abstract class TowerBomb extends Tower {
    public TowerBomb(Vec2 pos, Texture texture, int pv) {
        super(pos, texture, pv, false);
    }
    @Override
    public Projectile getProjectile(Entity e){
        return new ProjectileBomb(this, e, getAttack().getSpeed(), getAttack().getDamage());
    }
}
