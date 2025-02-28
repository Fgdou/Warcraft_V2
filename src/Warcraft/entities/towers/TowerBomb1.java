package Warcraft.entities.towers;

import Warcraft.entities.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.projectiles.ProjectileBomb;
import Warcraft.screen.textures.TextureImage;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class TowerBomb1 extends TowerBomb {
    public TowerBomb1(Vec2 pos) {
        super(pos, new TextureImage("assets/images/bomb1.png"), 40);
        setAttack(new Attack(this, 10, .15, 1.7, 90));
    }

    @Override
    public Tower copy() {
        return new TowerBomb1(getPos());
    }

    @Override
    public int costUpgrade() {
        return 100;
    }

    @Override
    public Tower getUpgrade() {
        return new TowerBomb2(getPos());
    }

    @Override
    public int getCost() {
        return 60;
    }

    @Override
    public void onInteract(Entity e, Level level){
        if(getAttack() == null)
            return;
        if((e instanceof Monster && !((Monster)e).getFly()) && getAttack().inRange(e) && getAttack().isReady()){
            Projectile p = new ProjectileBomb(this, e, getAttack().getSpeed(), getAttack().getDamage());
            level.addEntity(p);
            getAttack().resetCoolDown();
        }
    }
}
