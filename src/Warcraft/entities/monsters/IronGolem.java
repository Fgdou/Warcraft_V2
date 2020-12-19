package Warcraft.entities.monsters;

import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;

public class IronGolem extends Monster {
    public IronGolem() {
        super(new TextureAnimated(new TextureImage[]{
                new TextureImage("assets/images/ironGolem1.png"),
                new TextureImage("assets/images/ironGolem3.png"),
                new TextureImage("assets/images/ironGolem2.png"),
                new TextureImage("assets/images/ironGolem3.png")
        },90), 100, .01, 1.1, false, 80);
    }
}
