package Warcraft.entities.monsters;

import Warcraft.entities.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileLlama;
import Warcraft.screen.textures.Texture;
import Warcraft.screen.textures.TextureAnimated;
import Warcraft.screen.textures.TextureImage;

public class Phantom extends Monster {
    public Phantom() {
        super(new TextureAnimated(new TextureImage[]{
                new TextureImage("assets/images/phantom1.png"),
                new TextureImage("assets/images/phantom2.png")
        }, 30), 10, .025, .6, true, 5);
        setAttack(new Attack(this, 1, .05, 1.5, 120));
    }
    @Override
    public Projectile getProjectile(Entity e){
        return new ProjectileLlama(this, e, getAttack().getSpeed(), getAttack().getDamage());
    }
}
