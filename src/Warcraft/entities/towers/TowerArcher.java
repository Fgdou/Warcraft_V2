package Warcraft.entities.towers;

import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileArrow;
import Warcraft.screen.textures.Texture;
import Warcraft.tools.Vec2;

public abstract class TowerArcher extends Tower {
    public TowerArcher(Vec2 pos, Texture texture, int pv) {
        super(pos, texture, pv, true);
    }

    @Override
    public Projectile getProjectile(Entity e){
        return new ProjectileArrow(this, e, getAttack().getSpeed(), getAttack().getDamage());
    }
}
