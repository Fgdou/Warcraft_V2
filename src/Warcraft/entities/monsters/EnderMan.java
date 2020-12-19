package Warcraft.entities.monsters;

import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;

public class EnderMan extends Monster {
    public EnderMan() {
        super(new TextureAnimated(new TextureImage[]{
                new TextureImage("assets/images/enderman1.png"),
                new TextureImage("assets/images/enderman3.png"),
                new TextureImage("assets/images/enderman2.png"),
                new TextureImage("assets/images/enderman3.png")
        }, 20), 20, .03, .9, false, 15);
    }
}
