package Warcraft.screen.textures;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

/**
 * This class handle the drawing of entities. Each entity can have one type of texture.
 * With this system, we can create an use new texture on any entity
 */

public abstract class Texture {
    //Static element for the level
    public final static Texture BACKGROUND = new TextureImage("assets/images/background.png");
    public final static Texture PATH = new TextureImage("assets/images/path.png");
    public final static Texture SPAWN = new TextureImage("assets/images/path.png");
    public final static Texture CASTLE = new TextureImage("assets/images/castle.png");

    /**
     * Draw to the screen
     * @param screen        the main screen
     * @param pos           the position on the screen (absolute)
     * @param scale         the scale
     * @param angle         the angle in deg
     */
    public abstract void draw(Screen screen, Vec2 pos, double scale, double angle);
}
