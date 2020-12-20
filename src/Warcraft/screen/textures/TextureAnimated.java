package Warcraft.screen.textures;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

import java.util.Arrays;
import java.util.List;

public class TextureAnimated extends Texture {

    private final List<Texture> textures;
    private int cnt;
    private int iterationTexture;
    private final int timeChange;

    TextureAnimated(List<Texture> textures, int timeChange){
        this.textures = textures;
        this.timeChange = timeChange;
        this.iterationTexture = 0;
        cnt = 0;
    }
    public TextureAnimated(Texture textures[], int timeChange){
        this.textures = Arrays.asList(textures);
        this.timeChange = timeChange;
        this.iterationTexture = 0;
        cnt = 0;
    }

    @Override
    public void draw(Screen screen, Vec2 pos, double scale, double angle) {
        cnt++;
        if(cnt == timeChange) {
            iterationTexture = (iterationTexture + 1) % textures.size();
            cnt = 0;
        }

        Texture t = textures.get(iterationTexture);
        t.draw(screen, pos, scale, angle);
    }
}
