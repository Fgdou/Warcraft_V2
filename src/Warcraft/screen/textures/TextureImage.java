package Warcraft.screen.textures;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

public class TextureImage extends Texture {

    private final String filename;

    public TextureImage(String filename){
        this.filename = filename;
    }

    @Override
    public void draw(Screen screen, Vec2 pos, double scale, double angle) {
        screen.drawImage(pos, new Vec2(scale, scale), filename, angle);
    }
}
