package Warcraft.entities.towers;

import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;
import Warcraft.tools.Vec2;

public class TowerArcher1 extends Tower {
    public TowerArcher1(Vec2 pos) {
        super(pos, new TextureImage("assets/images/archer1.png"), 10);
    }
}
