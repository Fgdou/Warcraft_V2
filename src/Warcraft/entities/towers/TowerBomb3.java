package Warcraft.entities.towers;

import Warcraft.entities.Attack;
import Warcraft.screen.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerBomb3 extends TowerBomb {
    public TowerBomb3(Vec2 pos) {
        super(pos, new TextureImage("assets/images/bomb3.png"), 400);
        setAttack(new Attack(this, 30, .15, 2.5, 60));
    }

    @Override
    public Tower copy() {
        return new TowerBomb3(getPos());
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
