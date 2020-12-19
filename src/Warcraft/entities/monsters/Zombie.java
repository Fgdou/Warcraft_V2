package Warcraft.entities.monsters;

import Warcraft.Attack;
import Warcraft.entities.Entity;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class Zombie extends Monster {
    public Zombie(Vec2 pos) {
        super(new TextureAnimated(new Texture[]{
                        new TextureImage("assets/images/zombie1.png"),
                        new TextureImage("assets/images/zombie3.png"),
                        new TextureImage("assets/images/zombie2.png"),
                        new TextureImage("assets/images/zombie3.png"),
                }, 60),
                5, .02, .5, false);
        setAttack(new Attack(this, 2, 1, 4, 60));
    }
}
