package Warcraft.entities.monsters;

import Warcraft.entities.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileEnderpearl;
import Warcraft.screen.textures.TextureAnimated;
import Warcraft.screen.textures.TextureImage;

public class EnderMan extends Monster {
    public EnderMan() {
        super(new TextureAnimated(new TextureImage[]{
                new TextureImage("assets/images/enderman1.png"),
                new TextureImage("assets/images/enderman3.png"),
                new TextureImage("assets/images/enderman2.png"),
                new TextureImage("assets/images/enderman3.png")
        }, 20), 35, .025, .9, false, 7);
        setAttack(new Attack(this, 3, .025, 2, 300));
    }
    @Override
    public Projectile getProjectile(Entity e){
        return new ProjectileEnderpearl(this, e, getAttack().getSpeed(), getAttack().getDamage());
    }
}
