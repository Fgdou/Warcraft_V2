package Warcraft.entities.monsters;

import Warcraft.screen.textures.TextureAnimated;
import Warcraft.screen.textures.TextureImage;

public class Bat extends Monster {

    public Bat() {
        super(
                new TextureAnimated(new TextureImage[]{
                        new TextureImage("assets/images/bat1.png"),
                        new TextureImage("assets/images/bat2.png")
                }, 10)
                , 2, .01, .3, true, 3);
    }
}
