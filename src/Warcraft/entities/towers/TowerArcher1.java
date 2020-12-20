package Warcraft.entities.towers;

import Warcraft.Attack;
import Warcraft.entities.Entity;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.screen.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerArcher1 extends TowerArcher {
    public TowerArcher1(Vec2 pos) {
        super(pos, new TextureImage("assets/images/archer1.png"), 70);
        setAttack(new Attack(this, 2, .2, 2, 60));
    }

    @Override
    public Tower copy(){
        return new TowerArcher1(getPos());
    }
    @Override
    public int costUpgrade(){
        return 40;
    }
    @Override
    public Tower getUpgrade(){
        return new TowerArcher2(getPos());
    }
    @Override
    public int getCost(){
        return 50;
    }
}
