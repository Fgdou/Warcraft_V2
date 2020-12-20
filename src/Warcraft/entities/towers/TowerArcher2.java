package Warcraft.entities.towers;

import Warcraft.Attack;
import Warcraft.screen.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerArcher2 extends TowerArcher {
    public TowerArcher2(Vec2 pos) {
        super(pos, new TextureImage("assets/images/archer2.png"), 250);
        setAttack(new Attack(this, 5, .4, 2.4, 50));
    }

    @Override
    public Tower copy() {
        return new TowerArcher2(getPos());
    }

    @Override
    public int costUpgrade() {
        return 0;
    }

    @Override
    public Tower getUpgrade() {
        return null;
    }

    @Override
    public int getCost() {
        return 0;
    }
}
