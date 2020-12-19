package Warcraft.entities.monsters;

import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;
import Warcraft.tools.Vec2;

public class Zombie extends Monster {
    public Zombie(Vec2 pos) {
        super(new TextureAnimated(new Texture[]{
                        new TextureImage("assets/images/zombie1.png"),
                        new TextureImage("assets/images/zombie3.png"),
                        new TextureImage("assets/images/zombie2.png"),
                        new TextureImage("assets/images/zombie3.png"),
                }, 60),
                5, .1, .5, false);
    }
}
