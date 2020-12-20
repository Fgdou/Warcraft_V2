package Warcraft.entities.towers;

import Warcraft.entities.Attack;
import Warcraft.screen.textures.Texture;
import Warcraft.screen.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerArcher3 extends TowerArcher {
    public TowerArcher3(Vec2 pos) {
        super(pos, new TextureImage("assets/images/archer3.png"), 500);
        setAttack(new Attack(this, 10, .5, 3, 30));
    }

    @Override
    public Tower copy() {
        return new TowerArcher3(getPos());
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
