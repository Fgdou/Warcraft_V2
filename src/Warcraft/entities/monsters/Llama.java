package Warcraft.entities.monsters;

import Warcraft.entities.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileLlama;
import Warcraft.screen.textures.TextureImage;

public class Llama extends Monster {
    public Llama() {
        super(new TextureImage("assets/images/llama.png"), 250, .01, .7, false, 20);
        setAttack(new Attack(this, 10, .015, 1.3, 180));
    }

    @Override
    public Projectile getProjectile(Entity e){
        return new ProjectileLlama(this, e, getAttack().getSpeed(), getAttack().getDamage());
    }
}
