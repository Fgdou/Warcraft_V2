package Warcraft.entities.monsters;

import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;

public class Bat extends Monster {

    public Bat() {
        super(
                new TextureAnimated(new TextureImage[]{
                        new TextureImage("assets/images/bat1.png"),
                        new TextureImage("assets/images/bat2.png")
                }, 30)
                , 2, .01, .3, true, 3);
    }
}
