package Warcraft.entities.towers;

import Warcraft.entities.Attack;
import Warcraft.screen.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerArcher2 extends TowerArcher {
    public TowerArcher2(Vec2 pos) {
        super(pos, new TextureImage("assets/images/archer2.png"), 100);
        setAttack(new Attack(this, 5, .4, 2.4, 50));
    }

    @Override
    public Tower copy() {
        return new TowerArcher2(getPos());
    }

    @Override
    public int costUpgrade() {
        return 250;
    }

    @Override
    public Tower getUpgrade() {
        return new TowerArcher3(getPos());
    }

    @Override
    public int getCost() {
        return 0;
    }
}
