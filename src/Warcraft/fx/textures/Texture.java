package Warcraft.fx.textures;

import Warcraft.fx.Screen;
import Warcraft.tools.Vec2;

public abstract class Texture {
    public abstract void draw(Screen screen, Vec2 pos, double scale, double angle);
}
