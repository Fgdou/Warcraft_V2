package Warcraft.entities.towers;

import Warcraft.entities.Attack;
import Warcraft.screen.textures.Texture;
import Warcraft.screen.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerBomb2 extends TowerBomb {
    public TowerBomb2(Vec2 pos) {
        super(pos, new TextureImage("assets/images/bomb2.png"), 100);
        setAttack(new Attack(this, 15, .15, 2, 70));
    }

    @Override
    public Tower copy() {
        return new TowerBomb2(getPos());
    }

    @Override
    public int costUpgrade() {
        return 500;
    }

    @Override
    public Tower getUpgrade() {
        return new TowerBomb3(getPos());
    }

    @Override
    public int getCost() {
        return 0;
    }
}
