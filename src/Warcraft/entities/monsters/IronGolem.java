package Warcraft.entities.monsters;

import Warcraft.screen.textures.TextureAnimated;
import Warcraft.screen.textures.TextureImage;

public class IronGolem extends Monster {
    public IronGolem() {
        super(new TextureAnimated(new TextureImage[]{
                new TextureImage("assets/images/ironGolem3.png"),
                new TextureImage("assets/images/ironGolem1.png"),
                new TextureImage("assets/images/ironGolem2.png"),
                new TextureImage("assets/images/ironGolem1.png")
        },60), 100, .01, 1.1, false, 80);
    }
}
