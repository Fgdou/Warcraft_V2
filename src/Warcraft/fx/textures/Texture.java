package Warcraft.fx.textures;

import Warcraft.fx.Screen;
import Warcraft.tools.Vec2;

public abstract class Texture {
    public final static Texture BACKGROUND = new TextureImage("assets/images/background.png");
    public final static Texture PATH = new TextureImage("assets/images/path.png");
    public final static Texture SPAWN = new TextureImage("assets/images/path.png");
    public final static Texture CASTLE = new TextureImage("assets/images/castle.png");

    public abstract void draw(Screen screen, Vec2 pos, double scale, double angle);
}
