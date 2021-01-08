package Warcraft.screen.textures;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

import java.awt.*;

public class TextureTextRight extends Texture {

    private String text;
    private TextureImage image;

    /**
     * @param text      The text to show
     */
    public TextureTextRight(String text, TextureImage image){
        this.text = text;
        this.image = image;
    }

    @Override
    public void draw(Screen screen, Vec2 pos, double scale, double angle) {
        if(image == null){
            screen.drawTextLeft(pos, (int)scale*20, Color.black, text);
        }else{
            screen.drawTextImageRight(pos, (int)scale*20, Color.black, text, image.getFilename());
        }
    }
}
