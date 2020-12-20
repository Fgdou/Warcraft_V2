package Warcraft.entities.monsters;

import Warcraft.screen.textures.Texture;
import Warcraft.screen.textures.TextureAnimated;
import Warcraft.screen.textures.TextureImage;

public class Zombie extends Monster {
    public Zombie() {
        super(new TextureAnimated(new Texture[]{
                        new TextureImage("assets/images/zombie1.png"),
                        new TextureImage("assets/images/zombie3.png"),
                        new TextureImage("assets/images/zombie2.png"),
                        new TextureImage("assets/images/zombie3.png"),
                }, 20),
                5, .02, .5, false, 5);
    }
}
